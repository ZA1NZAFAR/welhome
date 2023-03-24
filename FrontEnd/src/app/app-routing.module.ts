import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component'
import { HostsComponent } from './hosts/hosts.component'

const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'hosts', component: HostsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
