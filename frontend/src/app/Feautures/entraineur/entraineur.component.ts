import { Component, inject, OnInit } from '@angular/core';
import { UserPayload } from '../../core/Models/interfaces/user-payload';
import { JwtServiceService } from '../../core/services/jwt/jwt-service.service';
import {EntraineurService, SeanceResponse} from '../../core/services/entraineur/entraineur.service';
import {Entraineur} from '../Admin/admin.component';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-entraineur',
  standalone: true,
  templateUrl: './entraineur.component.html',
  imports: [
    DatePipe,
    NgIf,
    NgForOf,
    FormsModule
  ],
  styleUrls: ['./entraineur.component.css']
})
export class EntraineurComponent implements OnInit {
  private jwtService = inject(JwtServiceService);
  private entraineurService = inject(EntraineurService);

  token: string | null = null;
  currentUserFullName: string = '';
  userPayload: UserPayload | null = null;

  // Données pour les séances
  mesSeances: SeanceResponse[] = [];
  toutesSeances: SeanceResponse[] = [];

  // Données pour l'entraîneur
  entraineurInfo: Entraineur | null = null;

  ngOnInit(): void {
    this.token = localStorage.getItem('token');

    if (this.token) {
      this.userPayload = this.jwtService.getDecodedToken(this.token);
      this.currentUserFullName = this.userPayload?.fullName || '';
    }

    this.chargerMesSeances();
    this.chargerToutesSeances();
  }

  // ---- SÉANCES ----
  chargerMesSeances(): void {
    this.entraineurService.getMesSeances().subscribe({
      next: (data) => this.mesSeances = data,
      error: (err) => console.error('Erreur lors du chargement de mes séances', err)
    });
  }

  chargerToutesSeances(): void {
    this.entraineurService.getAllSeances().subscribe({
      next: (data) => this.toutesSeances = data,
      error: (err) => console.error('Erreur lors du chargement de toutes les séances', err)
    });
  }

  annulerSeance(id: number): void {
    this.entraineurService.annulerSeance(id).subscribe({
      next: () => {
        console.log(`Séance ${id} annulée`);
        this.chargerMesSeances(); // recharger après suppression
      },
      error: (err) => console.error('Erreur lors de l’annulation de la séance', err)
    });
  }


}
