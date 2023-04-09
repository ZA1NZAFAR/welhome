import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IReservation } from './reservation.model';
import { BehaviorSubject, Observable, Subject, Subscription, map } from 'rxjs';
import { ToastService } from 'src/app/utils/toast/toast.service'
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';
import { ContextService } from '../context/context.service';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private reservationSubject: Subject<IReservation[]>;
  private reservationLoadingSubject: BehaviorSubject<boolean>;
  private reservationObservable: Observable<IReservation[]>;
  private reservationLoadingObservable: Observable<boolean>;
  private reservationSubsrciption: Subscription;

  constructor(    
    private http: HttpClient,
    private toastService: ToastService,
    private authService: AuthService,
    private contextService: ContextService
    ) {
      this.reservationSubject = new Subject<IReservation[]>();
      this.reservationLoadingSubject = new BehaviorSubject<boolean>(false);
      this.reservationObservable = this.reservationSubject.asObservable();
      this.reservationLoadingObservable = this.reservationLoadingSubject.asObservable();
    }
  
    getReservations(): ReservationService {
      const renter_email = this.contextService.isRenter ? this.authService.profile!.email : undefined;
      if (this.reservationSubsrciption) {
        this.reservationSubsrciption.unsubscribe();
      }
      this.reservationLoadingSubject.next(true);
      this.reservationSubsrciption = this.http.get<IReservation[]>(`${environment.backEndUrl}/reservations${ renter_email ? `/renter_email/${renter_email}` : ''}`)
        .subscribe((reservations) => {
          this.reservationSubject.next(reservations);
          this.reservationLoadingSubject.next(false);
        });
      return this;
    }

    getReservationObservable(): Observable<IReservation[]> {
      return this.reservationObservable;
    }
    getReservationLoadingObservable(): Observable<boolean> {
      return this.reservationLoadingObservable;
    }

    getReservationLoading(): Observable<boolean> {
      return this.reservationLoadingSubject.asObservable();
    }

    addReservation(reservation: IReservation): Observable<IReservation> {
      return this.http.post<IReservation>(`${environment.backEndUrl}/reservations`, reservation)
        .pipe(
          map((reservation) => {
            this.getReservations();
            this.toastService.showSuccess('Reservation added successfully');
            return reservation;
          })
        );
    }

    updateReservation(reservation: IReservation): Observable<IReservation> {
      return this.http.put<IReservation>(`${environment.backEndUrl}/reservations/${reservation.id}`, reservation)
        .pipe(
          map((reservation) => {
            this.getReservations();
            this.toastService.showSuccess('Reservation updated successfully');
            return reservation;
          })
        );
    }

    deleteReservation(reservation: IReservation): Observable<IReservation> {
      return this.http.delete<IReservation>(`${environment.backEndUrl}/reservations/${reservation.id}`)
        .pipe(
          map((reservation) => {
            this.getReservations();
            this.toastService.showSuccess('Reservation deleted successfully');
            return reservation;
          })
        );
    }
}
