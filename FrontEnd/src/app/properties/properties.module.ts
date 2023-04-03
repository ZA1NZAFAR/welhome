import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertiesComponent } from './properties.component';
import { SlickCarouselModule } from 'ngx-slick-carousel';



@NgModule({
  declarations: [
    PropertiesComponent
  ],
  imports: [
    CommonModule,
    SlickCarouselModule
  ]
})
export class PropertiesModule { }
