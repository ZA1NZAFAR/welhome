import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IProperty } from './property.model';
import { BehaviorSubject, Subject } from 'rxjs';
import { AuthService } from '../auth/auth.service'

const mockProperties: IProperty[] = [
  {
    title: 'House in Paris',
    description: 'A beautiful house in Paris',
    property_catergory: 'House',
    address: '1, rue de la paix',
    city: 'Paris',
    state: 'Ile-de-France',
    country: 'France',
    price: 100,
    surface_area: 100,
    floors: 1,
    capacity: 2,
    owner_email: 'zain.zafar@gmail.com',
  },
  {
    title: 'Apartment in Lyon',
    description: 'A beautiful apartment in Lyon',
    property_catergory: 'Apartment',
    address: '1, rue General de Gaulle',
    city: 'Lyon',
    state: 'Auvergne-Rhône-Alpes',
    country: 'France',
    price: 200,
    surface_area: 50,
    floors: 5,
    capacity: 4,
    owner_email: 'abc@gmail.com'
  }
];

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private properties: IProperty[] = mockProperties;

  private propertySubject: BehaviorSubject<IProperty[]> = new BehaviorSubject<IProperty[]>([]);

  constructor(
    private http: HttpClient
  ) {
    
  }
  /**
   * use only for test
   * @returns 
   */
  private getMockProperty(): BehaviorSubject<IProperty[]> {
    this.propertySubject.next(this.properties);
    return this.propertySubject;
  }

  getProperties(): BehaviorSubject<IProperty[]> {
    return this.getMockProperty(); // should use http client to get data from server
  }
}
