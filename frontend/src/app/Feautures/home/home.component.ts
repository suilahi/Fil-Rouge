import { Component, OnInit } from '@angular/core';
import {DatePipe, NgForOf, NgIf, SlicePipe} from '@angular/common';
import { ReservationService } from '../../core/services/Reservation/Reservation.service';
import { Seance } from '../Admin/admin.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
  imports: [NgForOf, NgIf, DatePipe, SlicePipe]
})
export class HomeComponent implements OnInit {
  seances: Seance[] = [];

  constructor(private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.loadSeances();
  }

  loadSeances(): void {
    this.reservationService.getSeances().subscribe({
      next: (data: Seance[]) => {
        this.seances = data;
      },
      error: (err) => console.error('Erreur lors du chargement des séances :', err)
    });
  }
}
