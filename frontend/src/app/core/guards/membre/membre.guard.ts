import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';

export const membreGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const role = authService.getRole();

  if (role === 'MEMBRE') {
    return true;
  } else {
    alert('Access denied: membres only');
    router.navigate(['/home']);
    return false;
  }
  return true;
};
