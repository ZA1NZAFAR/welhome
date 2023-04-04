import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IProperty } from './property.model';
import { BehaviorSubject, Subject } from 'rxjs';
import { AuthService } from '../auth/auth.service'

const mockProperties: IProperty[] = [
  {
    id : 1,
    title: 'House in Paris',
    description: 'A beautiful house in Paris',
    property_catergory: 'House',
    address: '1, rue de la paix',
    city: 'Paris',
    region: 'Ile-de-France',
    country: 'France',
    price: 100,
    surface_area: 100,
    floors: 1,
    capacity: 4,
    owner_email: 'zain.zafar@gmail.com',
    image_url: 'https://cdn.pixabay.com/photo/2016/11/18/17/46/house-1836070_960_720.jpg'
  },
  {
    id : 2,
    title: 'Room in Toulouse',
    description: 'A beautiful room in Toulouse',
    property_catergory: 'House',
    address: '1, rue de la Republique',
    city: 'Toulouse',
    region: 'Occitanie',
    country: 'France',
    price: 100,
    surface_area: 100,
    floors: 5,
    capacity: 2,
    owner_email: 'zain.zafar@gmail.com',
    image_url: 'https://images.unsplash.com/photo-1615874959474-d609969a20ed?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YmVkJTIwcm9vbXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60'
  },
  {
    id : 3,
    title: 'Apartment in Lyon',
    description: 'A beautiful apartment in Lyon',
    property_catergory: 'Apartment',
    address: '1, rue General de Gaulle',
    city: 'Lyon',
    region: 'Auvergne-Rhône-Alpes',
    country: 'France',
    price: 200,
    surface_area: 50,
    floors: 5,
    capacity: 4,
    owner_email: 'abc@gmail.com',
    image_url: 'https://media.istockphoto.com/id/1165384568/fr/photo/complexe-moderne-europ%C3%A9en-de-b%C3%A2timents-r%C3%A9sidentiels.jpg?s=612x612&w=0&k=20&c=nvoIbiIffCt-nuj47Cc3I261Ke98iMouq_HefNM7Lz0='
  }
];
export { mockProperties };

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
