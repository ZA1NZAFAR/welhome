import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service';
import { IReservation } from '../core/reservation/reservation.model';
import { ReservationService } from '../core/reservation/reservation.service';
import { PropertyService } from '../core/property/property.service'
import { IProperty } from '../core/property/property.model'
import { ContextService } from '../core/context/context.service';
import { Observable, Subscription } from 'rxjs';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit, OnDestroy {
  reservations: IReservation[] = [];
  propertyMap: Map<number, IProperty> = new Map();
  private propertySubscription: Subscription;
  private reservationSubscription: Subscription;

  filterControl: FormControl;

  ownerPropertyLoadingObservable$: Observable<boolean>;
  reservationLoadingObservable$: Observable<boolean>;

  constructor(
    private reservationService: ReservationService,
    private propertyService: PropertyService,
    private authService: AuthService,
    private contextService: ContextService
  ) {

  }

  filterReservation(reservation: IReservation): boolean {
    const filters: string[] = this.filterControl.value;
    if (filters.includes(this.getStatus(reservation))) {
      return true;
    }
    return false;
  }

  getStatus(reservation: IReservation): string {
    if (!reservation.confirmed_owner && reservation.confirmed_renter) {
      return 'Rejected';
    }
    if (!reservation.confirmed_renter && reservation.confirmed_owner) {
      return 'Cancelled';
    }
    if (new Date(reservation.end_date) < new Date()) {
      if (reservation.confirmed_renter && reservation.confirmed_owner) {
        return 'Completed';
      }
      return 'Rejected';
    }
    if (reservation.confirmed_renter && reservation.confirmed_owner) {
      return 'Confirmed';
    }
    return 'Pending';
  }


  toggleFilterButtonColor(buttonString: string) {
    let className = 'btn-group';
    switch (buttonString) {
      case 'Confirmed':
        className = 'btn-confirmed btn-active';
        break;
      case 'Pending':
        className = 'btn-pending btn-active';
        break;
      case 'Rejected':
        className = 'btn-rejected btn-active';
        break;
      case 'Cancelled':
        className = 'btn-cancelled btn-active';
        break;
      case 'Completed':
        className = 'btn-completed btn-active';
        break;
    };
    const filters: string[] = this.filterControl.value;
    if (filters.includes(buttonString)) {
      return className;
    }
    return 'btn-group';
  }

  propertyExists(propertyId: number): boolean {
    return this.propertyMap.has(propertyId);
  }

  getProperty(propertyId: number): IProperty {
    if (!this.propertyExists(propertyId)) {
      throw new Error(`Property ${propertyId} does not exist`);
    }
    return this.propertyMap.get(propertyId)!;
  }

  ngOnInit(): void {
    this.filterControl = new FormControl(['Confirmed', 'Pending', 'Rejected', 'Cancelled', 'Completed']);
    const userEmail = this.authService.profile!.email;
    this.ownerPropertyLoadingObservable$ = this.propertyService.getOwnerProperties(userEmail).getOwnerPropertyLoadingObservable();
    this.reservationLoadingObservable$ = this.reservationService.getReservations().getReservationLoadingObservable();

    this.reservationSubscription = this.reservationService.getReservations()
      .getReservationObservable()
      .subscribe((reservations) => {
        this.reservations = reservations;
      });

    const propertyObservable = this.contextService.isRenter ? this.propertyService.getProperties().getPropertyObservable() : this.propertyService.getOwnerProperties(userEmail).getOwnerPropertyObservable();
    this.propertySubscription = propertyObservable.subscribe((properties) => {
      this.propertyMap.clear();
      properties.forEach((property: IProperty) => {
        this.propertyMap.set(property.id, property);
      });
    });
  }

  ngOnDestroy(): void {
    this.propertySubscription.unsubscribe();
    this.reservationSubscription.unsubscribe();
  }
}
