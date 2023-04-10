import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertiesComponent } from './properties.component';
import { MatButtonModule } from '@angular/material/button'
import { HostPropertyListModule } from '../host-property-list/host-property-list.module'
import { PropertyFormModule } from '../property-form/property-form.module'
import { ReactiveFormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { ReviewModalComponent } from './review-modal/review-modal.component';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';



@NgModule({
  declarations: [
    PropertiesComponent,
    ReviewModalComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    HostPropertyListModule,
    PropertyFormModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    NgbModalModule,
    MatInputModule
  ]
})
export class PropertiesModule { }
