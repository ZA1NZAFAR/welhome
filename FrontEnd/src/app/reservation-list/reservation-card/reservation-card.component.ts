import { Component, Input, OnInit } from '@angular/core';
import { IReservation } from 'src/app/core/reservation/reservation.model';

@Component({
  selector: 'app-reservation-card',
  templateUrl: './reservation-card.component.html',
  styleUrls: ['./reservation-card.component.scss']
})
export class ReservationCardComponent implements OnInit {

  @Input() reservation: IReservation;

  constructor() { }

  ngOnInit(): void {
  }

  getButtonClass(): string {
    switch (this.reservation.state) {
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
