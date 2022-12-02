import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandingPccComponent } from './landing-pcc.component';

describe('LandingPccComponent', () => {
  let component: LandingPccComponent;
  let fixture: ComponentFixture<LandingPccComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LandingPccComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LandingPccComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
