import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/auth/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }

  login(): void {
    if (!environment.production) {
      this.authService.login();
      return;
    }
    const loginWindow = window.open(environment.authUrl, 'Authentication', 'location=yes,height=300,width=300,scrollbars=yes,status=yes');
    if (loginWindow !== null) {
      loginWindow.focus();
      window.addEventListener('message', event => {
        const data = event.data;
        if (data.token !== undefined) {
          this.authService.login(data.token);
          loginWindow.close();
        }
      });
    }
    
  }

  logout(): void {
    this.authService.logout();
  }

}
