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
  }

  get isHost(): boolean {
    return this.authService.isHost;
  }

  async login(): Promise<void> {
    await this.authService.login();
  }

  logout(): void {
    this.authService.logout();
  }

}
