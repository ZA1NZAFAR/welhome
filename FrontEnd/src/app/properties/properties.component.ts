import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { IProperty } from 'src/app/core/property/property.model';
import { PropertyService } from 'src/app/core/property/property.service';
import { Location } from '@angular/common';
import { AuthService } from '../core/auth/auth.service'
import { AbstractControl, FormControl, FormGroup, FormGroupDirective, NgForm, ValidatorFn, Validators } from '@angular/forms';
import { DateAdapter, ErrorStateMatcher, MAT_DATE_LOCALE } from '@angular/material/core';
import { ReservationService } from '../core/reservation/reservation.service';
import { IReservation } from '../core/reservation/reservation.model';
import { ContextService } from '../core/context/context.service';
import {IReview} from "../core/review/review.model";
import {ReviewService} from "../core/review/review.service";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReviewModalComponent } from './review-modal/review-modal.component';
import { IconDefinition, faStar } from '@fortawesome/free-regular-svg-icons';
import { faStar as faSolidStar, faStarHalfAlt, faCheck, faExchangeAlt } from '@fortawesome/free-solid-svg-icons';

class DateErrorMatcher implements ErrorStateMatcher {
  isErrorState(control: AbstractControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return !!control && control.touched && !!form && (form.hasError('dayMinimum') || form.hasError('required') || form.hasError('invalidDate'));
  }
}
@Component({
  selector: 'app-properties',
  templateUrl: './properties.component.html',
  styleUrls: ['./properties.component.scss'],
  providers:[ { provide: MAT_DATE_LOCALE, useValue: 'fr' } ]
})
export class PropertiesComponent implements OnInit, OnDestroy {
  faCheck = faCheck;
  faExchangeAlt = faExchangeAlt;
  propertyData: IProperty;
  reviewData: IReview[] = [];

  minStartDate: Date;

  userEmail: string;
  getPropertySub$: Subscription;
  paramsSub$: Subscription;
  currentImageIndex: number = 0;
  images: string[] = [];

  reservationGroup: FormGroup;

  errorDateMatcher: DateErrorMatcher;


  constructor(
    private propertyService: PropertyService,
    private reviewService : ReviewService,
    private route: ActivatedRoute,
    private reservationService: ReservationService,
    private locationService: Location,
    private authService: AuthService,
    private adapter: DateAdapter<any>,
    private contextService: ContextService,
    private modalService: NgbModal
  ) {
    this.minStartDate = new Date();
    this.minStartDate.setDate(this.minStartDate.getDate() + 2);
  }

  ngOnInit(): void {
    this.adapter.setLocale('fr');
    this.images = [];
    this.errorDateMatcher = new DateErrorMatcher();
    this.reservationGroup = new FormGroup({
      startDate: new FormControl('', [Validators.required]),
      endDate: new FormControl('', [Validators.required])
    }, { validators: [this.validateDate()]});
    this.paramsSub$ = this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id === null) {
        this.locationService.back();
        return;
      }
      this.getPropertySub$ = this.propertyService.getProperties().getPropertyObservable().subscribe(properties => {
        const property = properties.find(p => p.id === id);
        if (property === undefined) {
          this.locationService.back();
          return;
        }
        this.propertyData = property;
        const reviewSub$ = this.reviewService.getPropertyReviews(property.id).subscribe(reviews => {
          this.reviewData = reviews;
          reviewSub$.unsubscribe();
        });

        
        if (!!this.propertyData.imageUrl1) {
          this.images.push(this.propertyData.imageUrl1);
        }
        if (!!this.propertyData.imageUrl2) {
          this.images.push(this.propertyData.imageUrl2);
        }
        if (!!this.propertyData.imageUrl3) {
          this.images.push(this.propertyData.imageUrl3);
        }
      });
    });
    this.userEmail = this.authService.profile?.email || '';
  }
  getRatingStars(rating: number): IconDefinition[] {
    const fullStars = Math.floor(rating);
    const halfStar = rating % 1 >= 0.5 ? 1 : 0;
    const emptyStars = 5 - fullStars - halfStar;
    return Array(fullStars).fill(faSolidStar)
      .concat(Array(halfStar).fill(faStarHalfAlt))
      .concat(Array(emptyStars).fill(faStar));
  }

  validateDate(): ValidatorFn {
    return (control: AbstractControl) => {
      if (!this.reservationGroup) {
        return null;
      }
      const startDate = this.reservationGroup.value.startDate;
      const endDate = this.reservationGroup.value.endDate;
      if (!startDate || !endDate) {
        return { required: true };
      }
      const start = new Date(startDate);
      const end = new Date(endDate);
      if (isNaN(start.getTime()) || isNaN(end.getTime())){
        return { invalidDate: true };
      }
      const days = (end.getTime() - start.getTime()) / (1000 * 3600 * 24);
      if (days < 1) {
        return { dayMinimum: true };
      }
      return null;
    };
  }

  get days(): number {
    const start = new Date(this.reservationGroup.value.startDate);
    const end = new Date(this.reservationGroup.value.endDate);
    if (isNaN(start.getTime()) || isNaN(end.getTime()) || start.getTime() > end.getTime()) {
      return 0;
    }
    return (end.getTime() - start.getTime()) / (1000 * 3600 * 24);
  }

  get totalPrice(): number {
    return this.days * this.propertyData.price;
  }

  get isOwner(): boolean {
    return this.userEmail === this.propertyData.ownerEmail;
  }

  get isRenterContext(): boolean {
    return this.contextService.isRenter;
  }

  ngOnDestroy(): void {
    this.getPropertySub$.unsubscribe();
    this.paramsSub$.unsubscribe();
  }

  nextImage() {
    this.currentImageIndex++;
    if (this.currentImageIndex >= this.images.length) {
      this.currentImageIndex = 0;
    }
  }

  get userIsLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }

  promptLogin(): void {
    this.authService.login();
  }

  reserveProperty(): void {
    if (this.reservationGroup.valid) {
      const reservation: IReservation = {
        ...this.reservationGroup.value,
        startDate: new Date(this.reservationGroup.value.startDate),
        endDate: new Date(this.reservationGroup.value.endDate),
        renterEmail: this.userEmail,
        propertyId: this.propertyData.id,
        confirmedOwner: false,
        confirmedRenter: false,
        totalPrice: this.totalPrice
      }
      this.reservationService.addReservation(reservation).subscribe(() => {
        this.reservationGroup.reset();
      });
    }
  }

  openImage(image: string): void {
    const modalRef = this.modalService.open(ReviewModalComponent);
    modalRef.componentInstance.image = image;
  }
}
