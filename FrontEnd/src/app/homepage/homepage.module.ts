import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { HomepageComponent } from './homepage.component';
import { PropertyCardComponent } from './property-card/property-card.component';

@NgModule({
  declarations: [
    SearchBarComponent,
    HomepageComponent,
    PropertyCardComponent
  ],
  imports: [
    CommonModule
  ]
})
export class HomepageModule { }
