import { Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import jwtDecode, { JwtPayload } from 'jwt-decode';
import { IProfile, ITokenPayload } from './auth.model'
import { environment } from 'src/environments/environment';
import { ToastService } from 'src/app/utils/toast/toast.service'
import { ContextService } from '../context/context.service';

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

  private _payload: ITokenPayload | null = null;

  constructor(
    private router: Router,
    private toast: ToastService,
    private contextService: ContextService
  ) { }
  ngOnInit(): void {
  }

  startupToken(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this._payload = jwtDecode<ITokenPayload>(token);
    }
  }

  login(): void {
    if (!environment.production) {
      localStorage.setItem('token', this._token);
      this._payload = jwtDecode<ITokenPayload>(this._token);
      this.toast.showSuccess('Logged in');
      return;
    }
    const loginWindow = window.open(`${environment.authUrl}/auth/google`, 'Authentication', 'height=800,width=600');
    if (loginWindow !== null) {
      loginWindow.focus();
      window.addEventListener('message', event => {
        if (event.source !== loginWindow) {
          return;
        }
        const data = event.data.data;
        if (data.accessToken !== undefined) {
          try {
            this._payload = jwtDecode<ITokenPayload>(data.accessToken);
            if (!this.isValid(this._payload)) {
              this.toast.showError('Invalid token');
              throw new Error('Invalid token');
            }
            localStorage.setItem('token', data.accessToken);
            this.toast.showSuccess('Logged in');
          } catch (err) {
            this.logout();
            console.error(err);
          }
          loginWindow.close();
        }
      });
    }
    
  }

  get profile(): IProfile | null {
    if (!this._payload) {
      return null;
    }
    return {
      email: this._payload.email,
      first_name: this._payload.first_name,
      last_name: this._payload.last_name,
      birth_date: new Date(this._payload.birth_date),
      phone_number: this._payload.phone_number,
      gender: this._payload.gender
    }
  }

  get token(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    this.toast.showInfo('Logged out');
    this.contextService.setContext('RENTER')
    localStorage.removeItem('token');
    this._payload = null;
    this.router.navigate(['/']);
  }

  get isLoggedIn(): boolean {
    return !!this._payload;
  }


  get isExpired(): boolean {
    if (!this.isValid(this._payload)) {
      return true;
    }
    this.logout();
    return false;
  }

  isValid(payload: ITokenPayload | null): boolean {
    if (!payload) {
      return false
    }
    if (!payload.iat || !payload.exp) {
      return false
    }
    const tokenIssuedDateIsValid = payload.iat < Date.now() / 1000;
    const tokenIsNotExpired = payload.exp > Date.now() / 1000;
    if (!tokenIssuedDateIsValid || !tokenIsNotExpired) {
      return false
    }
    return true;
  }
}
