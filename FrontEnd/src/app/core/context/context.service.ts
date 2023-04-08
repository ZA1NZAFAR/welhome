import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ContextService {
  context: 'OWNER' | 'RENTER' = 'RENTER';

  constructor() { }

  getContext(): 'OWNER' | 'RENTER' {
    return this.context;
  }

  get isRenter(): boolean {
    return this.context === 'RENTER';
  }

  changeContext() {
    this.context = this.context === 'OWNER' ? 'RENTER' : 'OWNER';
  }
}
