import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FilterService {
  minPrice: number = 0;
  maxPrice: number = 3000;
  category: string[] = ['House', 'Apartment', 'Room'];
  cities: string[];


  constructor() { }

  applyFilter() {

  }

  clearFilter() {
    this.minPrice = 0;
    this.maxPrice = 3000;
    this.category = ['House', 'Apartment', 'Room'];
    this.cities = [];
  }
}
