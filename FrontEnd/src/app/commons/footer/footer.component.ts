import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AboutUsComponent } from './about-us/about-us.component';
import { PolicyComponent } from './policy/policy.component';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  constructor(
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
  }
  openAboutUs(): void {
    this.modalService.open(AboutUsComponent, { size: 'lg' });
  }
  openPolicy(): void {
    this.modalService.open(PolicyComponent, { size: 'lg' });
  }
}
