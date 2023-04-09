import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/core/auth/auth.service';
import { IProperty } from 'src/app/core/property/property.model'
import { PropertyService } from 'src/app/core/property/property.service'
import { PropertyFormComponent } from 'src/app/property-form/property-form.component';

@Component({
  selector: 'app-host-property-card',
  templateUrl: './host-property-card.component.html',
  styleUrls: ['./host-property-card.component.scss']
})
export class HostPropertyCardComponent implements OnInit {

  @Input() property: IProperty;

  constructor(
    private router: Router,
    private propertyService: PropertyService,
    private modalService: NgbModal,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
  }
  openEditForm() {
    const modal = this.modalService.open(PropertyFormComponent);
    modal.componentInstance.selectedProperty = this.property;
    modal.componentInstance.owner_email = this.authService.profile!.email;
  }



  deleteProperty() {
    this.propertyService.deleteProperty(this.property.id).subscribe();

  }
}
