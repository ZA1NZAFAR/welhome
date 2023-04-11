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
  @Input() ownerEmail: string;

  @Input() selectedProperty: IProperty;

  imageIsFile = {
    imageUrl1: false,
    imageUrl2: false,
    imageUrl3: false
  };

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
      propertyCategory: new FormControl('House', [Validators.required, this.categoryValidator]),
      address: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required]),
      state: new FormControl(''),
      country: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required, Validators.min(0)]),
      surfaceArea: new FormControl('', [Validators.required, Validators.min(0)]),
      floors: new FormControl('', [Validators.required]),
      capacity: new FormControl('', [Validators.required, Validators.min(0)]),
      constructionDate: new FormControl(''),
      imageUrl1: new FormControl(''),
      imageUrl2: new FormControl(''),
      imageUrl3: new FormControl('')
    });

    if (this.selectedProperty) {
      this.propertyGroup.patchValue(this.selectedProperty);
    }
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
      const constructionDate = new Date(this.propertyGroup.controls['constructionDate'].value).toISOString();
      const property: IProperty = {
        ...this.propertyGroup.value,
        constructionDate,
        ownerEmail: this.ownerEmail,
        id: this.selectedProperty?.id
      };
      console.log(property);
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

  handleImageUpload(event: Event, control: 'imageUrl1' | 'imageUrl2' | 'imageUrl3') {
    const target = event.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];

    const reader = new FileReader();
    reader.readAsDataURL(file);
    console.log(reader);
    reader.onload = () => {
      this.propertyGroup.controls[control].setValue(reader.result);
    };
  }

  toggleUploadImage(control: 'imageUrl1' | 'imageUrl2' | 'imageUrl3') {
    this.propertyGroup.controls[control].setValue('');
    this.imageIsFile[control] = !this.imageIsFile[control];
  }
}
