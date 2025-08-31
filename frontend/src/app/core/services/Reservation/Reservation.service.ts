import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Seance } from '../../../Feautures/Admin/admin.component';

export interface SeanceWithReservation {
  idSeance: number;
  nomSeance: string;
  dateTime: string;
  capaciteMax: number;
  idMembre?: number;
  idEntraineur?: number;
  reservationId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = 'http://localhost:8082/api/reservation'; // noter "reservation" singulier

  constructor(private http: HttpClient) { }

  reserverSeance(membreId: number | undefined, seanceId: number | undefined): Observable<any> {
    const url = `${this.apiUrl}/membre/${membreId}/seance/${seanceId}`;
    return this.http.post(url, {});
  }

  getSeances(): Observable<Seance[]> {
    return this.http.get<Seance[]>(`${this.apiUrl}/seances`);
  }

  annulerReservation(reservationId: number | undefined, seanceId: number | undefined): Observable<any> {
    return this.http.delete(`${this.apiUrl}/seance/${seanceId}/reservation/${reservationId}`);
  }

  getSeanceByMembre(membreId: number | undefined): Observable<SeanceWithReservation[]> {
    return this.http.get<SeanceWithReservation[]>(`${this.apiUrl}/membre/${membreId}/seances`);
  }
}
