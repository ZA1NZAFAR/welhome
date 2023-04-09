import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service';
import { IReservation } from '../core/reservation/reservation.model';
import { ReservationService } from '../core/reservation/reservation.service';
import { PropertyService } from '../core/property/property.service'
import { IProperty } from '../core/property/property.model'
import { ContextService } from '../core/context/context.service';
import { Observable, Subscription } from 'rxjs';

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

  ownerPropertyLoadingObservable$: Observable<boolean>;
  reservationLoadingObservable$: Observable<boolean>;

  constructor(
    private reservationService: ReservationService,
    private propertyService: PropertyService,
    private authService: AuthService,
    private contextService: ContextService
  ) {

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
