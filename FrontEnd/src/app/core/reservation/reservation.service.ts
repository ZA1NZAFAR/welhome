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
  private reservationSubscription: Subscription;

  private ownerReservations: Subject<IReservation[]>;
  private ownerReservationsLoading: BehaviorSubject<boolean>;
  private ownerReservationsObservable: Observable<IReservation[]>;
  private ownerReservationsLoadingObservable: Observable<boolean>;
  private ownerReservationsSubscription: Subscription;

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
      this.ownerReservations = new Subject<IReservation[]>();
      this.ownerReservationsLoading = new BehaviorSubject<boolean>(false);
      this.ownerReservationsObservable = this.ownerReservations.asObservable();
      this.ownerReservationsLoadingObservable = this.ownerReservationsLoading.asObservable();
    }
  
    getReservations(): ReservationService {
      if (this.reservationSubscription) {
        this.reservationSubscription.unsubscribe();
      }
      this.reservationLoadingSubject.next(true);
      this.reservationSubscription = this.http.get<IReservation[]>(`${environment.backEndUrl}/reservations/renter_email/${this.authService.profile!.email}`)
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

    getOwnerReservations(propertyId: number = 0): ReservationService {
      if (this.ownerReservationsSubscription) {
        this.ownerReservationsSubscription.unsubscribe();
      }
      this.ownerReservationsLoading.next(true);
      this.ownerReservationsSubscription = this.http.get<IReservation[]>(`${environment.backEndUrl}/reservations?owner_email=${this.authService.profile!.email}`)
        .subscribe((reservations) => {
          if (propertyId > 0) {
            reservations = reservations.filter(reservation => reservation.propertyId === propertyId);
          }
          this.ownerReservations.next(reservations);
          this.ownerReservationsLoading.next(false);
        });
      return this;
    }

    getOwnerReservationObservable(): Observable<IReservation[]> {
      return this.ownerReservationsObservable;
    }
    getOwnerReservationLoadingObservable(): Observable<boolean> {
      return this.ownerReservationsLoadingObservable;
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
            this.getOwnerReservations();
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
            this.getOwnerReservations();
            this.toastService.showSuccess('Reservation deleted successfully');
            return reservation;
          })
        );
    }
}
