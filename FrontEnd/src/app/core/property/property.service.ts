import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IProperty } from './property.model';
import { BehaviorSubject, Observable, Subject, Subscription, catchError, map } from 'rxjs';
import { ToastService } from 'src/app/utils/toast/toast.service'
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth/auth.service';
import { IQuery } from '../query.model';
import { FilterService } from '../filter/filter.service';

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
    private authService: AuthService,
    private filterService: FilterService
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

  constructQuery(): string {
    let query = '';
    const { minPrice, maxPrice, category, city, country } = this.filterService;
    
    if (minPrice) {
      query += `min_price=${minPrice}`;
    }
    if (maxPrice < 3000) {
      if (query.length > 0) {
        query += '&';
      }
      query += `max_price=${maxPrice}`;
    }
    if (category && category.length < 3) {
      if (query.length > 0) {
        query += '&';
      }
      query += `category=${category.join(',')}`;
    }
    if (city) {
      if (query.length > 0) {
        query += '&';
      }
      query += `city=${city}`;
    }
    if (country) {
      if (query.length > 0) {
        query += '&';
      }
      query += `country=${country}`;
    }
    return query;
  }

  getDestinations(): Observable<Set<string>> {
    return this.http.get<IProperty[]>(`${environment.backEndUrl}/properties`).pipe(map((properties) => {
      const destination = new Set<string>();
      properties.forEach((property) => {
        destination.add(`${property.city}, ${property.country}`)
      });
      return destination;
    }));
  }

  getProperties(): PropertyService {
    if (this.propertySubscription) {
      this.propertySubscription.unsubscribe();
    }
    this.propertyLoadingSubject.next(true);
    this.propertySubscription = this.http.get<IProperty[]>(`${environment.backEndUrl}/properties/property?${this.constructQuery()}`).subscribe((properties) => {
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
    this.ownerPropertySubscription = this.http.get<IQuery>(`${environment.backEndUrl}/queries/owner_booked_properties?owner_email=${ownerEmail}`)
      .subscribe((queryResults) => {
        console.log(queryResults);
        const properties = queryResults.properties;
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
      this.getOwnerProperties(this.authService.profile!.email);
      this.toastService.showSuccess('Property added successfully');
      return property;
    }));
  }

  updateProperty(property: IProperty): Observable<IProperty> {
    return this.http.put<IProperty>(`${environment.backEndUrl}/properties/${property.id}`, property).pipe(map(() => {
      this.getOwnerProperties(this.authService.profile!.email);
      this.toastService.showSuccess('Property updated successfully');
      return property;
    }));
  }

  deleteProperty(propertyId: number): Observable<boolean> {
    return this.http.delete(`${environment.backEndUrl}/properties/${propertyId}`).pipe(map(() => {
      this.getOwnerProperties(this.authService.profile!.email);
      this.toastService.showSuccess('Property deleted successfully');
      return true;
    }));
  }
}
