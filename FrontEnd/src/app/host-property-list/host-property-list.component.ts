import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service'
import { IProperty } from '../core/property/property.model'
import { PropertyService } from '../core/property/property.service'

@Component({
  selector: 'app-host-property-list',
  templateUrl: './host-property-list.component.html',
  styleUrls: ['./host-property-list.component.scss']
})
export class HostPropertyListComponent implements OnInit {
  properties: IProperty[] = [];

  constructor(
    private propertyService: PropertyService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.propertyService.getProperties().subscribe((properties: IProperty[]) => {
      const userEmail = this.authService.profile?.email;
      properties.forEach((property: IProperty) => {
        if (property.owner_email === userEmail) {
          this.properties.push(property);
        }
      });
    });
  }
}
