import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Entraineur {
  id: number;
  fullName: string;
  email?: string;
  specialite?: string;
}

export interface SeanceResponse {
  idSeance: number;
  nomSeance: string;
  dateTime: string;
  capaciteMax: number;
  membre?: any;
  entraineur?: any;
}

@Injectable({
  providedIn: 'root'
})
export class EntraineurService {

  private apiUrl = 'http://localhost:8082/entraineurs'; // adapte le port

  constructor(private http: HttpClient) { }

  // ---- SÉANCES ----
  annulerSeance(seanceId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/seances/${seanceId}`);
  }

  getAllSeances(): Observable<SeanceResponse[]> {
    return this.http.get<SeanceResponse[]>(`${this.apiUrl}/seances`);
  }

  getMesSeances(): Observable<SeanceResponse[]> {
    return this.http.get<SeanceResponse[]>(`${this.apiUrl}/mes-seances`);
  }

  // ---- ENTRAÎNEUR ----
  modifierInfo(id: number, entraineur: Entraineur): Observable<Entraineur> {
    return this.http.put<Entraineur>(`${this.apiUrl}/${id}`, entraineur);
  }
}
