import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-review-modal',
  templateUrl: './review-modal.component.html',
  styleUrls: ['./review-modal.component.scss']
})
export class ReviewModalComponent implements OnInit {
  @Input() image: string;

  constructor() { }

  ngOnInit(): void {
  }

}
