import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyListComponent } from './property-list.component';
import { PropertyCardComponent } from './property-card/property-card.component';
import { MatSliderModule } from '@angular/material/slider'; 
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { FilterComponent } from '../commons/navbar/search-bar/filter/filter.component';

@NgModule({
  declarations: [
    PropertyListComponent,
    PropertyCardComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule
  ],
  providers: [ ]
})
export class PropertyListModule { }
