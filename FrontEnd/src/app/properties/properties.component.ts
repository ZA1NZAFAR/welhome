import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap'
import { Subscription, map } from 'rxjs';
import { IProperty } from 'src/app/core/property/property.model';
import { PropertyService } from 'src/app/core/property/property.service';
import { PropertyFormComponent } from '../property-form/property-form.component'
import { Location } from '@angular/common';
import { AuthService } from '../core/auth/auth.service'
@Component({
  selector: 'app-properties',
  templateUrl: './properties.component.html',
  styleUrls: ['./properties.component.scss']
})
export class PropertiesComponent implements OnInit, OnDestroy {
  propertyData: IProperty;

  user_email: string;
  getPropertySub$: Subscription;
  paramsSub$: Subscription;

  constructor(
    private propertyService: PropertyService,
    private route: ActivatedRoute,
    private modalService: NgbModal,
    private locationService: Location,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.paramsSub$ = this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id === null) {
        this.locationService.back();
        return;
      }
      this.getPropertySub$ = this.propertyService.getProperties().subscribe(properties => {
        const property = properties.find(p => p.id === id);
        if (property === undefined) {
          this.locationService.back();
          return;
        }
        this.propertyData = property;
      });
    });
    console.log(this.authService.profile?.email)
    this.user_email = this.authService.profile?.email || '';
  }

  get isOwner(): boolean {
    return this.user_email === this.propertyData.owner_email;
  }

  ngOnDestroy(): void {
    this.getPropertySub$.unsubscribe();
    this.paramsSub$.unsubscribe();
  }

  editProperty(): void {
    const modal = this.modalService.open(PropertyFormComponent);
    modal.componentInstance.selectedProperty = this.propertyData;
    modal.componentInstance.owner_email = this.user_email;
  }

  deleteProperty(): void {
    this.propertyService.deleteProperty(this.propertyData.id).subscribe().unsubscribe();
  }

  nextImage(): void {

  }
  /*
  $(document).ready(function() {
    var currentPhoto = 1;
    var numPhotos = $('.small-photo').length;

    function showPhoto(photoNum) {
      $('.main-photo').attr('src', $('.small-photo:eq(' + (photoNum-1) + ')').attr('src'));
      $('.small-photo').removeClass('active');
      $('.small-photo:eq(' + (photoNum-1) + ')').addClass('active');
    }

    $('.small-photo').click(function() {
      currentPhoto = $(this).index() + 1;
      showPhoto(currentPhoto);
    });

    $('#next-btn').click(function() {
      currentPhoto++;
      if (currentPhoto > numPhotos) {
        currentPhoto = 1;
      }
      showPhoto(currentPhoto);
    });

    showPhoto(currentPhoto);
  });
  */
}
