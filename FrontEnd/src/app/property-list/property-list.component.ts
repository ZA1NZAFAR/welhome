import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service';
import { IProperty } from '../core/property/property.model';
import { PropertyService } from '../core/property/property.service';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})
export class PropertyListComponent implements OnInit, OnDestroy {
  properties: IProperty[] = [];

  propertyLoadingObservable$: Observable<boolean>;

  propertySubscription: Subscription;
  constructor(
    private propertyService: PropertyService
  ) { }

  ngOnInit(): void {
    this.propertyLoadingObservable$ = this.propertyService.getPropertyLoadingObservable();
    this.propertySubscription = this.propertyService.getPropertyObservable().subscribe((properties: IProperty[]) => {
      this.properties = properties;
    });
  }

  ngOnDestroy(): void {
    if (this.propertySubscription) this.propertySubscription.unsubscribe();
  }
}
