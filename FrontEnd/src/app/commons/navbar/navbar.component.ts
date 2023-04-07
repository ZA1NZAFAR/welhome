import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/auth/auth.service';
import { ContextService } from 'src/app/core/context/context.service'

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private contextService: ContextService
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

  get changeContextText(): string {
    return this.contextService.isRenter ? 'Switch to Owner View' : 'Switch to Renter View';
  }

  changeContext(): void {
    this.contextService.changeContext();
  }

}
