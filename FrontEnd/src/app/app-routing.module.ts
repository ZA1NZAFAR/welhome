import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HostPropertyListComponent } from './host-property-list/host-property-list.component';
import { PropertiesComponent } from './properties/properties.component';
import { PropertyListComponent } from './property-list/property-list.component';
import { ReservationListComponent } from './reservation-list/reservation-list.component';
import { authGuard, contextGuard } from './app.guard';

const routes: Routes = [
  { path: '', component: PropertyListComponent },
  { path: 'properties/:id', component: PropertiesComponent },
  { path: 'myproperties',
    component: HostPropertyListComponent,
    canActivate: [ authGuard, contextGuard ],
    runGuardsAndResolvers: 'always',
  },
  { path: 'myreservations',
    component: ReservationListComponent,
    canActivate: [ authGuard ],
    runGuardsAndResolvers: 'always', },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
