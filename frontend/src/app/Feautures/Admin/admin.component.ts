import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SeanceService } from '../../core/services/Seance/Seance.service';
import { HttpClientModule } from '@angular/common/http';
import {DatePipe, NgForOf, NgIf} from '@angular/common';

export interface Seance {
  idSeance?: number;
  nomSeance:string;
  dateTime: string;
  capaciteMax: number;
  idEntraineur: number;
  idMembre: number;
}
export interface Membre {
  id: number;
  NomMembre:string;
}

export interface Entraineur {
  id: number;
  fullName:string;
}

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    HttpClientModule,
    ReactiveFormsModule,
    DatePipe
  ]
})
export class AdminComponent implements OnInit {

  seances: Seance[] = [];
  membres: Membre[] = [];
  entraineurs:Entraineur[]=[];
  seanceForm!: FormGroup;
  editingSeanceId: number | null = null; // 👈 Savoir si on modifie ou ajoute


  constructor(
    private seanceservice: SeanceService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.seanceForm = this.fb.group({
      nomSeance:['',Validators.required],
      dateTime: ['', Validators.required],
      capaciteMax: ['', [Validators.required, Validators.min(1)]],
      idEntraineur: ['', Validators.required],
      idMembre: ['', Validators.required]
    });

    this.loadSeances();
    this.loadMembres();
    this.loadEntraineurs();
  }

  loadSeances() {
    this.seanceservice.getSeances().subscribe({
      next: (data) => {
        const now = new Date();
        this.seances = data.filter(seance => new Date(seance.dateTime) >= now);
      },
      error: err => console.error('Erreur lors du chargement des séances :', err)
    });
  }

  loadMembres(){
    this.seanceservice.getAllMembre().subscribe({
      next:(data)=>this.membres=data,
      error:(err)=>console.error('Erreur lors du chargement des membres :', err )
    });
  }

  loadEntraineurs(){
    this.seanceservice.getAllEntraineurs().subscribe({
      next:(data)=>this.entraineurs=data,
      error:(err)=>console.error('Erreur lors du chargement des entraineurs :', err )
    })
  }



  onEdit(seance: Seance) {
    this.editingSeanceId = seance.idSeance || null;

    // Pré-remplir le formulaire
    this.seanceForm.patchValue({
      nomSeance: seance.nomSeance,
      dateTime: seance.dateTime,
      capaciteMax: seance.capaciteMax,
      idEntraineur: seance.idEntraineur,
      idMembre: seance.idMembre
    });

    // Afficher le modal
    document.getElementById('modal')?.classList.remove('hidden');
  }

  onSubmit() {
    if (this.seanceForm.invalid) return;

    const formValue = this.seanceForm.value;

    if (this.editingSeanceId) {
      // Mode édition
      this.seanceservice.updateSeance(this.editingSeanceId, formValue).subscribe({
        next: (updated) => {
          const index = this.seances.findIndex(s => s.idSeance === updated.idSeance);
          if (index !== -1) {
            this.seances[index] = updated;
          }
          this.closeModal();
        },
        error: (err) => console.error("Erreur modification :", err)
      });
    } else {
      // Mode ajout
      this.seanceservice.addSeance(formValue).subscribe({
        next: (seance) => {
          this.seances.push(seance);
          this.closeModal();
        },
        error: (err) => console.error("Erreur ajout :", err)
      });
    }
  }

  closeModal() {
    this.seanceForm.reset();
    this.editingSeanceId = null;
    document.getElementById('modal')?.classList.add('hidden');
  }




  onDelete(id: number | undefined) {
    this.seanceservice.deleteSeance(id).subscribe({
      next: () => {
        this.seances = this.seances.filter(s => s.idSeance !== id);
      },
      error: err => console.error("Erreur lors de la suppression :", err)
    });
  }



}
