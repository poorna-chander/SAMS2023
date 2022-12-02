import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminSetDeadlinesComponent } from './admin-set-deadlines.component';

describe('AdminSetDeadlinesComponent', () => {
  let component: AdminSetDeadlinesComponent;
  let fixture: ComponentFixture<AdminSetDeadlinesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminSetDeadlinesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminSetDeadlinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
