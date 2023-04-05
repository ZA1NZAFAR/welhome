import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DateAdapter, MAT_DATE_LOCALE } from '@angular/material/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IProperty, propertyCategory } from 'src/app/core/property/property.model';
import * as _moment from 'moment';
import { PropertyService } from 'src/app/core/property/property.service';
@Component({
  selector: 'app-property-form',
  templateUrl: './property-form.component.html',
  styleUrls: ['./property-form.component.scss'],
  providers:[ { provide: MAT_DATE_LOCALE, useValue: 'fr' } ]
})
export class PropertyFormComponent implements OnInit {
  propertyGroup: FormGroup;
  @Input() owner_email: string;

  @Input() selectedProperty: IProperty;

  constructor(
    public activeModal: NgbActiveModal,
    private adapter: DateAdapter<any>,
    private propertyService: PropertyService
  ) { }

  ngOnInit(): void {
    this.adapter.setLocale('fr');
    this.propertyGroup = new FormGroup({
      title: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      property_category: new FormControl('', [Validators.required, this.categoryValidator]),
      address: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      state: new FormControl(''),
      country: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required, Validators.min(0)]),
      surface_area: new FormControl('', [Validators.required, Validators.min(0)]),
      floors: new FormControl('', [Validators.required]),
      capacity: new FormControl('', [Validators.required, Validators.min(0)]),
      construction_date: new FormControl(''),
      image_url: new FormControl('')
    });
  }

  categoryValidator(control: FormControl) {
    if (propertyCategory.includes(control.value)) {
      return null;
    }
    return { category: true };
  }

  closeModal() {
    this.activeModal.close();
  }

  submitForm() {
    if (this.propertyGroup.valid) {
      const property: IProperty = {
        ...this.propertyGroup.value,
        owner_email: this.owner_email,
        id: this.selectedProperty?.id
      };
      if (this.selectedProperty) {
        this.propertyService.updateProperty(property).subscribe(() => {
          this.activeModal.close();
        });
      } else {
        this.propertyService.addProperty(property).subscribe(() => {
          this.activeModal.close();
        });
      }
    }
  }
}
