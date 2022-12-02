import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PccRatepaperComponent } from './pcc-ratepaper.component';

describe('PccRatepaperComponent', () => {
  let component: PccRatepaperComponent;
  let fixture: ComponentFixture<PccRatepaperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PccRatepaperComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PccRatepaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
