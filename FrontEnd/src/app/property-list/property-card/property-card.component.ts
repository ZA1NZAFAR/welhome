import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-property-card',
  templateUrl: './property-card.component.html',
  styleUrls: ['./property-card.component.scss']
})
export class PropertyCardComponent implements OnInit {

  @Input() location: string = '';
  @Input() price: number = 0;
  @Input() area: number = 0;

  constructor() { }

  ngOnInit(): void {
  }

}
