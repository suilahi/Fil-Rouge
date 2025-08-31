import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';

export const entraineurGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const role = authService.getRole();

  if (role === 'ENTRAINEUR') {
    return true;
  } else {
    alert('Access denied: entraineurs only');
    router.navigate(['/home']);
    return false;
  }
  return true;
};
