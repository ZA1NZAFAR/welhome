import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ReviewService } from '../core/review/review.service';
import { IReview } from '../core/review/review.model';
import jwt_decode from 'jwt-decode';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {AuthService} from "../core/auth/auth.service";

@Component({
  selector: 'app-review-form',
  templateUrl: './review-form.component.html',
  styleUrls: ['./review-form.component.scss']
})
export class ReviewFormComponent implements OnInit {
  propertyId: number;
  reviewForm: FormGroup;
  rating: number;
  reviewAdded = false;

  constructor(
    public modal: NgbActiveModal,
    public authService: AuthService,
    private formBuilder: FormBuilder,
    private reviewService: ReviewService
  ) { }

  ngOnInit(): void {
    this.rating = 3; // Set the default rating to 3 stars
    this.reviewForm = this.formBuilder.group({
      rating: [null, Validators.required],
      review_text: ['', Validators.required],
      image: ['']
    });
  }

  onSubmit(): void {
    console.log("hello");
    // const token = localStorage.getItem('access_token');
    // // @ts-ignore
    // const decodedToken: any = jwt_decode(token);
    // const userEmail = decodedToken.email;

    const newReview: IReview = {
      id: 0, // will be set below
      rating: this.reviewForm.value.rating,
      review_text: this.reviewForm.value.review_text,
      published_date: new Date(),
      image: this.reviewForm.value.image,
      property_id: this.propertyId,
      reviewer_email: this.authService.profile!.email
    };

    // Get the next available ID for the new review
    this.reviewService.getPropertyReviews(this.propertyId).pipe(
      map((reviews: IReview[]) => {
        const ids = reviews.map(review => review.id);
        return ids.length > 0 ? Math.max(...ids) + 1 : 1;
      })
    ).subscribe(id => {
      newReview.id = id;

      this.reviewService.addReview(newReview).subscribe(() => {
        this.reviewAdded = true; // set reviewAdded to true
        setTimeout(() => {
          this.modal.close('success');
        }, 2000);
      });
    });
  }


  handleImageUpload(event: Event) {
    const target = event.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];

    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.reviewForm.patchValue({
        image: reader.result
      });
    };
  }

}
