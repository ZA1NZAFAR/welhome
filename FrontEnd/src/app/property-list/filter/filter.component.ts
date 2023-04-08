import { Component, OnInit, TemplateRef } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IProperty, PropertyCategory, propertyCategory } from 'src/app/core/property/property.model';
import { PropertyService } from 'src/app/core/property/property.service';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

  modalRef: NgbModalRef;
  types: string [] = propertyCategory;
  rangePrixMin: number = 0;
  rangePrixMax: number = 1000;
  prixMin: number = 0;
  prixMax: number = 1000;


  constructor(
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {
  }

  openFilterModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.open(template);
  }

  onPrixRangeChange(event: any) {
    this.rangePrixMin = event.target.value;
    this.rangePrixMax = event.target.value;

  }
  

  onPrixMinChange(event: any) {
    this.rangePrixMin = parseInt(event.target.value);
    if (this.rangePrixMin > this.rangePrixMax) {
      this.rangePrixMax = this.rangePrixMin;
    }
  }
  

 onPrixMaxChange(event: any) {
    this.rangePrixMax = parseInt(event.target.max) - parseInt(event.target.value);
    if (this.rangePrixMax < this.rangePrixMin) {
      this.rangePrixMin = this.rangePrixMax;
    }
}

  
  
  
}
