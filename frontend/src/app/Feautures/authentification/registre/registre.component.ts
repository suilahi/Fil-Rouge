import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registre',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './registre.component.html',
  styleUrl: './registre.component.css'
})
export class RegistreComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private router: Router
  ) {
    this.registerForm = this.fb.group({
      fullname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      role: ['',Validators.required]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const formData = {
        fullName: this.registerForm.value.fullname, // ✅ correspond au formGroup
        email: this.registerForm.value.email,
        password: this.registerForm.value.password,
        role: this.registerForm.value.role
      };
      this.http.post('http://localhost:8081/api/auth/register', formData)
        .subscribe({
          next: () => this.router.navigate(['/login']),
          error: (err) => console.error('Erreur d’inscription', err)
        });
    }
  }
}
