import { Component, OnDestroy, OnInit } from '@angular/core';
import { IProperty } from '../core/property/property.model';
import { PropertyService } from '../core/property/property.service';
import { Observable, Subscription } from 'rxjs';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})
export class PropertyListComponent implements OnInit, OnDestroy {
  properties: IProperty[] = [];

  propertyLoadingObservable$: Observable<boolean>;
  pageEvent: PageEvent;
  propertySubscription: Subscription;
  constructor(
    private propertyService: PropertyService
  ) { }

  ngOnInit(): void {
    this.propertyLoadingObservable$ = this.propertyService.getPropertyLoadingObservable();
    this.propertySubscription = this.propertyService.getProperties().getPropertyObservable().subscribe((properties: IProperty[]) => {
      this.properties = properties;
    });
  }

  ngOnDestroy(): void {
    if (this.propertySubscription) this.propertySubscription.unsubscribe();
  }

  handlePageEvent(event: PageEvent) {
    this.pageEvent = event;
  }
}
