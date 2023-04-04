import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IReservation } from './reservation.model';
import { BehaviorSubject, Subject } from 'rxjs';
import { mockProperties } from '../property/property.service';
import { mockGuests } from '../guest/guest.service';




const mockReservations: IReservation[] = [
{
  property: mockProperties.find((property) => property.id === 1),
  guest: mockGuests.find((guest) => guest.id === 1),
  state : 'Terminé',
  nbr_person : 2,
  check_in : new Date(2023, 3, 15),
  check_out : new Date(2023, 3, 18) ,
  total_price : 250
},

{
  property: mockProperties.find((property) => property.id === 2),
  guest: mockGuests.find((guest) => guest.id === 1),
  state : 'Validé',
  nbr_person : 2,
  check_in : new Date(2023, 3, 15),
  check_out : new Date(2023, 3, 18) ,
  total_price : 250
},

{
  property: mockProperties.find((property) => property.id === 3),
  guest: mockGuests.find((guest) => guest.id === 1),
  state : 'Annulé',
  nbr_person : 2,
  check_in : new Date(2023, 3, 15),
  check_out : new Date(2023, 3, 18) ,
  total_price : 250
},

]
@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private reservations: IReservation[] = mockReservations;
  private reservationSubject: BehaviorSubject<IReservation[]> = new BehaviorSubject<IReservation[]>([]);

  constructor(    
    private http: HttpClient
    ) { }

    /* for test */

    private getMockReservation(): BehaviorSubject<IReservation[]> {
      this.reservationSubject.next(this.reservations);
      return this.reservationSubject;
    }
  
    getProperties(): BehaviorSubject<IReservation[]> {
      return this.getMockReservation(); // should use http client to get data from server
    }
}
