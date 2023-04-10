import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewFormComponent } from './review-form.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [ ReviewFormComponent ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ]
})
export class ReviewFormModule { }
