import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HostPropertyListComponent } from './host-property-list.component';
import { HostPropertyCardComponent } from './host-property-card/host-property-card.component';
import { PropertyFormComponent } from './property-form/property-form.component';



@NgModule({
  declarations: [
    HostPropertyListComponent,
    HostPropertyCardComponent,
    PropertyFormComponent
  ],
  imports: [
    CommonModule
  ]
})
export class HostPropertyListModule { }
