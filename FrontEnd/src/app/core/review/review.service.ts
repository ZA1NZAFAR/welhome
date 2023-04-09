import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastService } from 'src/app/utils/toast/toast.service';
import { AuthService } from '../auth/auth.service';
import { BehaviorSubject, Observable, Subject, Subscription, catchError, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IReview } from './review.model';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private reviewSubject: Subject<IReview[]>;
  private reviewLoadingSubject: BehaviorSubject<boolean>;
  private reviewObservable: Observable<IReview[]>;
  private reviewLoadingObservable: Observable<boolean>;
  private reviewSubscription: Subscription;

  constructor(
    private http: HttpClient,
    private toastService: ToastService,
    private authService: AuthService
  ) {
    this.reviewSubject = new Subject<IReview[]>();
    this.reviewLoadingSubject = new BehaviorSubject<boolean>(false);
    this.reviewObservable = this.reviewSubject.asObservable();
    this.reviewLoadingObservable = this.reviewLoadingSubject.asObservable();
  }

  getReviews(propertyId: number): ReviewService {
    if (this.reviewSubscription) {
      this.reviewSubscription.unsubscribe();
    }
    this.reviewLoadingSubject.next(true);
    this.reviewSubscription = this.http.get<IReview[]>(`${environment.backEndUrl}/reviews?property_id=${propertyId}`).subscribe((reviews) => {
      this.reviewSubject.next(reviews);
      this.reviewLoadingSubject.next(false);
    });
    return this;
  }

  addReview(review: IReview): Observable<IReview> {
    return this.http.post<IReview>(`${environment.backEndUrl}/reviews`, review).pipe(map((review) => {
      this.toastService.showSuccess('Review added successfully');
      return review;
    }), catchError((error: Error) => {
      this.toastService.showError('Error while adding review :' + error.message);
      throw error;
    }));
  }

  editReview(review: IReview): Observable<IReview> {
    if (!this.authService.isLoggedIn) {
      this.toastService.showError('User not logged in');
      throw new Error('User not logged in');
    }
    const userEmail = this.authService.profile!.email;
    if (userEmail !== review.reviewer_email) {
      this.toastService.showError('User not authorized to edit this review');
      throw new Error('User not authorized to edit this review');
    }
    return this.http.put<IReview>(`${environment.backEndUrl}/reviews/${review.id}`, review).pipe(map((review) => {
      this.toastService.showSuccess('Review updated successfully');
      return review;
    }), catchError((error: Error) => {
      this.toastService.showError('Error while updating review :' + error.message);
      throw error;
    }));
  }

  deleteReview(review: IReview): Observable<IReview> {
    if (!this.authService.isLoggedIn) {
      this.toastService.showError('User not logged in');
      throw new Error('User not logged in');
    }
    const userEmail = this.authService.profile!.email;
    if (userEmail !== review.reviewer_email) {
      this.toastService.showError('User not authorized to delete this review');
      throw new Error('User not authorized to delete this review');
    }
    return this.http.delete<IReview>(`${environment.backEndUrl}/reviews/${review.id}`).pipe(map((review) => {
      this.toastService.showSuccess('Review deleted successfully');
      return review;
    }), catchError((error: Error) => {
      this.toastService.showError('Error while deleting review :' + error.message);
      throw error;
    }));
  }
}
