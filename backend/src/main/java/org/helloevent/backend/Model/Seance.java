package org.helloevent.backend.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSeance;

    private LocalDate date;
    private Integer capaciteMax;

    @ManyToOne
    private Entraineur entraineur;

    @ManyToOne
    private Membre membre;
}