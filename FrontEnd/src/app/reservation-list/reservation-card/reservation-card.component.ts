import { Component, Input, OnInit } from '@angular/core';
import { IProperty } from 'src/app/core/property/property.model'
import { PropertyService } from 'src/app/core/property/property.service'
import { IReservation } from 'src/app/core/reservation/reservation.model';
import { ReservationService } from 'src/app/core/reservation/reservation.service'
<<<<<<< Updated upstream
import { ActivatedRoute } from '@angular/router';

=======
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewService } from 'src/app/core/review/review.service';
import { AuthService } from 'src/app/core/auth/auth.service';
import { ContextService } from 'src/app/core/context/context.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {ReviewFormComponent} from "../../review-form/review-form.component";
import { ReactiveFormsModule } from '@angular/forms';
>>>>>>> Stashed changes

@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.scss']
})
export class ReservationCardComponent implements OnInit {
  @Input() reservation: IReservation;
  @Input() property: IProperty;
  propertyId: string;

  constructor(
    private propertyService: PropertyService,
    private reservationService: ReservationService,
<<<<<<< Updated upstream
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.propertyId = params['propertyId'];
      console.log("ici");
    });
=======
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
>>>>>>> Stashed changes
  }

  get status(): string {
    if (this.reservation.end_date < new Date()) {
      if (this.reservation.confirmed_renter) {
        return 'Terminé';
      }
      if (this.reservation.confirmed_owner) {
        return 'Annulé';
      }
      return 'Refusé';
    }
    if (!this.reservation.confirmed_renter) {
      if (this.reservation.confirmed_owner) {
        return 'Validé';
      }
      return 'En cours';
    }
    return 'Error';
  }

  get buttonClass(): string {
    switch (this.status) {
      case 'Terminé':
        return 'btn-success';
      case 'Validé':
        return 'btn-info';
      case 'En cours':
        return 'btn-warning';
      case 'Refusé':
        return 'btn-danger';
      case 'Annulé':
        return 'btn-secondary';
      default:
        return 'btn-primary';
    }
  }

<<<<<<< Updated upstream
=======
  goToProperty() {
    this.router.navigate(['properties', this.property.id]);
  }

  openReviewForm(propertyId: number) {
    const modalRef = this.modalService.open(ReviewFormComponent, { centered: true });
    modalRef.componentInstance.propertyId = propertyId;
    modalRef.result.then((result) => {
      // Do something with the result if needed
    }, (reason) => {
      // Handle the modal dismissal if needed
    });
  }
>>>>>>> Stashed changes

}
