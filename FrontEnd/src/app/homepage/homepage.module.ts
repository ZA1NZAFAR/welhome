import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchBarComponent } from './search-bar/search-bar.component';
import { HomepageComponent } from './homepage.component'



@NgModule({
  declarations: [
    SearchBarComponent,
    HomepageComponent
  ],
  imports: [
    CommonModule
  ]
})
export class HomepageModule { }
