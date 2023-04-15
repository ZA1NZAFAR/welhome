import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service'
import { IProperty } from '../core/property/property.model'
import { PropertyService } from '../core/property/property.service'
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subscription } from 'rxjs';
import { PropertyFormComponent } from '../property-form/property-form.component'
import { ReviewService } from '../core/review/review.service';
import { IReview } from '../core/review/review.model';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-host-property-list',
  templateUrl: './host-property-list.component.html',
  styleUrls: ['./host-property-list.component.scss']
})
export class HostPropertyListComponent implements OnInit, OnDestroy {
  propertyMap: Map<number, IProperty> = new Map();
  properties: IProperty[] = [];
  propertyShow: IProperty[] = [];

  length: number = 0;

  pageIndex = 0;
  pageSizeOptions = [5, 10, 15];
  pageSize = this.pageSizeOptions[0];

  pageEvent: PageEvent;

  reviewMap: Map<number, IReview[]> = new Map();

  ownerPropertyLoadingObservable$: Observable<boolean>;

  private getOwnerPropertiesSub$: Subscription;
  private reviewSub$: Subscription;

  constructor(
    private propertyService: PropertyService,
    private authService: AuthService,
    private modalService: NgbModal,
    private reviewService: ReviewService
  ) { }
  ngOnDestroy(): void {
    this.getOwnerPropertiesSub$.unsubscribe();
    this.reviewSub$.unsubscribe();
  }

  ngOnInit(): void {
    const userEmail = this.authService.profile!.email;
    this.ownerPropertyLoadingObservable$ = this.propertyService.getOwnerPropertyLoadingObservable();
    this.propertyService.getOwnerProperties(userEmail);
    this.getOwnerPropertiesSub$ = this.propertyService.getOwnerPropertyObservable().subscribe((properties) => {
      console.log(properties);
      this.propertyMap.clear();
      properties.forEach((property: IProperty) => {
        if (property.ownerEmail === userEmail) {
          this.propertyMap.set(property.id, property);
        }
      });
      this.properties = Array.from(this.propertyMap.values());
      this.length = this.properties.length;
      this.propertyShow = this.properties.slice(this.pageIndex * this.pageSize, (this.pageIndex + 1) * this.pageSize);
    });
    this.reviewSub$ = this.reviewService.getReviews().subscribe((reviews) => {
      this.reviewMap.clear();
      reviews.forEach((review) => {
        if (this.reviewMap.has(review.propertyId)) {
          this.reviewMap.get(review.propertyId)!.push(review);
        } else {
          this.reviewMap.set(review.propertyId, [review]);
        }
      });
    });
  }

  openAddForm() {
    const modal = this.modalService.open(PropertyFormComponent, { size: 'xl' });
    modal.componentInstance.ownerEmail = this.authService.profile?.email;
  }

  handlePageEvent(event: PageEvent) {
    this.pageEvent = event;
    this.length = event.length;
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.propertyShow = this.properties.slice(this.pageIndex * this.pageSize, (this.pageIndex + 1) * this.pageSize);
  }
}
