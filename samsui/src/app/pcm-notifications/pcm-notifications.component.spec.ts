import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PcmNotificationsComponent } from './pcm-notifications.component';

describe('PcmNotificationsComponent', () => {
  let component: PcmNotificationsComponent;
  let fixture: ComponentFixture<PcmNotificationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PcmNotificationsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PcmNotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
