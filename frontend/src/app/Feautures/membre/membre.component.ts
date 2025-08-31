import { Component, inject, OnInit } from '@angular/core';
import { DatePipe, NgForOf, NgIf } from '@angular/common';
import { Seance } from '../Admin/admin.component';
import { ReservationService, SeanceWithReservation } from '../../core/services/Reservation/Reservation.service';
import { JwtServiceService } from '../../core/services/jwt/jwt-service.service';
import { UserPayload } from '../../core/Models/interfaces/user-payload';

@Component({
  selector: 'app-membre',
  imports: [NgForOf, DatePipe, NgIf],
  templateUrl: './membre.component.html',
  styleUrls: ['./membre.component.css']
})
export class MembreComponent implements OnInit {
  seances: Seance[] = [];
  mesSeances: SeanceWithReservation[] = [];
  membreId?: number;

  private reservationService = inject(ReservationService);
  private jwtService = inject(JwtServiceService);

  token: string | null = null;
  userPayload: UserPayload | null = null;

  ngOnInit(): void {
    this.token = localStorage.getItem("token");
    this.userPayload = this.jwtService.getDecodedToken(this.token);
    this.membreId = this.userPayload?.id;

    this.loadSeances();
    this.loadMesSeances();
  }

  //  toutes les séances futures
  loadSeances() {
    this.reservationService.getSeances().subscribe({
      next: (data) => {
        const now = new Date();
        this.seances = data.filter(seance => new Date(seance.dateTime) >= now);
      },
      error: err => console.error('Erreur lors du chargement des séances :', err)
    });
  }

  // Réserver une séance
  reserverSeance(seanceId?: number) {
    if (!this.membreId || !seanceId) return;

    this.reservationService.reserverSeance(this.membreId, seanceId).subscribe({
      next: () => {
        console.log(`Réservation réussie pour séance ${seanceId}`);
        this.loadMesSeances();
        this.loadSeances();
      },
      error: err => console.error('Erreur lors de la réservation :', err)
    });
  }

  //  séances réservées
  loadMesSeances() {
    if (!this.membreId) return;

    this.reservationService.getSeanceByMembre(this.membreId).subscribe({
      next: (data) => {
        this.mesSeances = data;
        console.log("Mes séances :", this.mesSeances);
      },
      error: (err) => console.error("Erreur lors du chargement des séances réservées :", err)
    });
  }


  annulerReservation(reservationId?: number, seanceId?: number) {
    if (!reservationId || !seanceId) return;

    this.reservationService.annulerReservation(reservationId, seanceId).subscribe({
      next: () => {
        console.log(`Réservation ${reservationId} annulée`);
        this.loadMesSeances();
        this.loadSeances();
      },
      error: err => console.error("Erreur lors de l'annulation :", err)
    });
  }
}
