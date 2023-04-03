import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service'
import { IProperty } from '../core/property/property.model'
import { PropertyService } from '../core/property/property.service'
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PropertyFormComponent } from './property-form/property-form.component';

@Component({
  selector: 'app-host-property-list',
  templateUrl: './host-property-list.component.html',
  styleUrls: ['./host-property-list.component.scss']
})
export class HostPropertyListComponent implements OnInit {
  properties: IProperty[] = [];

  constructor(
    private propertyService: PropertyService,
    private authService: AuthService,
    private modalService: NgbModal
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

  openAddForm() {
    const modal = this.modalService.open(PropertyFormComponent);
    modal.componentInstance.owner_email = this.authService.profile?.email;
  }
}
