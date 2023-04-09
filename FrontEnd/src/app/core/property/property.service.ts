import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IProperty } from './property.model';
import { BehaviorSubject, Observable, Subject, Subscription, catchError, map } from 'rxjs';
import { ToastService } from 'src/app/utils/toast/toast.service'
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';

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

  private propertySubject: Subject<IProperty[]>;
  private propertyLoadingSubject: BehaviorSubject<boolean>;
  private propertyObservable: Observable<IProperty[]>;
  private propertyLoadingObservable: Observable<boolean>;
  private propertySubscription: Subscription;

  private ownerPropertySubject: Subject<IProperty[]>;
  private ownerPropertyLoadingSubject: BehaviorSubject<boolean>;
  private ownerPropertyObservable: Observable<IProperty[]>;
  private ownerPropertyLoadingObservable: Observable<boolean>;
  private ownerPropertySubscription: Subscription;

  constructor(
    private http: HttpClient,
    private toastService: ToastService,
    private authService: AuthService
  ) {
    this.propertySubject = new Subject<IProperty[]>();
    this.propertyLoadingSubject = new BehaviorSubject<boolean>(false);
    this.propertyObservable = this.propertySubject.asObservable();
    this.propertyLoadingObservable = this.propertyLoadingSubject.asObservable();
    this.ownerPropertySubject = new Subject<IProperty[]>();
    this.ownerPropertyLoadingSubject = new BehaviorSubject<boolean>(false);
    this.ownerPropertyObservable = this.ownerPropertySubject.asObservable();
    this.ownerPropertyLoadingObservable = this.ownerPropertyLoadingSubject.asObservable();
  }

  getProperties(): PropertyService {
    if (this.propertySubscription) {
      this.propertySubscription.unsubscribe();
    }
    this.propertyLoadingSubject.next(true);
    this.propertySubscription = this.http.get<IProperty[]>(`${environment.backEndUrl}/properties`).subscribe((properties) => {
      this.propertySubject.next(properties);
      this.propertyLoadingSubject.next(false);
    });
    return this;
  }

  getPropertyObservable(): Observable<IProperty[]> {
    return this.propertyObservable;
  }
  getPropertyLoadingObservable(): Observable<boolean> {
    return this.propertyLoadingObservable;
  }

  getOwnerProperties(ownerEmail: string): PropertyService {
    if (this.ownerPropertySubscription) {
      this.ownerPropertySubscription.unsubscribe();
    }
    this.ownerPropertyLoadingSubject.next(true);
    this.ownerPropertySubscription = this.http.get<IProperty[]>(`${environment.backEndUrl}/properties?owner_email=${ownerEmail}`)
      .subscribe((properties) => {
        this.ownerPropertySubject.next(properties);
        this.ownerPropertyLoadingSubject.next(false);
    });
    return this;
  }

  getOwnerPropertyObservable(): Observable<IProperty[]> {
    return this.ownerPropertyObservable;
  }
  getOwnerPropertyLoadingObservable(): Observable<boolean> {
    return this.ownerPropertyLoadingObservable;
  }
  addProperty(property: IProperty): Observable<IProperty> {
    return this.http.post<IProperty>(`${environment.backEndUrl}/properties`, property).pipe(map((property) => {
      this.getProperties();
      this.getOwnerProperties(property.owner_email);
      this.toastService.showSuccess('Property added successfully');
      return property;
    }));
  }

  updateProperty(property: IProperty): Observable<IProperty> {
    return this.http.put<IProperty>(`${environment.backEndUrl}/properties/${property.id}`, property).pipe(map(() => {
      this.getProperties();
      this.getOwnerProperties(property.owner_email);
      this.toastService.showSuccess('Property updated successfully');
      return property;
    }));
  }

  deleteProperty(propertyId: number): Observable<boolean> {
    return this.http.delete(`${environment.backEndUrl}/properties/${propertyId}`).pipe(map(() => {
      this.getProperties();
      this.getOwnerProperties(this.authService.profile!.email);
      this.toastService.showSuccess('Property deleted successfully');
      return true;
    }));
  }
}
