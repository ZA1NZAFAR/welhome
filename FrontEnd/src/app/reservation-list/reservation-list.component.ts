import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service';
import { IReservation } from '../core/reservation/reservation.model';
import { ReservationService } from '../core/reservation/reservation.service';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {
  reservations: IReservation[] = [];

  constructor(
    private reservationService: ReservationService,
    private authService: AuthService
  ) { 
    
  }

  ngOnInit(): void {
    this.reservationService.getProperties().subscribe((reservations: IReservation[]) => {
      reservations.forEach((property: IReservation) => {
          this.reservations.push(property);
        
      });
    });
  }

}
