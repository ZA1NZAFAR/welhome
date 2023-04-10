import { Component, Input, OnInit } from '@angular/core';
import { IProperty } from 'src/app/core/property/property.model'
import { PropertyService } from 'src/app/core/property/property.service'
import { IReservation } from 'src/app/core/reservation/reservation.model';
import { ReservationService } from 'src/app/core/reservation/reservation.service'
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewService } from 'src/app/core/review/review.service';
import { AuthService } from 'src/app/core/auth/auth.service';
import { ContextService } from 'src/app/core/context/context.service';


@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.scss']
})
export class ReservationCardComponent implements OnInit {
  @Input() reservation: IReservation;
  @Input() property: IProperty;

  rating: number = -1;

  constructor(
    private propertyService: PropertyService,
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

  get status(): string {
    if (new Date(this.reservation.end_date) < new Date()) {
      if (this.reservation.confirmed_renter) {
        return 'Completed';
      }
      if (this.reservation.confirmed_owner) {
        return 'Cancelled';
      }
      return 'Rejected';
    }
    if (!this.reservation.confirmed_renter) {
      if (this.reservation.confirmed_owner) {
        return 'Confirmed';
      }
      return 'Pending';
    }
    return `Error`;
  }

  get buttonClass(): string {
    switch (this.status) {
      case 'Completed':
        return 'btn-success';
      case 'Confirmed':
        return 'btn-info';
      case 'Pending':
        return 'btn-warning';
      case 'Rejected':
        return 'btn-danger';
      case 'Cancelled':
        return 'btn-secondary';
      default:
        return 'btn-primary';
    }
  }

  goToProperty() {
    this.router.navigate(['properties', this.property.id]);
  }


}
