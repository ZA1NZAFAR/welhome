import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { IProperty } from 'src/app/core/property/property.model';
import { PropertyService } from 'src/app/core/property/property.service';

@Component({
  selector: 'app-properties',
  templateUrl: './properties.component.html',
  styleUrls: ['./properties.component.scss']
})
export class PropertiesComponent implements OnInit, OnDestroy {
  propertyData: IProperty;

  getPropertySub$: Subscription;
  paramsSub$: Subscription;

  constructor(
    private propertyService: PropertyService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.paramsSub$ = this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id === null) {
        this.router.navigate(['properties']);
        return;
      }
      this.getPropertySub$ = this.propertyService.getProperties().subscribe(properties => {
        const property = properties.find(p => p.id === id);
        if (property === undefined) {
          this.router.navigate(['properties']);
          return;
        }
        this.propertyData = property;
      });
    });
  }

  ngOnDestroy(): void {
    this.getPropertySub$.unsubscribe();
    this.paramsSub$.unsubscribe();
  }

  deleteProperty(): void {
    this.propertyService.deleteProperty(this.propertyData.id).subscribe(() => {
      this.router.navigate(['properties']);
    });
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
