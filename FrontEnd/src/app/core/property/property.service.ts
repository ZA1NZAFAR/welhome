import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IProperty } from './property.model';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private properties: IProperty[] = [
    {
      location: 'Paris',
      prix: 100,
      area: 100
    },
    {
      location: 'Lyon',
      prix: 200,
      area: 50
    }
  ];

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
    return this.getMockProperty();
  }
}
