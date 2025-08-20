package org.FilRouge.backend.Dto;

import org.FilRouge.backend.Model.Seance;

public class SeanceResponse {
    private Long idSeance;
    private String dateTime;
    private Integer capaciteMax;
    private Long idMembre;
    private Long idEntraineur;
    private String nomSeance;
    private Long reservationId; // <-- nouveau champ

    // Constructeur existant
    public SeanceResponse(Seance seance) {
        this.idSeance = seance.getIdSeance();
        this.nomSeance = seance.getNomSeance();
        this.dateTime = seance.getDate().toString();
        this.capaciteMax = seance.getCapaciteMax();
        this.idMembre = seance.getMembre() != null ? seance.getMembre().getId() : null;
        this.idEntraineur = seance.getEntraineur() != null ? seance.getEntraineur().getId() : null;
    }

    // Nouveau constructeur avec reservationId
    public SeanceResponse(Seance seance, Long reservationId) {
        this(seance); // appelle le constructeur précédent
        this.reservationId = reservationId;
    }

    // Getter et setter pour reservationId
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Long idSeance) {
        this.idSeance = idSeance;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(Integer capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public Long getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(Long idMembre) {
        this.idMembre = idMembre;
    }

    public Long getIdEntraineur() {
        return idEntraineur;
    }

    public void setIdEntraineur(Long idEntraineur) {
        this.idEntraineur = idEntraineur;
    }

    public String getNomSeance() {
        return nomSeance;
    }

    public void setNomSeance(String nomSeance) {
        this.nomSeance = nomSeance;
    }

}