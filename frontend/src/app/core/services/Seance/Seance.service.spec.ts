import { TestBed } from '@angular/core/testing';

import { SeanceService } from './Seance.service';

describe('AuthService', () => {
  let service: SeanceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SeanceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
