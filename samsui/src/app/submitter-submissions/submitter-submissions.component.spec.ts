import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitterSubmissionsComponent } from './submitter-submissions.component';

describe('SubmitterSubmissionsComponent', () => {
  let component: SubmitterSubmissionsComponent;
  let fixture: ComponentFixture<SubmitterSubmissionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubmitterSubmissionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubmitterSubmissionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
