import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppGuard } from './app.guard'
import { HostPropertyListComponent } from './host-property-list/host-property-list.component';
import { PropertyListComponent } from './property-list/property-list.component';
import { ReservationListComponent } from './reservation-list/reservation-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'homepage', pathMatch: 'full' },
  { path: 'homepage', component: PropertyListComponent },
  { path: 'myproperties',
    component: HostPropertyListComponent,
    canActivate: [ AppGuard ]
  },
  { path: 'myreservations',
    component: ReservationListComponent,
    canActivate: [ AppGuard ] },
  { path: '**', redirectTo: 'homepage' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
