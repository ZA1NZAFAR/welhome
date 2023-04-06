import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IProperty } from './property.model';
import { BehaviorSubject, Observable, catchError, map, of, throwError } from 'rxjs';
import { ToastService } from 'src/app/utils/toast/toast.service'

const mockPropertyMap: Map<number,IProperty> = new Map([
  [1, {
    id : 1,
    title: 'House in Paris',
    description: 'A beautiful house in Paris',
    property_category: 'House',
    address: '1, rue de la paix',
    city: 'Paris',
    state: 'Ile-de-France',
    country: 'France',
    price: 100,
    surface_area: 100,
    floors: 1,
    capacity: 4,
    owner_email: 'zain.zafar@gmail.com',
    image_url: 'https://cdn.pixabay.com/photo/2016/11/18/17/46/house-1836070_960_720.jpg'
  }],
  [2, {
    id : 2,
    title: 'Room in Toulouse',
    description: 'A beautiful room in Toulouse',
    property_category: 'House',
    address: '1, rue de la Republique',
    city: 'Toulouse',
    state: 'Occitanie',
    country: 'France',
    price: 100,
    surface_area: 100,
    floors: 5,
    capacity: 2,
    owner_email: 'zain.zafar@gmail.com',
    image_url: 'https://images.unsplash.com/photo-1615874959474-d609969a20ed?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YmVkJTIwcm9vbXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60'
  }],
  [3, {
    id : 3,
    title: 'Apartment in Lyon',
    description: 'A beautiful apartment in Lyon',
    property_category: 'Apartment',
    address: '1, rue General de Gaulle',
    city: 'Lyon',
    state: 'Auvergne-Rhône-Alpes',
    country: 'France',
    price: 200,
    surface_area: 50,
    floors: 5,
    capacity: 4,
    owner_email: 'abc@gmail.com',
    image_url: 'https://media.istockphoto.com/id/1165384568/fr/photo/complexe-moderne-europ%C3%A9en-de-b%C3%A2timents-r%C3%A9sidentiels.jpg?s=612x612&w=0&k=20&c=nvoIbiIffCt-nuj47Cc3I261Ke98iMouq_HefNM7Lz0='
  }]
]);

export const mockProperties: IProperty[] = Array.from(mockPropertyMap.values());

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private properties: IProperty[] = mockProperties;

  private _propertyCount: number = mockPropertyMap.size;

  private propertySubject: BehaviorSubject<IProperty[]> = new BehaviorSubject<IProperty[]>(this.properties);
  private propertyLoadingSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(
    private http: HttpClient,
    private toastService: ToastService
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

  getProperties(): Observable<IProperty[]> {
    this.propertyLoadingSubject.next(true);
    return this.getMockProperty().pipe(map(() => {
      this.propertyLoadingSubject.next(false);
      return this.properties;
    })); // should use http client to get data from server
  }

  getPropertyLoading(): Observable<boolean> {
    return this.propertyLoadingSubject.asObservable();
  }

  addProperty(property: IProperty): Observable<IProperty> {
    return this._addProperty(property).pipe(map(() => {
      this.propertySubject.next(this.properties);
      this.toastService.showSuccess('Property added successfully');
      return property;
    }), catchError((error: Error) => {
      this.toastService.showError('Error while updating property : ' + error.message);
      throw error;
    }));
  }

  private _addProperty(property: IProperty): Observable<IProperty> {
    property.id = ++this._propertyCount;
    mockPropertyMap.set(property.id, property);
    this.properties = Array.from(mockPropertyMap.values());
    return of(property);
  }

  updateProperty(property: IProperty): Observable<IProperty> {
    return this._updateProperty(property).pipe(map(() => {
      this.toastService.showSuccess('Property updated successfully');
      this.propertySubject.next(this.properties);
      return property;
    }), catchError((error: Error) => {
      this.toastService.showError('Error while updating property : ' + error.message);
      throw error;
    }));
  }

  private _updateProperty(property: IProperty): Observable<IProperty> {
    mockPropertyMap.set(property.id, property);
    this.properties = Array.from(mockPropertyMap.values());
    return of(property);
  }

  deleteProperty(propertyId: number): Observable<boolean> {
    return this._deleteProperty(propertyId).pipe(map((result) => {
      this.toastService.showSuccess('Property deleted successfully');
      this.propertySubject.next(this.properties);
      return true;
    }), catchError((error: Error) => {
      this.toastService.showError('Error while deleting property : ' + error.message);
      throw error;
    }));
  }

  private _deleteProperty(propertyId: number): Observable<boolean> {
    if (!mockPropertyMap.has(propertyId)) {
      return of(false);
    }
    mockPropertyMap.delete(propertyId);
    this.properties = Array.from(mockPropertyMap.values());
    return of(true);
  }
}
