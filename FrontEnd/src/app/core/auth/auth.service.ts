import { Injectable, OnInit } from '@angular/core';
import jwtDecode, { JwtPayload } from 'jwt-decode';

// This is the interface for the payload of the token (temp)
interface ITokenPayload extends JwtPayload {
  user_type?: 'user' | 'host';
  email?: string;
  name?: string;
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
      "user_type": "user",
      "name": "Zain Zafar",
      "iat": 1716239022
    }
   */
  private _token: string = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX3R5cGUiOiJ1c2VyIiwibmFtZSI6IlphaW4gWmFmYXIiLCJpYXQiOjE3MTYyMzkwMjJ9.UmOOc4Qinh8TWmKTiA2BQIZimOwJnL3O2qL0OAEIPto';
  private async _mockLogin(): Promise<string> {
    return this._token;
  }

  private _payload: ITokenPayload | null = null;

  constructor() { }
  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        this._payload = jwtDecode<ITokenPayload>(token);
      } catch {
        localStorage.removeItem('token');
      }
    }
  }

  async login(): Promise<void> {
    try {
      const token = await this._mockLogin();
      this._payload = jwtDecode<JwtPayload>(token);
      if (!this.isValid(this._payload)) {
        throw new Error('Invalid token');
      }
      localStorage.setItem('token', token);
    } catch (err) {
      this.logout();
      console.error(err);
    }
  }

  logout(): void {
    localStorage.removeItem('token');
    this._payload = null;
  }

  get isLoggedIn(): boolean {
    return !!this._payload;
  }

  get userName(): string | undefined {
    if (!this._payload) {
      return undefined;
    }
    return this._payload.name;
  }

  get userType(): string | undefined {
    if (!this._payload) {
      return undefined;
    }
    return this._payload.user_type;
  }

  get isHost(): boolean {
    return this.userType === 'host';
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
