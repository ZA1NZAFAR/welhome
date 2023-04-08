import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IReservation } from './reservation.model';
import { BehaviorSubject, Observable, Subject, map } from 'rxjs';
import { ToastService } from 'src/app/utils/toast/toast.service'

const mockPropertyMap: Map<number, IReservation> = new Map([
  [1, {
    id: 1,
    property_id : 1,
    start_date : new Date(2023, 2, 15),
    end_date : new Date(2023, 2, 18) ,
    confirmed_owner : true,
    confirmed_renter : false,
    renter_email : 'xyz@gmail.com'
  }],
  [2, {
    id: 2,
    property_id : 3,
    start_date : new Date(2023, 2, 15),
    end_date : new Date(2023, 2, 18) ,
    confirmed_owner : true,
    confirmed_renter : true,
    renter_email : 'zain.zafar@gmail.com'
  }],
  [3, {
    id: 3,
    property_id : 2,
    start_date : new Date(2023, 3, 15),
    end_date : new Date(2023, 3, 18) ,
    confirmed_owner : true,
    confirmed_renter : false,
    renter_email : 'zzz@lmao.com'
  }]
]);

const mockReservations: IReservation[] = Array.from(mockPropertyMap.values());
@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private reservations: IReservation[] = mockReservations;

  private _reservationCount: number = mockReservations.length;
  private reservationSubject: BehaviorSubject<IReservation[]> = new BehaviorSubject(this.reservations);

  private loadingSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(    
    private http: HttpClient,
    toastService: ToastService
    ) { }

    /* for test */

    private _getMockReservation(renter_email?: string): BehaviorSubject<IReservation[]> {
      const reservations: IReservation[] = [];
      mockReservations.forEach((reservation: IReservation) => {
        if (renter_email && reservation.renter_email !== renter_email) {
          return;
        }
        reservations.push(reservation);
      });
      return new BehaviorSubject(reservations);
    }
  
    getReservations(renter_email?: string): Observable<IReservation[]> {
      this.loadingSubject.next(true);
      return this._getMockReservation(renter_email).pipe(
        map((reservations) => {
          this.reservations = reservations;
          this.reservationSubject.next(this.reservations);
          this.loadingSubject.next(false);
          return this.reservations;
        })
      ); // should use http client to get data from server
    }

    getReservationLoading(): Observable<boolean> {
      return this.loadingSubject.asObservable();
    }
}
