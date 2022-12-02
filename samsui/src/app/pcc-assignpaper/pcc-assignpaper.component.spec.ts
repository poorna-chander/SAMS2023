import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PccAssignpaperComponent } from './pcc-assignpaper.component';

describe('PccAssignpaperComponent', () => {
  let component: PccAssignpaperComponent;
  let fixture: ComponentFixture<PccAssignpaperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PccAssignpaperComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PccAssignpaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
