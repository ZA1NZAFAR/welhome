import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.authService.startupToken();
  }

  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }

  login(): void {
    this.authService.login();    
  }

  logout(): void {
    this.authService.logout();
  }

}
