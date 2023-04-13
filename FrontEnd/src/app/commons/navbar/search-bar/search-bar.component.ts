import { Component, OnDestroy, OnInit } from '@angular/core';
import { FilterComponent } from './filter/filter.component';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FilterService } from 'src/app/core/filter/filter.service';
import { PropertyService } from 'src/app/core/property/property.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss']
})
export class SearchBarComponent implements OnInit, OnDestroy {

  cities: string[];

  propertySubscription: Subscription;

  constructor(
    private modalService: NgbModal,
    private filterService: FilterService,
    private propertyService: PropertyService
  ) { }

  ngOnInit(): void {
    this.propertySubscription = this.propertyService.getPropertyObservable().subscribe((data) => {
        this.cities = data.map(p => p.city);
      }
    );
  }

  ngOnDestroy(): void {
    this.propertySubscription.unsubscribe();
  }
  openFilterModal() {
    const modalRef = this.modalService.open(FilterComponent);
  }

  applyFilter() {
    this.filterService.applyFilter();
  }

}
