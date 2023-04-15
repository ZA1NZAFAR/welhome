import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationListComponent } from './reservation-list.component';
import { ReservationCardComponent } from './reservation-card/reservation-card.component';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import { ReviewFormModule } from '../review-form/review-form.module';
import { MatPaginatorModule } from '@angular/material/paginator';

@NgModule({
  declarations: [
    ReservationListComponent,
    ReservationCardComponent
  ],
  imports: [
    CommonModule,
    MatButtonToggleModule,
    MatButtonModule,
    ReactiveFormsModule,
    ReviewFormModule,
    MatPaginatorModule
  ]
})
export class ReservationListModule { }
