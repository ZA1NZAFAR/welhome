import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/auth/auth.service'
import { IProperty } from 'src/app/core/property/property.model'
import { PropertyService } from 'src/app/core/property/property.service'
import { IReservation } from 'src/app/core/reservation/reservation.model';
import { ReservationService } from 'src/app/core/reservation/reservation.service'

@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.scss']
})
export class ReservationCardComponent implements OnInit {
  @Input() reservation: IReservation;
  @Input() property: IProperty;

  constructor(
    private propertyService: PropertyService,
    private reservationService: ReservationService
  ) { }

  ngOnInit(): void { }

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
  

}
