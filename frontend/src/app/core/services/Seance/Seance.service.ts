
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
// @ts-ignore
import { Seance } from '../../models/seance.model';

// @ts-ignore
import {Membre} from '../../Models/Membre.model';

// @ts-ignore
import {Entraineur} from '../../Models/Entraineur.model';

@Injectable({
  providedIn: 'root'
})
export class SeanceService {
  private apiUrl = 'http://localhost:8081/api/admin';

  constructor(private http: HttpClient) {}

  addSeance(seance: Seance): Observable<Seance> {
    return this.http.post<Seance>(`${this.apiUrl}/seances`,seance);
  }

  getSeances(): Observable<Seance[]> {
    return this.http.get<Seance[]>(`${this.apiUrl}/seances`);
  }

  updateSeance(id: number, seance: Seance): Observable<Seance> {
    return this.http.put<Seance>(`${this.apiUrl}/seances/${id}`, seance);
  }

  deleteSeance(id: number | undefined): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/seances/${id}`);
  }

  getAllMembre(): Observable<Membre[]> {
    return this.http.get<Membre[]>(`${this.apiUrl}/membres`);
  }

  getAllEntraineurs():Observable<Entraineur[]>{
    return this.http.get<Entraineur>(`${this.apiUrl}/entraineurs`)
  }


}
