package org.FilRouge.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("ENTRAINEUR")
public class Entraineur extends User {

    private String specialite;

    @OneToMany(mappedBy = "entraineur")
    @JsonIgnore
    private List<Seance> seances;

    // Getters & Setters

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public List<Seance> getSeances() {
        return seances;
    }

    public void setSeances(List<Seance> seances) {
        this.seances = seances;
    }
}
