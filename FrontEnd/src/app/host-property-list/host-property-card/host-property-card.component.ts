import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IProperty } from 'src/app/core/property/property.model'
import { PropertyService } from 'src/app/core/property/property.service'

@Component({
  selector: 'app-host-property-card',
  templateUrl: './host-property-card.component.html',
  styleUrls: ['./host-property-card.component.scss']
})
export class HostPropertyCardComponent implements OnInit {

  @Input() property: IProperty;

  constructor(
    private router: Router,
    private propertyService: PropertyService
  ) { }

  ngOnInit(): void {
  }

  goToProperty() {
    this.router.navigate(['/properties', this.property.id]);
  }

  deleteProperty() {
    this.propertyService.deleteProperty(this.property.id).subscribe();

  }
}
