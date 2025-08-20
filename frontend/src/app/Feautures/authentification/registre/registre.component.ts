import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import {Router, RouterLink} from '@angular/router';
import { CommonModule } from '@angular/common';
import {AuthService} from '../../../core/services/auth/auth.service';

@Component({
  selector: 'app-registre',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule, RouterLink],
  templateUrl: './registre.component.html',
  styleUrl: './registre.component.css'
})
export class RegistreComponent {
  registerForm: FormGroup;

  constructor(private authservice: AuthService,
              private fb: FormBuilder,
              private http: HttpClient,
              private router: Router
  ) {
    this.registerForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['',Validators.required]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.authservice.register(this.registerForm.value).subscribe({
        next: () => this.router.navigate(['/login']),
        error: (err) => console.error('Erreur d’inscription', err)
      });
    }
  }
}
