// src/app/core/services/auth/auth.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
// @ts-ignore
import { Seance } from '../../models/seance.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/api/admin/seances';

  constructor(private http: HttpClient) {}

  addSeance(seance: Seance): Observable<Seance> {
    return this.http.post<Seance>(this.apiUrl, seance);
  }

  getSeances(): Observable<Seance[]> {
    return this.http.get<Seance[]>(this.apiUrl);
  }

  updateSeance(id: number, seance: Seance): Observable<Seance> {
    return this.http.put<Seance>(`${this.apiUrl}/${id}`, seance);
  }

  deleteSeance(id: number | undefined): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }


}
