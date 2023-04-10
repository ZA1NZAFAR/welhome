import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { IProperty } from 'src/app/core/property/property.model';
import { PropertyService } from 'src/app/core/property/property.service';
import { Location } from '@angular/common';
import { AuthService } from '../core/auth/auth.service'
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { DateAdapter, MAT_DATE_LOCALE } from '@angular/material/core';
import { ReservationService } from '../core/reservation/reservation.service';
import { IReservation } from '../core/reservation/reservation.model';
import { ContextService } from '../core/context/context.service';
import {IReview} from "../core/review/review.model";
import {ReviewService} from "../core/review/review.service";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReviewModalComponent } from './review-modal/review-modal.component';
@Component({
  selector: 'app-properties',
  templateUrl: './properties.component.html',
  styleUrls: ['./properties.component.scss'],
  providers:[ { provide: MAT_DATE_LOCALE, useValue: 'fr' } ]
})
export class PropertiesComponent implements OnInit, OnDestroy {
  propertyData: IProperty;
  reviewData: IReview[] = [];

  user_email: string;
  getPropertySub$: Subscription;
  paramsSub$: Subscription;
  currentImageIndex: number = 0;
  images: string[] = [];

  reservationGroup: FormGroup;


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
  ) {}

  ngOnInit(): void {
    this.adapter.setLocale('fr');
    this.reservationGroup = new FormGroup({
      start_date: new FormControl('', [Validators.required, this.validateDate()]),
      end_date: new FormControl('', [Validators.required, this.validateDate()])
    });
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

        this.images = [];
        if (this.propertyData.image_url !== undefined) {
          this.images.push(this.propertyData.image_url);
        }
        if (this.propertyData.image_url2 !== undefined) {
          this.images.push(this.propertyData.image_url2);
        }
        if (this.propertyData.image_url3 !== undefined) {
          this.images.push(this.propertyData.image_url3);
        }
      });
    });
    this.user_email = this.authService.profile?.email || '';
  }
  getRatingStars(rating: number): string[] {
    const fullStars = Math.floor(rating);
    const halfStar = rating % 1 >= 0.5 ? 1 : 0;
    const emptyStars = 5 - fullStars - halfStar;
    return Array(fullStars).fill('fas fa-star')
      .concat(Array(halfStar).fill('fas fa-star-half-alt'))
      .concat(Array(emptyStars).fill('far fa-star'));
  }

  get dateRequiredError(): boolean {
    return this.reservationGroup.controls['start_date'].hasError('required') ||
           this.reservationGroup.controls['end_date'].hasError('required');
  }

  get dateMinimumError(): boolean {
    return !this.dateRequiredError && (
      this.reservationGroup.controls['start_date'].hasError('dateMinimum') || 
      this.reservationGroup.controls['end_date'].hasError('dateMinimum'));
  }

  validateDate(): ValidatorFn {
    return (control: AbstractControl) => {
      const date = control.value;
      if (date !== null && date !== undefined && date < Date.now()) {
        return { dateMinimum: true };
      }
      return null;
    };
  }

  get days(): number {
    const start = new Date(this.reservationGroup.value.start_date);
    const end = new Date(this.reservationGroup.value.end_date);
    if (isNaN(start.getTime()) || isNaN(end.getTime()) || start.getTime() > end.getTime()) {
      return 0;
    }
    return (end.getTime() - start.getTime()) / (1000 * 3600 * 24);
  }

  get totalPrice(): number {
    return this.days * this.propertyData.price;
  }

  get isOwner(): boolean {
    return this.user_email === this.propertyData.owner_email;
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
        renter_email: this.user_email,
        property_id: this.propertyData.id,
        confirmed_owner: false,
        confirmed_renter: false
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
