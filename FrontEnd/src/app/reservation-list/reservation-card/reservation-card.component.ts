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
import { ReviewFormComponent } from 'src/app/review-form/review-form.component';
import { IReview } from 'src/app/core/review/review.model';

@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.scss']
})
export class ReservationCardComponent implements OnInit {
  @Input() reservation: IReservation;
  @Input() property: IProperty;
  @Input() status: string = 'Error';

  review: IReview;

  constructor(
    private modalService: NgbModal,
    private reservationService: ReservationService,
    private router: Router,
    private reviewService: ReviewService,
    private authService: AuthService,
    private contextService: ContextService
  ) { }


  ngOnInit() {
    const email = this.contextService.isRenter ? this.authService.profile!.email : this.reservation.renter_email;
    const reviewSub$ = this.reviewService.getPropertyReviews(this.property.id).subscribe(reviews => {
      const review = reviews.find(r => r.reviewer_email === email);
      if (review) {
        this.review = review;
      }
      reviewSub$.unsubscribe();
    });
  }

  get canReview(): boolean {
    return (this.status === 'Completed' ||
            this.status === 'Rejected' ||
            this.status === 'Cancelled') &&
            this.contextService.isRenter;
  }

  get ratingText(): string {
    if (!this.review) {
      return 'No rating';
    }
    return this.review.rating.toString();
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

  openReviewForm() {
    const modalRef = this.modalService.open(ReviewFormComponent);
    modalRef.componentInstance.property = this.property;
    modalRef.componentInstance.review = this.review || undefined;
  }
}
