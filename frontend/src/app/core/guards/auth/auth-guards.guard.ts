import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';

export const authGuardsGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {

    // getrole
    const role = authService.getRole();

    if (role === 'ADMIN') {
      router.navigate(['/admin']);
    } else if (role === 'ENTRAINEUR') {
      router.navigate(['/entraineur']);
    } else if (role === 'MEMBRE') {
      router.navigate(['/membre']);
    } else {
      router.navigate(['/']);
    }
// no access to login & register page
    return false;
  }
  return true;
};
