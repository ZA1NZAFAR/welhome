import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HostPropertyListComponent } from './host-property-list.component';

describe('HostPropertyListComponent', () => {
  let component: HostPropertyListComponent;
  let fixture: ComponentFixture<HostPropertyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HostPropertyListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HostPropertyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
