import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyFormComponent } from './property-form.component'
import { MatButtonToggleModule } from '@angular/material/button-toggle'
import { MatNativeDateModule } from '@angular/material/core'
import { MatDatepickerModule } from '@angular/material/datepicker'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from '@angular/material/form-field'
import { ReactiveFormsModule } from '@angular/forms'



@NgModule({
  declarations: [
    PropertyFormComponent
  ],
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonToggleModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    MatFormFieldModule
  ]
})
export class PropertyFormModule { }
