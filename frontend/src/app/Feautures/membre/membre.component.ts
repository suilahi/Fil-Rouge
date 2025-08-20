import {Component, inject, OnInit} from '@angular/core';

import {Seance} from '../Admin/admin.component';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {ReservationService, SeanceWithReservation} from '../../core/services/Reservation/Reservation.service';
import {JwtServiceService} from '../../core/services/jwt/jwt-service.service';
import {UserPayload} from '../../core/Models/interfaces/user-payload';

@Component({
  selector: 'app-membre',
  imports: [
    NgForOf,
    DatePipe,
    NgIf
  ],
  templateUrl: './membre.component.html',
  styleUrl: './membre.component.css'
})
export class MembreComponent implements OnInit {
  seances: Seance[] = [];
  mesSeances: SeanceWithReservation[] = [];
  membreId: number | undefined;
  mesSeance: { reservationId: number, seance: Seance }[] = [];




  reservationService: ReservationService = inject( ReservationService )
  jwtserviceService : JwtServiceService= inject(JwtServiceService)

  token: string | null = null;
  userPayload: UserPayload | null = null;

    ngOnInit(): void {
      this.token = localStorage.getItem("token");
      this.userPayload = this.jwtserviceService.getDecodedToken( this.token )

      this.membreId = this.userPayload?.id;
      this.loadSeances();
      this.loadMesSeances();

    }

loadSeances() {
  this.reservationService.getSeances().subscribe({
    next: (data) => {
      const now = new Date();
      this.seances = data.filter(seance => new Date(seance.dateTime) >= now);
    },
    error: err => console.error('Erreur lors du chargement des séances :', err)
  });
}

  reserverSeance(seanceId: number | undefined) {
    this.reservationService.reserverSeance(this.membreId, seanceId).subscribe({
      next: () => {
        console.log(`Réservation réussie pour séance ${seanceId}`);
      },
      error: err => {
        console.error('Erreur lors de la réservation :', err);
      }
    });
  }

  loadMesSeances() {
    if (!this.membreId) return;
    this.reservationService.getSeanceByMembre(this.membreId).subscribe({
      next: (data) => {
        // filtre les séances appartenant au membre connecté
        this.mesSeances = data.filter(seance => seance.idMembre === this.membreId);
        console.log("Mes séances :", this.mesSeances);
      },
      error: (err: any) => console.error("Erreur lors du chargement des séances réservées :", err)
    });
  }


  annulerReservation(reservationId: number | undefined,seanceId:number) {
    if (!reservationId || !seanceId) return;
    this.reservationService.annulerReservation(reservationId, seanceId).subscribe({
      next: () => {
        console.log(`Réservation ${reservationId} annulée`);
        this.loadMesSeances();
      },
      error: err => console.error("Erreur lors de l'annulation :", err)
    });
  }

}
