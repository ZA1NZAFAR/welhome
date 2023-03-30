import { Component, Input, OnInit } from '@angular/core';
import { IProperty } from 'src/app/core/property/property.model'

@Component({
  selector: 'app-host-property-card',
  templateUrl: './host-property-card.component.html',
  styleUrls: ['./host-property-card.component.scss']
})
export class HostPropertyCardComponent implements OnInit {

  @Input() property: IProperty;

  constructor() { }

  ngOnInit(): void {
  }

}
