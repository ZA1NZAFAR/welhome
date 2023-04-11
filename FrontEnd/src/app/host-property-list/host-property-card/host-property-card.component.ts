import { Component, Input, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/core/auth/auth.service';
import { IProperty } from 'src/app/core/property/property.model'
import { PropertyService } from 'src/app/core/property/property.service'
import { IReview } from 'src/app/core/review/review.model';
import { PropertyFormComponent } from 'src/app/property-form/property-form.component';

@Component({
  selector: 'app-host-property-card',
  templateUrl: './host-property-card.component.html',
  styleUrls: ['./host-property-card.component.scss']
})
export class HostPropertyCardComponent implements OnInit {

  @Input() property: IProperty;
  @Input() reviews: IReview[] | undefined;

  rating: string;

  constructor(
    private propertyService: PropertyService,
    private modalService: NgbModal,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    if (this.reviews && this.reviews.length > 0) {
      let total = 0;
        this.reviews.forEach((review) => {
          total += review.rating;
        });
        this.rating = `${(total / this.reviews.length).toFixed(1)} (${this.reviews.length})`;
    } else {
      this.rating = 'No reviews yet';
    }
  }
  openEditForm() {
    const modal = this.modalService.open(PropertyFormComponent);
    modal.componentInstance.selectedProperty = this.property;
    modal.componentInstance.ownerEmail = this.authService.profile!.email;
  }



  deleteProperty() {
    this.propertyService.deleteProperty(this.property.id).subscribe();

  }
}
