import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';

export const adminGuardsGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const role = authService.getRole();

  if (role === 'ADMIN') {
    return true;
  } else {
    alert('Access denied: Admins only');
    router.navigate(['/home']);
    return false;
  }
  return true;
};
