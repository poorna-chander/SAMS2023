import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PccCompletedpapersComponent } from './pcc-completedpapers.component';

describe('PccCompletedpapersComponent', () => {
  let component: PccCompletedpapersComponent;
  let fixture: ComponentFixture<PccCompletedpapersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PccCompletedpapersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PccCompletedpapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
