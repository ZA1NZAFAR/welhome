import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap'
import { Subscription, map } from 'rxjs';
import { IProperty } from 'src/app/core/property/property.model';
import { PropertyService } from 'src/app/core/property/property.service';
import { PropertyFormComponent } from '../property-form/property-form.component'
import { Location } from '@angular/common';
import { AuthService } from '../core/auth/auth.service'
import {IReview} from "../core/review/review.model";
import {ReviewService} from "../core/review/review.service";

@Component({
  selector: 'app-properties',
  templateUrl: './properties.component.html',
  styleUrls: ['./properties.component.scss']
})
export class PropertiesComponent implements OnInit, OnDestroy {
  propertyData: IProperty;
  reviewData: IReview[];

  user_email: string;
  getPropertySub$: Subscription;
  paramsSub$: Subscription;
  currentImageIndex: number = 0;
  images = [
    "https://media.istockphoto.com/id/1449364000/fr/photo/petite-chambre-de-style-minimaliste.jpg?s=612x612&w=is&k=20&c=LPjfaAJ5HPVU-CXvoaz14qNg13o8VVSuiQgEcPflu5Q=",
    "https://media.istockphoto.com/id/943709096/fr/photo/salon-int%C3%A9rieur-illustration-3d.jpg?s=1024x1024&w=is&k=20&c=r5sjY84dqpuowRgnGM-di1pmVeK2Kcd6eZIDkn8T3PE=",
    "https://media.istockphoto.com/id/1195597185/fr/photo/image-g%C3%A9n%C3%A9r%C3%A9e-par-ordinateur-du-salon-rendu-3d.jpg?s=1024x1024&w=is&k=20&c=iVxRtBR_k0CTUYyyivGJRckUC24ol1R_KtWzNx-Ty0U="
  ]


  constructor(
    private propertyService: PropertyService,
    private reviewService : ReviewService,
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
      this.getPropertySub$ = this.propertyService.getProperties().getPropertyObservable().subscribe(properties => {
        const property = properties.find(p => p.id === id);
        if (property === undefined) {
          this.locationService.back();
          return;
        }
        this.propertyData = property;
        this.reviewService.getPropertyReviews(property.id).subscribe(reviews => {
          const propertyReviews = reviews.filter(r => r.property_id === property.id);
          if (propertyReviews.length === 0) {
            return;
          }
          this.reviewData = propertyReviews;
        });

      });
    });
    this.user_email = this.authService.profile?.email || '';
    if (this.propertyData.image_url !== undefined) {
      this.images.unshift(this.propertyData.image_url);
    }

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
    this.propertyService.deleteProperty(this.propertyData.id).subscribe();
  }

  nextImage() {
    this.currentImageIndex++;
    if (this.currentImageIndex >= this.images.length) {
      this.currentImageIndex = 0;
    }
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
