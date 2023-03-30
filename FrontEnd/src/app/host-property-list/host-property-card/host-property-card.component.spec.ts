import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HostPropertyCardComponent } from './host-property-card.component';

describe('HostPropertyCardComponent', () => {
  let component: HostPropertyCardComponent;
  let fixture: ComponentFixture<HostPropertyCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HostPropertyCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HostPropertyCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
