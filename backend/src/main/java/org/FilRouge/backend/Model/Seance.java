package org.FilRouge.backend.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeance;

        private LocalDateTime date;

    private Integer capaciteMax; // Corrigé: Integer au lieu de Long

    @ManyToOne
    @JoinColumn(name = "entraineur_id")
    private Entraineur entraineur;

    @ManyToOne
    @JoinColumn(name = "membre_id")
    private Membre membre;

    @OneToMany(mappedBy = "seance")
    private List<Reservation> reservations;


    public Seance() {} // Constructeur vide nécessaire

    public Seance(String date, String capaciteMax, String entraineurId, String membreId) {
        this.date = LocalDateTime.parse(date);
        this.capaciteMax = Integer.parseInt(capaciteMax);
        this.entraineur = new Entraineur();
        this.membre = new Membre();
    }

    public Long getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Long idSeance) {
        this.idSeance = idSeance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(Integer capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public Entraineur getEntraineur() {
        return entraineur;
    }

    public void setEntraineur(Entraineur entraineur) {
        this.entraineur = entraineur;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }
}
