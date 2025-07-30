package org.FilRouge.backend.Model;

import jakarta.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Membre membre;

    @ManyToOne
    private Seance seance;

    // Optionnel : ajouter date de réservation, statut, etc.

    public Reservation() {}

    public Reservation(Membre membre, Seance seance) {
        this.membre = membre;
        this.seance = seance;
    }

    public Long getId() {
        return id;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }
}
