import { TestBed } from '@angular/core/testing';

import { SamsSubmissionService } from './sams-submission.service';

describe('SamsSubmissionService', () => {
  let service: SamsSubmissionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SamsSubmissionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
