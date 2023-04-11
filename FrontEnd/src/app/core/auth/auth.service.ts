import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import jwtDecode, { JwtPayload } from 'jwt-decode';
import { IProfile, ITokenPayload } from './auth.model'
import { environment } from 'src/environments/environment';
import { ToastService } from 'src/app/utils/toast/toast.service'
import { ContextService } from '../context/context.service';
import { HttpClient } from '@angular/common/http';

interface IRefreshToken {
  accessToken: string;
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
  private _token: string = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InphaW4uemFmYXJAZ21haWwuY29tIiwiZmlyc3RfbmFtZSI6IlphaW4iLCJsYXN0X25hbWUiOiJaYWZhciIsImJpcnRoX2RhdGUiOiIwMS8wMS8yMDAwIiwicGhvbmVfbnVtYmVyIjoiMDEyMzQ1Njc4OSIsImdlbmRlciI6Ik1hbGUiLCJpYXQiOjE2NzUyMzkwMjIsImV4cCI6MTcxNjIzOTAyMn0.IW6M5wQBx9iaYlMp1463W8KUMrn965H75mNVuX7Xptk';
  private async _mockLogin(): Promise<string> {
    return this._token;
  }

  private _email: string;

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
      this.http.get<IRefreshToken>('http://localhost:3001/auth/refresh-token').subscribe((data) => {
        localStorage.setItem('token', data.accessToken);
      });
  }
  
  startupToken(): void {
    const token = localStorage.getItem('token');
  }

  login(): void {
    const loginWindow = window.open(`${environment.authUrl}/auth/google`, 'Authentication', 'height=800,width=600');
    console.log('loginWindow', loginWindow)
    if (loginWindow !== null) {
      //loginWindow.focus();
      window.addEventListener('message', event => {
        console.log('event', event)
        if (event.source !== loginWindow) {
          return;
        }
        console.log('data', event.data)
        const data = event.data.data;
        if (data.accessToken !== undefined && data.email !== undefined) {
          localStorage.setItem('token', data.accessToken);
          this.toast.showSuccess('Logged in');
          this._email = data.email;
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
    this.router.navigate(['/']);
  }

  get isLoggedIn(): boolean {
    return !!this.token;
  }
}
