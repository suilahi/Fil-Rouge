package org.helloevent.backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;

@Entity
public class Membre extends User {

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @OneToMany(mappedBy = "membre")
    private List<Seance> seances;

    @ManyToOne
    private Abonnement abonnement;
}