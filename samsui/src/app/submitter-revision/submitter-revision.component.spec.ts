import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitterRevisionComponent } from './submitter-revision.component';

describe('SubmitterRevisionComponent', () => {
  let component: SubmitterRevisionComponent;
  let fixture: ComponentFixture<SubmitterRevisionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubmitterRevisionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubmitterRevisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
