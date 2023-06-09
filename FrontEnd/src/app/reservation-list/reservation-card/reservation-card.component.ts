import { Component, Input, OnInit } from '@angular/core';
import { IProperty } from 'src/app/core/property/property.model'
import { IReservation } from 'src/app/core/reservation/reservation.model';
import { ReservationService } from 'src/app/core/reservation/reservation.service'
import { Router } from '@angular/router';
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

  @Input() review: IReview | undefined;

  constructor(
    private modalService: NgbModal,
    private reservationService: ReservationService,
    private router: Router,
    private authService: AuthService,
    private contextService: ContextService
  ) { }


  ngOnInit() {
    const email = this.contextService.isRenter ? this.authService.profile!.email : this.reservation.renterEmail;
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
    this.reservation.confirmedOwner = true;
    this.reservation.confirmedRenter = true;
    this.reservationService.updateReservation(this.reservation).subscribe();
  }

    submitReject(): void {
    if (!this.canReject) {
      return;
    }
    if (this.contextService.isRenter) {
      this.reservation.confirmedRenter = false;
      this.reservation.confirmedOwner = true;
    } else {
      this.reservation.confirmedRenter = true;
      this.reservation.confirmedOwner = false;
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
