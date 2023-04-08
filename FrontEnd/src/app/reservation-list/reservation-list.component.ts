import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service';
import { IReservation } from '../core/reservation/reservation.model';
import { ReservationService } from '../core/reservation/reservation.service';
import { PropertyService } from '../core/property/property.service'
import { IProperty } from '../core/property/property.model'
import { ContextService } from '../core/context/context.service';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.scss']
})
export class ReservationListComponent implements OnInit {
  reservations: IReservation[] = [];
  propertyMap: Map<number, IProperty> = new Map();

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
    const userEmail = this.authService.profile?.email;
    this.reservationService.getReservations(this.contextService.isRenter ? userEmail : undefined).subscribe((reservations: IReservation[]) => {
      this.reservations = reservations;
    });
    this.propertyService.getProperties(!this.contextService.isRenter ? userEmail : undefined).subscribe((properties) => {
      this.propertyMap.clear();
      properties.forEach((property: IProperty) => {
        this.propertyMap.set(property.id, property);
      });
    });
  }

}
