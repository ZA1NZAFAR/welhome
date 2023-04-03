import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { IProperty } from 'src/app/core/property/property.model';
import { PropertyService } from 'src/app/core/property/property.service';

@Component({
  selector: 'app-properties',
  templateUrl: './properties.component.html',
  styleUrls: ['./properties.component.scss']
})
export class PropertiesComponent implements OnInit {
  propertyData: IProperty;

  constructor(
    private propertyService: PropertyService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));
      if (id === null) {
        this.router.navigate(['properties']);
        return;
      }
      this.propertyService.getProperties().subscribe(properties => {
        const property = properties.find(p => p.id === id);
        if (property === undefined) {
          this.router.navigate(['properties']);
          return;
        }
        this.propertyData = property;
      });
    });
  }

  slideConfig = {
    slidesToShow: 3,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
    responsive: [
      {
        breakpoint: 768,
        settings: {
          slidesToShow: 2
        }
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow: 1
        }
      }
    ]
  };

  slides = [
    'https://media.istockphoto.com/id/1449364000/fr/photo/petite-chambre-de-style-minimaliste.jpg?s=612x612&w=is&k=20&c=LPjfaAJ5HPVU-CXvoaz14qNg13o8VVSuiQgEcPflu5Q=',
    'https://media.istockphoto.com/id/943709096/fr/photo/salon-int%C3%A9rieur-illustration-3d.jpg?s=1024x1024&w=is&k=20&c=r5sjY84dqpuowRgnGM-di1pmVeK2Kcd6eZIDkn8T3PE=',
    'https://media.istockphoto.com/id/1195597185/fr/photo/image-g%C3%A9n%C3%A9r%C3%A9e-par-ordinateur-du-salon-rendu-3d.jpg?s=1024x1024&w=is&k=20&c=iVxRtBR_k0CTUYyyivGJRckUC24ol1R_KtWzNx-Ty0U='
  ];

}
