import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ReviewService } from '../core/review/review.service';
import { IReview } from '../core/review/review.model';
import jwt_decode from 'jwt-decode';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {AuthService} from "../core/auth/auth.service";
import { IProperty } from '../core/property/property.model';

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.scss']
})
export class ReviewFormComponent implements OnInit {
  @Input() property: IProperty;
  @Input() review: IReview;
  reviewForm: FormGroup;
  rating: number;

  constructor(
    public modal: NgbActiveModal,
    public authService: AuthService,
    private formBuilder: FormBuilder,
    private reviewService: ReviewService
  ) { }

  ngOnInit(): void {
    this.reviewForm = this.formBuilder.group({
      rating: [3, Validators.required], // Set the default rating to 3 stars
      review_text: ['', Validators.required],
      image: ['']
    });
    if (this.review) {
      this.reviewForm.patchValue(this.review);
    }
  }
  setRating(rating: number): void {
    this.rating = rating;
    this.reviewForm.controls['rating'].setValue(rating);
    const stars = document.querySelectorAll('.rating span');
    stars.forEach((star, index) => {
      if (index < rating) {
        star.classList.add('filled');
      } else {
        star.classList.remove('filled');
      }
    });
  }

  onSubmit(): void {
    const reviewData: IReview = {
      ...this.reviewForm.value,
      published_date: new Date(),
      property_id: this.property.id,
      reviewer_email: this.authService.profile!.email
    };
    if (!this.review) {
      this.reviewService.addReview(reviewData).subscribe(() => {
        this.modal.close();;
      });
    }
    else {
      this.reviewService.editReview(reviewData).subscribe(() => {
        this.modal.close();
      });
    }
    
  }



  handleImageUpload(event: Event) {
    const target = event.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];

    const reader = new FileReader();
    reader.readAsDataURL(file);
    console.log(reader);
    reader.onload = () => {
      this.reviewForm.patchValue({
        image: reader.result
      });
    };
  }

}
