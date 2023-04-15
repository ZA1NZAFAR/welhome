import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastService } from 'src/app/utils/toast/toast.service';
import { AuthService } from '../auth/auth.service';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IReview } from './review.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(
    private http: HttpClient,
    private toastService: ToastService,
    private authService: AuthService
  ) {
  }
  getPropertyReviews(propertyId: number): Observable<IReview[]> {
    return this.getReviews().pipe(map((reviews) => {
        return reviews.filter((review) => review.propertyId === propertyId);
      }
    ));
  }

  getReviews(): Observable<IReview[]> {
    return this.http.get<IReview[]>(`${environment.backEndUrl}/reviews`);
  }

  addReview(review: IReview): Observable<IReview> {
    return this.http.post<IReview>(`${environment.backEndUrl}/reviews`, review).pipe(map((review) => {
      this.toastService.showSuccess('Review added successfully');
      return review;
    }));
  }

  editReview(review: IReview): Observable<IReview> {
    if (!this.authService.isLoggedIn) {
      this.toastService.showError('User not logged in');
      throw new Error('User not logged in');
    }
    const userEmail = this.authService.profile!.email;
    if (userEmail !== review.reviewerEmail) {
      this.toastService.showError('User not authorized to edit this review');
      throw new Error('User not authorized to edit this review');
    }
    return this.http.put<IReview>(`${environment.backEndUrl}/reviews/${review.id}`, review).pipe(map((review) => {
      this.toastService.showSuccess('Review updated successfully');
      return review;
    }));
  }

  deleteReview(review: IReview): Observable<IReview> {
    if (!this.authService.isLoggedIn) {
      this.toastService.showError('User not logged in');
      throw new Error('User not logged in');
    }
    const userEmail = this.authService.profile!.email;
    if (userEmail !== review.reviewerEmail) {
      this.toastService.showError('User not authorized to delete this review');
      throw new Error('User not authorized to delete this review');
    }
    return this.http.delete<IReview>(`${environment.backEndUrl}/reviews/${review.id}`).pipe(map((review) => {
      this.toastService.showSuccess('Review deleted successfully');
      return review;
    }));
  }
}