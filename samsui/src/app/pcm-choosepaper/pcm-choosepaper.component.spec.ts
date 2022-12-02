import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PcmChoosepaperComponent } from './pcm-choosepaper.component';

describe('PcmChoosepaperComponent', () => {
  let component: PcmChoosepaperComponent;
  let fixture: ComponentFixture<PcmChoosepaperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PcmChoosepaperComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PcmChoosepaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
