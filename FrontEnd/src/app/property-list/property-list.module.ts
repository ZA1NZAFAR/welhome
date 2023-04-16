import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyListComponent } from './property-list.component';
import { PropertyCardComponent } from './property-card/property-card.component';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginatorModule } from '@angular/material/paginator'; 

@NgModule({
  declarations: [
    PropertyListComponent,
    PropertyCardComponent
  ],
  imports: [
    CommonModule,
    MatButtonModule,
    MatPaginatorModule
  ],
  providers: [ ]
})
export class PropertyListModule { }
