import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FilterService {
  private _minPrice: number = 0;
  private _maxPrice: number = 3000;
  private _category: string[] = ['House', 'Apartment', 'Room'];
  private _city: string = '';

  private _country: string = '';

  get minPrice(): number {
    return this._minPrice;
  }
  set minPrice(value: number) {
    this._minPrice = value;
  }

  get maxPrice(): number {
    return this._maxPrice;
  }
  set maxPrice(value: number) {
    this._maxPrice = value;
  }

  get category(): string[] {
    return this._category;
  }
  set category(value: string[]) {
    this._category = value;
  }

  get city(): string {
    return this._city;
  }
  set city(value: string) {
    this._city = value;
  }

  get country(): string {
    return this._country;
  }
  set country(value: string) {
    this._country = value;
  }

  constructor() { }

  clearFilter() {
    this._minPrice = 0;
    this._maxPrice = 3000;
    this._category = ['House', 'Apartment', 'Room'];
    this._city = '';
  }
}
