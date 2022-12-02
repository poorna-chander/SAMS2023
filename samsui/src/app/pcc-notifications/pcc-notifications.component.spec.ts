import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PccNotificationsComponent } from './pcc-notifications.component';

describe('PccNotificationsComponent', () => {
  let component: PccNotificationsComponent;
  let fixture: ComponentFixture<PccNotificationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PccNotificationsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PccNotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
