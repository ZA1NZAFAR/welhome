import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IProfile } from './auth.model';
import { environment } from 'src/environments/environment';
import { ToastService } from 'src/app/utils/toast/toast.service'
import { ContextService } from '../context/context.service';
import { HttpClient } from '@angular/common/http';

interface IRefreshToken {
  accessToken: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnInit {
  /**
   * The mock token to test stuffs
   * Generated via https://jwt.io/
   * header:
    {
      "alg": "HS256",
      "typ": "JWT"
    }
   * payload:
    {
      "email": "zain.zafar@gmail.com",
      "first_name": "Zain",
      "last_name": "Zafar",
      "birth_date": "01/01/2000",
      "phone_number": "0123456789",
      "gender": "Male",
      "iat": 1675239022,
      "exp": 1716239022
    }
   */
  private get _email(): string {
    return localStorage.getItem('email') || '';
  };

  constructor(
    private router: Router,
    private toast: ToastService,
    private contextService: ContextService,
    private http: HttpClient
  ) { }
  ngOnInit( ): void {
    setInterval(this.refreshToken, 5 * 60 * 1000);
  }
  refreshToken() {
      this.http.get<IRefreshToken>(`${environment.authUrl}/auth/refresh-token`).subscribe((data) => {
        localStorage.setItem('token', data.accessToken);
        localStorage.setItem('email', data.email);
      });
  }
  
  startupToken(): void {
    const token = localStorage.getItem('token');
  }

  login(): void {
    const loginWindow = window.open(`${environment.authUrl}/auth/google`, 'Authentication', 'height=800,width=600');
    if (loginWindow !== null) {
      //loginWindow.focus();
      window.addEventListener('message', event => {
        if (event.source !== loginWindow) {
          return;
        }
        const data = event.data.data;
        if (data.access_token !== undefined && data.email !== undefined) {
          localStorage.setItem('token', data.access_token);
          localStorage.setItem('email', data.email);
          this.toast.showSuccess('Logged in');
        }
        else {
          this.toast.showError('Login failed');
        }
        loginWindow.close();
      }, { once: false });
    }
    
  }

  get profile(): IProfile | null {
    if (!this._email) {
      return null;
    }
    return {
      email: this._email
    }
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    this.toast.showInfo('Logged out');
    this.contextService.setContext('RENTER')
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    this.router.navigate(['/']);
  }

  get isLoggedIn(): boolean {
    return !!this.token;
  }
}
