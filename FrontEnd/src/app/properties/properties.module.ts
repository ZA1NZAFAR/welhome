import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertiesComponent } from './properties.component';
import { MatButtonModule } from '@angular/material/button'
import { HostPropertyListModule } from '../host-property-list/host-property-list.module'
import { PropertyFormModule } from '../property-form/property-form.module'



@NgModule({
  declarations: [
    PropertiesComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    HostPropertyListModule,
    PropertyFormModule
  ]
})
export class PropertiesModule { }
