package org.FilRouge.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("MEMBRE")
public class Membre extends User {

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @OneToMany(mappedBy = "membre" , cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Seance> seances;


    @ManyToOne
    @JoinColumn(name = "abonnement_id")

    private Abonnement abonnement;

    // Getters & Setters

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }
}
