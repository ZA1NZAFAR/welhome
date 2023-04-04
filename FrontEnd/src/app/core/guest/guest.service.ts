import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IGuest } from './guest.model';
import { BehaviorSubject, Subject } from 'rxjs';
import { AuthService } from '../auth/auth.service'

const mockGuests: IGuest[] = [
  {
    id : 1,
    name : "Viet",
    email: "maim@gmail.com",
    phone_number: "01234567890"
  }
];
export { mockGuests};

@Injectable({
  providedIn: 'root'
})
export class GuestService {

  private guests: IGuest[] = mockGuests

  private guestSubject: BehaviorSubject<IGuest[]> = new BehaviorSubject<IGuest[]>([]);

  constructor(
    private http: HttpClient
  ) {
    
  }
  /**
   * use only for test
   * @returns 
   */
  private getMockGuest(): BehaviorSubject<IGuest[]> {
    this.guestSubject.next(this.guests);
    return this.guestSubject;
  }

  getGuests(): BehaviorSubject<IGuest[]> {
    return this.getMockGuest(); // should use http client to get data from server
  }
}
