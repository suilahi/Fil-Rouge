package org.FilRouge.backend.Dto;

import jakarta.validation.constraints.*;

public class SeanceRequest {

    private Long idMembre;

    @NotNull(message = "L'ID de l'entraîneur est requis")
    private Long idEntraineur;
    @NotNull(message = "Entrer le nom")
    private String nomSeance;

    @NotBlank(message = "La date est requise au format ISO ex: 2025-07-01T14:00:00")
    private String dateTime;

    @NotNull(message = "La capacité est requise")
    @Min(value = 10, message = "La capacité doit être au moins 10")
    private Integer capaciteMax;

    public SeanceRequest() {}

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

    public String getNomSeance() {
        return nomSeance;
    }
    public void setNomSeance(String nomSeance) {
        this.nomSeance = nomSeance;
    }
}
