import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbOffcanvas } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/core/auth/auth.service';
import { ContextService } from 'src/app/core/context/context.service'
import { FilterService } from 'src/app/core/filter/filter.service';
import { PropertyService } from 'src/app/core/property/property.service';
import { FilterComponent } from './filter/filter.component';
import { FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy {
  selectionControl: FormControl;
  destinations: Set<string>;
  countrySubscription: Subscription;
  propertySubscription: Subscription;
  isCollapsed = true;

  constructor(
    private authService: AuthService,
    private contextService: ContextService,
    private router: Router,
    private modalService: NgbModal,
    private filterService: FilterService,
    private propertyService: PropertyService,
    private offcanvasService: NgbOffcanvas
  ) { }

  open(content: any) {
		this.offcanvasService.open(content, { ariaLabelledBy: 'offcanvas-basic-title', position: 'top' });
	}

  ngOnInit(): void {
    this.authService.startupToken();
    const destination = this.filterService.city.length ? `${this.filterService.city}, ${this.filterService.country}` : '';
    this.selectionControl = new FormControl(destination);
    this.propertyService.getProperties();
    this.countrySubscription = this.propertyService.getDestinations().subscribe((destinations: Set<string>) => {
      this.destinations = destinations;
    });
  }

  ngOnDestroy(): void {
    this.propertySubscription.unsubscribe();
    this.countrySubscription.unsubscribe();
  }

  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }

  login(): void {
    this.authService.login();    
  }

  logout(): void {
    this.authService.logout();
  }

  get changeContextText(): string {
    return this.contextService.isRenter ? 'Switch to Owner View' : 'Switch to Renter View';
  }

  get isOwner(): boolean {
    return !this.contextService.isRenter;
  }

  changeContext(): void {
    this.contextService.changeContext();
    const url = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
    this.router.navigate([url]));
  }

  setActiveClass(path: string): string {
    return (path === '/properties' && this.router.url === '/') || this.router.url.includes(path) ? 'nav-link active' : 'nav-link';
  }

  openFilterModal() {
    const modalRef = this.modalService.open(FilterComponent);
    modalRef.result.then((result) => {
      if (result === 'clear') {
        this.selectionControl.setValue('');
      }
      this.propertyService.getProperties();
    });
  }

  applyFilter() {
    const destination = this.selectionControl.value;
    const [city, country] = destination.includes(', ') ? destination.split(', ') : ['', ''];
    this.filterService.city = city;
    this.filterService.country = country;
    this.propertyService.getProperties();
    this.router.navigate(['/properties']);
  }

}
