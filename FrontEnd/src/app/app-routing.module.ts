import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HostPropertyListComponent } from './host-property-list/host-property-list.component';
import { PropertyListComponent } from './property-list/property-list.component';
import { ReservationListComponent } from './reservation-list/reservation-list.component';

const routes: Routes = [
  { path: '', component: PropertyListComponent },
  { path: 'myproperties', component: HostPropertyListComponent },
  { path: 'myreservations', component: ReservationListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
