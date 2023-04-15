import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FilterService } from 'src/app/core/filter/filter.service';
@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {
  constructor(
    public activeModal: NgbActiveModal,
    private filterService: FilterService
  ) {}
  filterGroup: FormGroup;
  
  showPrice(value: number): string {
    return value + '$';
  }
  ngOnInit(): void {
    this.filterGroup = new FormGroup({
      minPrice: new FormControl(this.filterService.minPrice),
      maxPrice: new FormControl(this.filterService.maxPrice),
      category: new FormControl(this.filterService.category)
    });
  }
  dismiss() {
    this.activeModal.close();
  }

  applyFilter() {
    this.filterService.minPrice = this.filterGroup.get('minPrice')!.value;
    this.filterService.maxPrice = this.filterGroup.get('maxPrice')!.value;
    this.filterService.category = this.filterGroup.get('category')!.value;
    this.activeModal.close();
  }

  clearFilter() {
    this.filterService.clearFilter();
    this.activeModal.close('clear');
  }
}
