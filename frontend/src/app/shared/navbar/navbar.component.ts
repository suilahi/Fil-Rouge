import { Component } from '@angular/core';
import {NgIf} from '@angular/common';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {AuthService} from '../../core/services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  imports: [
    NgIf,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './navbar.component.html',
  standalone: true,
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  isMobileMenuOpen = false;

  constructor(public authService: AuthService) {}

  toggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  closeMobileMenu(): void {
    this.isMobileMenuOpen = false;
  }

  toggleSearch(): void {
    console.log('Search toggled');
  }

  logout(): void {
    this.authService.logout();
  }

}
