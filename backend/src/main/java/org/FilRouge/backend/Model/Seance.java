package org.FilRouge.backend.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeance;

    private String nomSeance;

    private LocalDateTime date;

    private Integer capaciteMax;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "entraineur_id")
    private Entraineur entraineur;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "membre_id")
    private Membre membre;

    @OneToMany(mappedBy = "seance")
    @JsonIgnore
    private List<Reservation> reservations;


    public Seance() {} // Constructeur vide nécessaire

    public Seance(String nomSeance ,String date, String capaciteMax, String entraineurId, String membreId) {
        this.nomSeance = nomSeance;
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
    public String getNomSeance() {
        return nomSeance;
    }

    public void setNomSeance(String nomSeance) {
        this.nomSeance = nomSeance;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
