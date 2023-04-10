import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationListComponent } from './reservation-list.component';
import { ReservationCardComponent } from './reservation-card/reservation-card.component';

@NgModule({
  declarations: [
    ReservationListComponent,
    ReservationCardComponent
  ],
  imports: [
    CommonModule
  ]
})
export class ReservationListModule { }
