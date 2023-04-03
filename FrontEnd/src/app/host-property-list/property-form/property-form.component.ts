import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { propertyCategory } from 'src/app/core/property/property.model';
@Component({
  selector: 'app-property-form',
  templateUrl: './property-form.component.html',
  styleUrls: ['./property-form.component.scss']
})
export class PropertyFormComponent implements OnInit {
  propertyGroup: FormGroup;
  constructor(
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit(): void {
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
}
