import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';


@Component({
  selector: 'app-footer',
  imports: [CommonModule, FormsModule],
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})

export class FooterComponent {
  emailAddress: string = '';

  subscribeToNewsletter(): void {
    if (this.emailAddress && this.isValidEmail(this.emailAddress)) {
      // Implémentez votre logique d'abonnement ici
      console.log('Newsletter subscription:', this.emailAddress);

      // Simuler un appel API
      this.handleNewsletterSubscription(this.emailAddress);

      // Reset form
      this.emailAddress = '';
    } else {
      console.error('Please enter a valid email address');
    }
  }

  private isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  private handleNewsletterSubscription(email: string): void {

    console.log(`Subscription request sent for: ${email}`);
  }
}
