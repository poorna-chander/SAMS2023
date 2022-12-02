import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PcmReviewPaperComponent } from './pcm-review-paper.component';

describe('PcmReviewPaperComponent', () => {
  let component: PcmReviewPaperComponent;
  let fixture: ComponentFixture<PcmReviewPaperComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PcmReviewPaperComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PcmReviewPaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
