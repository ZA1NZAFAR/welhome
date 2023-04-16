import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { ToastService } from 'src/app/utils/toast/toast.service';
import { AuthService } from '../auth/auth.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(
    private toastService: ToastService,
    private authService: AuthService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(catchError((error: HttpErrorResponse) => {
      switch (error.status) {
        case 401:
          this.authService.refreshToken();
          break;
        case 403:
          this.toastService.showError('You are not authorized to perform this action');
          break;
        case 404:
          console.log('Not found', error);
          break;
        case 500:
          this.toastService.showError('Internal server error');
          break;
        default:
          this.toastService.showError('Unknown error occurred');
      }
      throw error;
    }));
  }
}
