import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service';

interface IProperty {
  location: string,
  prix: number,
  area: number
}
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {
  public properties: IProperty[] = [
    {
      location: 'Paris',
      prix: 100,
      area: 100
    },
    {
      location: 'Lyon',
      prix: 200,
      area: 50
    }
  ];
  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
  }

  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }
}
