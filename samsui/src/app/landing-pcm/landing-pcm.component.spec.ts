import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandingPcmComponent } from './landing-pcm.component';

describe('LandingPcmComponent', () => {
  let component: LandingPcmComponent;
  let fixture: ComponentFixture<LandingPcmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LandingPcmComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LandingPcmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
