import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({

        next: (res) => {

          localStorage.setItem('token', res.token);
          localStorage.setItem('id', res.id || '');
          localStorage.setItem('role',res.role);
          localStorage.setItem('fullName',res.fullName)

          if (this.authService.getRole()=="ADMIN"){
            this.router.navigate(['/admin'])
          }else if (this.authService.getRole()=="MEMBRE") {
            this.router.navigate(['/membre'])
          }else (this.authService.getRole()=="ENTRAINEUR")
          {this.router.navigate(['/entraineur'])}
        },
        error: (err) => {
          console.error('Erreur de connexion', err);
          alert('Email ou mot de passe incorrect');
        }
      });
    }
  }
}
