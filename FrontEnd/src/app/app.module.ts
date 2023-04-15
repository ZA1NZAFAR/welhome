import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FooterComponent } from './commons/footer/footer.component';
import { NavbarComponent } from './commons/navbar/navbar.component';
import { PropertyListModule } from './property-list/property-list.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HostPropertyListModule } from './host-property-list/host-property-list.module'
import { ReservationListModule } from './reservation-list/reservation-list.module'
import { PropertiesModule } from './properties/properties.module';
import { ToastComponent } from './utils/toast/toast.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AuthInterceptor } from './core/auth/auth.interceptor';
import { ErrorInterceptor } from './core/error/error.interceptor';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NavbarModule } from './commons/navbar/navbar.module';
import { AboutUsComponent } from './commons/footer/about-us/about-us.component';
import { PolicyComponent } from './commons/footer/policy/policy.component';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    ToastComponent,
    PolicyComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    PropertyListModule,
    BrowserAnimationsModule,
    HostPropertyListModule,
    ReservationListModule,
    HttpClientModule,
    PropertiesModule,
    FontAwesomeModule,
    NavbarModule,
    AboutUsComponent
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
