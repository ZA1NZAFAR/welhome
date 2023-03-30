import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service';
import { IProperty } from '../core/property/property.model';
import { PropertyService } from '../core/property/property.service';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss']
})
export class PropertyListComponent implements OnInit {
  properties: IProperty[] = [];
  constructor(
    private propertyService: PropertyService
  ) { }

  ngOnInit(): void {
    this.propertyService.getProperties().subscribe((properties: IProperty[]) => {
      this.properties = properties;
    });
  }
}
