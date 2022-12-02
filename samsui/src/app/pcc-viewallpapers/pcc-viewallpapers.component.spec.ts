import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PccViewallpapersComponent } from './pcc-viewallpapers.component';

describe('PccViewallpapersComponent', () => {
  let component: PccViewallpapersComponent;
  let fixture: ComponentFixture<PccViewallpapersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PccViewallpapersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PccViewallpapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
