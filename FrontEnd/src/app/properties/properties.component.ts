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

}
