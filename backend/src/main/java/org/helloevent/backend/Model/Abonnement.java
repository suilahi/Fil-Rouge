package org.helloevent.backend.Model;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAbonnement;

    private String titre;
    private Double prix;
    private String description;

    @OneToMany(mappedBy = "abonnement")
    private List<Membre> membres;
}
