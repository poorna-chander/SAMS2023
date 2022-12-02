import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PcmRateassignedComponent } from './pcm-rateassigned.component';

describe('PcmRateassignedComponent', () => {
  let component: PcmRateassignedComponent;
  let fixture: ComponentFixture<PcmRateassignedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PcmRateassignedComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PcmRateassignedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
