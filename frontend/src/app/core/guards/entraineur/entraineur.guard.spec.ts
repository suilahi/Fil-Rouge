import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { entraineurGuard } from './entraineur.guard';

describe('entraineurGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => entraineurGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
