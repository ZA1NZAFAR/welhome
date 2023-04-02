import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IProperty } from 'src/app/core/property/property.model'

@Component({
  selector: 'app-property-card',
  templateUrl: './property-card.component.html',
  styleUrls: ['./property-card.component.scss']
})
export class PropertyCardComponent implements OnInit {

  @Input() property: IProperty;

  constructor(
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  openDetails(): void {
    this.router.navigate(['properties', this.property.id]);
  }


}
