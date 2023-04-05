import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service'
import { IProperty } from '../core/property/property.model'
import { PropertyService } from '../core/property/property.service'
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PropertyFormComponent } from './property-form/property-form.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-host-property-list',
  templateUrl: './host-property-list.component.html',
  styleUrls: ['./host-property-list.component.scss']
})
export class HostPropertyListComponent implements OnInit, OnDestroy {
  propertyMap: Map<number, IProperty> = new Map();
  properties: IProperty[] = [];

  loading = false;

  private getPropertiesSub$: Subscription;
  private getPropertyLoadingSub$: Subscription;

  constructor(
    private propertyService: PropertyService,
    private authService: AuthService,
    private modalService: NgbModal
  ) { }
  ngOnDestroy(): void {
    this.getPropertiesSub$.unsubscribe();
    this.getPropertyLoadingSub$.unsubscribe();
  }

  ngOnInit(): void {
    this.getPropertiesSub$ = this.propertyService.getProperties().subscribe((properties) => {
      const userEmail = this.authService.profile?.email;
      properties.forEach((property: IProperty) => {
        if (property.owner_email === userEmail && this.propertyMap.get(property.id) === undefined) {
          this.propertyMap.set(property.id, property);
        }
      });
      this.properties = Array.from(this.propertyMap.values());
    });
    this.getPropertyLoadingSub$ = this.propertyService.getPropertyLoading().subscribe((loading) => {
      this.loading = loading;
    });
  }

  openAddForm() {
    const modal = this.modalService.open(PropertyFormComponent);
    modal.componentInstance.owner_email = this.authService.profile?.email;
  }
}
