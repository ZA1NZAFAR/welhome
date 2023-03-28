import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { PropertyListComponent } from './property-list.component';
import { PropertyCardComponent } from './property-card/property-card.component';

@NgModule({
  declarations: [
    SearchBarComponent,
    PropertyListComponent,
    PropertyCardComponent
  ],
  imports: [
    CommonModule
  ]
})
export class PropertyListModule { }
