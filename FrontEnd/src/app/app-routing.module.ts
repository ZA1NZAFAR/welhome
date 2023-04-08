import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard, ContextGuard } from './app.guard'
import { HostPropertyListComponent } from './host-property-list/host-property-list.component';
import { PropertiesComponent } from './properties/properties.component';
import { PropertyListComponent } from './property-list/property-list.component';
import { ReservationListComponent } from './reservation-list/reservation-list.component';

const routes: Routes = [
  { path: '', component: PropertyListComponent },
  { path: 'properties/:id', component: PropertiesComponent },
  { path: 'myproperties',
    component: HostPropertyListComponent,
    canActivate: [ AuthenticationGuard, ContextGuard ],
    runGuardsAndResolvers: 'always',
  },
  { path: 'myreservations',
    component: ReservationListComponent,
    canActivate: [ AuthenticationGuard ],
    runGuardsAndResolvers: 'always', },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
