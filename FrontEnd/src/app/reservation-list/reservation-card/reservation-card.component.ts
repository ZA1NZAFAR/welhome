import { Component, Input, OnInit } from '@angular/core';
import { IProperty } from 'src/app/core/property/property.model'
import { PropertyService } from 'src/app/core/property/property.service'
import { IReservation } from 'src/app/core/reservation/reservation.model';
import { ReservationService } from 'src/app/core/reservation/reservation.service'
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewService } from 'src/app/core/review/review.service';
import { AuthService } from 'src/app/core/auth/auth.service';
import { ContextService } from 'src/app/core/context/context.service';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {ReviewFormComponent} from "../../review-form/review-form.component";
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.scss']
})
export class ReservationCardComponent implements OnInit {
  @Input() reservation: IReservation;
  @Input() property: IProperty;
  @Input() status: string = 'Error';

  propertyId: string;
  rating: number = -1;

  constructor(
    private propertyService: PropertyService,
    private reservationService: ReservationService,
    private router: Router,
    private reviewService: ReviewService,
    private authService: AuthService,
    private contextService: ContextService,
    private modalService: NgbModal
  ) { }


  ngOnInit() {
    const email = this.contextService.isRenter ? this.authService.profile!.email : this.reservation.renter_email;
    const reviewSub$ = this.reviewService.getPropertyReviews(this.property.id).subscribe(reviews => {
      const review = reviews.find(r => r.reviewer_email === email);
      if (review) {
        this.rating = review.rating;
      }
      reviewSub$.unsubscribe();
    });
  }

  get ratingText(): string {
    if (this.rating === -1) {
      return 'No rating';
    }
    return this.rating.toString();
  }

  get ratingDescription(): string {
    if (this.contextService.isRenter) {
      return 'Your rating';
    }
    return 'Renter rating';
  }

  // get status(): string {
  //   if (this.reservation.end_date < new Date()) {
  //     if (this.reservation.confirmed_renter) {
  //       return 'Terminé';
  //     }
  //     if (this.reservation.confirmed_owner) {
  //       return 'Annulé';
  //     }
  //     return 'Refusé';
  //
  //   }
  //   return this.rating.toString();
  // }

  get textClass(): string {
    switch (this.status) {
      case 'Completed':
        return 'text-success';
      case 'Confirmed':
        return 'text-primary';
      case 'Pending':
        return 'text-info';
      case 'Rejected':
        return 'text-danger';
      case 'Cancelled':
        return 'text-warning';
      default:
        return 'text-secondary';
    }
  }

  get canConfirm(): boolean {
    return !this.contextService.isRenter && this.status === 'Pending';
  }

  get canReject(): boolean {
    return this.status === 'Confirmed' || this.status === 'Pending';
  }

  submitConfirm(): void {
    if (!this.canConfirm) {
      return;
    }
    this.reservation.confirmed_owner = true;
    this.reservation.confirmed_renter = true;
    this.reservationService.updateReservation(this.reservation).subscribe();
  }

  openReviewForm(propertyId: number) {
    const modalRef = this.modalService.open(ReviewFormComponent, { centered: true });
    modalRef.componentInstance.propertyId = propertyId;
    modalRef.result.then((result) => {
      // Do something with the result if needed
    }, (reason) => {
      // Handle the modal dismissal if needed
    })
  }

    submitReject(): void {
    if (!this.canReject) {
      return;
    }
    if (this.contextService.isRenter) {
      this.reservation.confirmed_renter = false;
      this.reservation.confirmed_owner = true;
    } else {
      this.reservation.confirmed_renter = true;
      this.reservation.confirmed_owner = false;
    }
    this.reservationService.updateReservation(this.reservation).subscribe();
  }

  goToProperty() {
    this.router.navigate(['properties', this.property.id]);
  }
}
