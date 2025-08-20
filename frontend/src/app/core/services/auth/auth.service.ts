import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { Router } from '@angular/router';
import {catchError, Observable, throwError} from 'rxjs';

export interface RegisterDto {
  fullName: string;
  email: string;
  password: string;
  role: string;
}

export interface LoginRequestDTO {
  email: string;
  password: string;
}

export interface AuthResponseDTO {
  token: string;
  fullName: string;
  email: string;
  role: string;
  id?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8081/api/auth';

  constructor(private http: HttpClient,
              private router: Router) { }

  // 🔹 Inscription
  register(registerData: RegisterDto): Observable<AuthResponseDTO> {
    return this.http.post<AuthResponseDTO>(`${this.apiUrl}/register`, registerData);
  }

  // 🔹 Connexion
  login(loginData: LoginRequestDTO): Observable<AuthResponseDTO> {
    console.log(loginData)
    return this.http.post<AuthResponseDTO>(`${this.apiUrl}/login`, loginData).pipe(
      catchError((err: HttpErrorResponse) => {
        return throwError(() => err);
      })
    );
  }

  // 🔹 Déconnexion
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('id');
    this.router.navigate(['/login']);
  }

  // 🔹 Vérifier si l'utilisateur est connecté
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  // 🔹 Récupérer le token
  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
