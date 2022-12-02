import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RatepaperComponent } from './ratepaper.component';

describe('RatepaperComponent', () => {
  let component: RatepaperComponent;
  let fixture: ComponentFixture<RatepaperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RatepaperComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RatepaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
