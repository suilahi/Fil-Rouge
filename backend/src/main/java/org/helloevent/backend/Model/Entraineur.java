package org.helloevent.backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;


@Entity
public class Entraineur extends User {

    private String specialite;

    @OneToMany(mappedBy = "entraineur")
    private List<Seance> seances;
}
