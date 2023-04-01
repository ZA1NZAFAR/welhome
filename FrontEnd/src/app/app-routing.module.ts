import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppGuard } from './app.guard'
import { HostPropertyListComponent } from './host-property-list/host-property-list.component';
import { PropertiesComponent } from './properties/properties.component';
import { PropertyListComponent } from './property-list/property-list.component';
import { ReservationListComponent } from './reservation-list/reservation-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'properties', pathMatch: 'full' },
  { path: 'properties', 
    component: PropertyListComponent
  },
  { path: 'properties/:id', component: PropertiesComponent },
  { path: 'myproperties',
    component: HostPropertyListComponent,
    canActivate: [ AppGuard ]
  },
  { path: 'myreservations',
    component: ReservationListComponent,
    canActivate: [ AppGuard ] },
  { path: '**', redirectTo: 'properties' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
