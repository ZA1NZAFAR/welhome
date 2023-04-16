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

  propertyShow: IProperty[] = [];

  propertyLoadingObservable$: Observable<boolean>;
  length: number = 0;
  pageIndex = 0;
  pageSizeOptions = [10, 20, 30];
  pageSize = this.pageSizeOptions[0];

  pageEvent: PageEvent;

  propertySubscription: Subscription;
  constructor(
    private propertyService: PropertyService
  ) { }

  ngOnInit(): void {
    this.propertyLoadingObservable$ = this.propertyService.getPropertyLoadingObservable();
    this.propertySubscription = this.propertyService.getProperties().getPropertyObservable().subscribe((properties: IProperty[]) => {
      this.properties = properties;
      this.length = properties.length;
      this.propertyShow = properties.slice(this.pageIndex * this.pageSize, (this.pageIndex + 1) * this.pageSize);
    });
  }

  ngOnDestroy(): void {
    if (this.propertySubscription) this.propertySubscription.unsubscribe();
  }

  handlePageEvent(event: PageEvent) {
    this.pageEvent = event;
    this.length = event.length;
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.propertyShow = this.properties.slice(this.pageIndex * this.pageSize, (this.pageIndex + 1) * this.pageSize);
  }

}
