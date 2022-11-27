import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitterFormComponent } from './submitter-form.component';

describe('SubmitterFormComponent', () => {
  let component: SubmitterFormComponent;
  let fixture: ComponentFixture<SubmitterFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubmitterFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubmitterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
