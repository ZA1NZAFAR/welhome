import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ReviewService } from '../core/review/review.service';
import { IReview } from '../core/review/review.model';
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
      reviewText: ['', Validators.required],
      image: ['']
    });
    if (this.review) {
      this.reviewForm.patchValue(this.review);
    }
    this.setRating(this.reviewForm.controls['rating'].value); 
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
      publishedDate: new Date(),
      propertyId: this.property.id,
      reviewerEmail: this.authService.profile!.email
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
}
