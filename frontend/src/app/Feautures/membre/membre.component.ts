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
  entraineurs: { id: number; fullName: string }[] = [];

  private reservationService = inject(ReservationService);
  private jwtService = inject(JwtServiceService);

  token: string | null = null;
  userPayload: UserPayload | null = null;

  ngOnInit(): void {
    this.token = localStorage.getItem("token");
    this.userPayload = this.jwtService.getDecodedToken(this.token);
    this.membreId = this.userPayload?.id;

    // Charger entraîneurs et séances après
    this.loadEntraineurs();
  }

  /** Charger tous les entraîneurs depuis le backend */
  loadEntraineurs() {
    this.reservationService.getAllEntraineurs().subscribe({
      next: (data) => {
        this.entraineurs = data.map(e => ({ id: e.id, fullName: e.fullName }));
        this.loadSeances();
        this.loadMesSeances();
      },
      error: (err) => console.error('Erreur chargement des entraîneurs :', err)
    });
  }

  /** Récupérer le nom complet d'un entraîneur par ID */
  getEntraineurName(id?: number): string {
    if (!id) return 'Inconnu';
    const entraineur = this.entraineurs.find(e => e.id === id);
    return entraineur ? entraineur.fullName : 'Inconnu';
  }

  /** Charger toutes les séances futures avec mapping entraîneur */
  loadSeances() {
    this.reservationService.getSeances().subscribe({
      next: (data) => {
        const now = new Date();
        this.seances = data
          .filter(seance => new Date(seance.dateTime) >= now)
          .map(seance => ({
            ...seance,
            entraineur: {
              id: seance.entraineur?.id || seance.idEntraineur || 0,
              fullName: this.getEntraineurName(seance.entraineur?.id || seance.idEntraineur)
            }
          }));
      },
      error: (err) => console.error('Erreur chargement des séances :', err)
    });
  }

  /** Réserver une séance */
  reserverSeance(seanceId?: number) {
    if (!this.membreId || !seanceId) return;

    this.reservationService.reserverSeance(this.membreId, seanceId).subscribe({
      next: () => {
        console.log(`Réservation réussie pour séance ${seanceId}`);
        this.loadMesSeances();
        this.loadSeances();
      },
      error: (err) => console.error('Erreur réservation :', err)
    });
  }

  /** Charger les séances réservées par le membre */
  loadMesSeances() {
    if (!this.membreId) return;

    this.reservationService.getSeanceByMembre(this.membreId).subscribe({
      next: (data) => {
        this.mesSeances = data.map(s => ({
          ...s,
          entraineur: {
            id: s.idEntraineur || 0,
            fullName: this.getEntraineurName(s.idEntraineur)
          }
        }));
        console.log("Mes séances :", this.mesSeances);
      },
      error: (err) => console.error("Erreur chargement des séances réservées :", err)
    });
  }

  /** Annuler une réservation */
  annulerReservation(reservationId?: number, seanceId?: number) {
    if (!reservationId || !seanceId) return;

    this.reservationService.annulerReservation(reservationId, seanceId).subscribe({
      next: () => {
        console.log(`Réservation ${reservationId} annulée`);
        this.loadMesSeances();
        this.loadSeances();
      },
      error: (err) => console.error("Erreur annulation :", err)
    });
  }
}
