import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { SeanceService } from '../../core/services/Seance/Seance.service';
import { DatePipe, NgForOf, NgIf } from '@angular/common';

export interface Membre {
  id: number;
  fullName: string;
}

export interface Entraineur {
  id: number;
  fullName: string;
  email?: string;
  password?: string;
}

export interface Seance {
  idSeance?: number;
  nomSeance: string;
  dateTime: string;
  capaciteMax: number;
  membre?: Membre;
  entraineur: Entraineur;
  idMembre?: number;
  idEntraineur?: number;
}

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  imports: [NgIf, NgForOf, ReactiveFormsModule, DatePipe],
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  seances: Seance[] = [];
  seanceForm!: FormGroup;
  entraineurForm!: FormGroup;
  membres: Membre[] = [];
  entraineurs: Entraineur[] = [];
  editingSeanceId: number | null = null;

  showSeanceModal = false;
  showEntraineurModal = false;

  constructor(private seanceservice: SeanceService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
    this.loadMembres();
    this.loadEntraineurs();
    this.loadSeances();
  }

  initForm() {
    this.seanceForm = this.fb.group({
      nomSeance: ['', Validators.required],
      dateTime: ['', Validators.required],
      capaciteMax: ['', [Validators.required, Validators.min(1)]],
      idEntraineur: ['', Validators.required],
    });

    this.entraineurForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  /** Modal management */
  openSeanceModal() { this.showSeanceModal = true; }
  closeSeanceModal() {
    this.showSeanceModal = false;
    this.seanceForm.reset();
    this.editingSeanceId = null;
  }

  openEntraineurModal() { this.showEntraineurModal = true; }
  closeEntraineurModal() { this.showEntraineurModal = false; this.entraineurForm.reset(); }

  /** Chargement des données */
  loadMembres() {
    this.seanceservice.getAllMembre().subscribe({
      next: (data) => this.membres = data,
      error: (err) => console.error('Erreur chargement membres :', err)
    });
  }

  loadEntraineurs() {
    this.seanceservice.getAllEntraineurs().subscribe({
      next: (data) => this.entraineurs = data,
      error: (err) => console.error('Erreur chargement entraîneurs :', err)
    });
  }

  loadSeances() {
    this.seanceservice.getSeances().subscribe({
      next: (data) => {
        // On attend que membres et entraineurs soient chargés
        this.seances = data.map(seance => {
          const membre = this.membres.find(m => m.id === (seance.membre?.id || seance.idMembre));
          const entraineur = this.entraineurs.find(e => e.id === (seance.entraineur?.id || seance.idEntraineur));
          return {
            ...seance,
            membre: membre ?? { id: seance.idMembre ?? 0, fullName: 'Inconnu' },
            entraineur: entraineur ?? { id: seance.idEntraineur ?? 0, fullName: 'Inconnu' }
          };
        });
      },
      error: (err) => console.error('Erreur chargement séances :', err)
    });
  }

  /** CRUD Séances */
  onSubmit() {
    if (this.seanceForm.invalid) return;

    // Récupération de l'idMembre existant si en modification
    const currentSeance = this.editingSeanceId
      ? this.seances.find(s => s.idSeance === this.editingSeanceId)
      : null;

    const formValue = {
      ...this.seanceForm.value,
      idEntraineur: Number(this.seanceForm.value.idEntraineur),
      idMembre: currentSeance?.membre?.id ?? null
    };

    if (this.editingSeanceId) {
      this.seanceservice.updateSeance(this.editingSeanceId, formValue).subscribe({
        next: (updated) => {
          const entraineur = this.entraineurs.find(e => e.id === updated.idEntraineur);
          const membre = this.membres.find(m => m.id === updated.idMembre);

          this.seances = this.seances.map(s =>
            s.idSeance === updated.idSeance
              ? {
                ...updated,
                entraineur: entraineur ?? { id: 0, fullName: 'Inconnu' },
                membre: membre ?? { id: 0, fullName: 'Inconnu' }
              }
              : s
          );
          this.closeSeanceModal();
        },
        error: (err) => console.error('Erreur modification :', err)
      });
    } else {
      this.seanceservice.addSeance(formValue).subscribe({
        next: (newSeance) => {
          const entraineur = this.entraineurs.find(e => e.id === newSeance.idEntraineur);
          const membre = this.membres.find(m => m.id === newSeance.idMembre);

          this.seances.push({
            ...newSeance,
            entraineur: entraineur ?? { id: 0, fullName: 'Inconnu' },
            membre: membre ?? { id: 0, fullName: 'Inconnu' }
          });
          this.closeSeanceModal();
        },
        error: (err) => console.error('Erreur ajout :', err)
      });
    }
  }

  onEdit(seance: Seance) {
    if (!seance.idSeance) return;
    this.editingSeanceId = seance.idSeance;
    this.seanceForm.patchValue({
      nomSeance: seance.nomSeance,
      dateTime: seance.dateTime,
      capaciteMax: seance.capaciteMax,
      idEntraineur: seance.entraineur.id
    });
    this.openSeanceModal();
  }

  onDelete(id: number | undefined) {
    if (!id) return;
    this.seanceservice.deleteSeance(id).subscribe({
      next: () => this.seances = this.seances.filter(s => s.idSeance !== id),
      error: (err) => console.error('Erreur suppression :', err)
    });
  }

  /** CRUD Entraîneurs */
  onAddEntraineur() {
    if (this.entraineurForm.invalid) return;
    this.seanceservice.addEntraineur(this.entraineurForm.value).subscribe({
      next: (newEntraineur) => {
        this.entraineurs.push(newEntraineur);
        this.closeEntraineurModal();
        console.log('✅ Entraîneur ajouté :', newEntraineur);
      },
      error: (err) => {
        if (err.error?.message?.includes("Email déjà utilisé")) {
          alert("⚠️ Cet email est déjà pris !");
        } else {
          console.error('Erreur ajout entraîneur :', err);
        }
      }
    });
  }
}
