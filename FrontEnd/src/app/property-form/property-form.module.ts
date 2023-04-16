import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyFormComponent } from './property-form.component'
import { MatButtonToggleModule } from '@angular/material/button-toggle'
import { MatNativeDateModule } from '@angular/material/core'
import { MatDatepickerModule } from '@angular/material/datepicker'
import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from '@angular/material/form-field'
import { ReactiveFormsModule } from '@angular/forms'
import { MatButtonModule } from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon'; 



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
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class PropertyFormModule { }
