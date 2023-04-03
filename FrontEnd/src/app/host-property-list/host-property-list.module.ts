import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HostPropertyListComponent } from './host-property-list.component';
import { HostPropertyCardComponent } from './host-property-card/host-property-card.component';
import { PropertyFormComponent } from './property-form/property-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    HostPropertyListComponent,
    HostPropertyCardComponent,
    PropertyFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    NgbModalModule,
    MatInputModule
  ]
})
export class HostPropertyListModule { }
