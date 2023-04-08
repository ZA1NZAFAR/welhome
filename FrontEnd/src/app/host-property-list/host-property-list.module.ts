import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HostPropertyListComponent } from './host-property-list.component';
import { HostPropertyCardComponent } from './host-property-card/host-property-card.component';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { PropertyFormModule } from '../property-form/property-form.module'


@NgModule({
  declarations: [
    HostPropertyListComponent,
    HostPropertyCardComponent
  ],
  imports: [
    CommonModule,
    NgbModalModule,
    PropertyFormModule
  ]
})
export class HostPropertyListModule { }
