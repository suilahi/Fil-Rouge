package org.FilRouge.backend.Dto; // ou un autre package que tu veux

public class SeanceRequest {

    private Long idMembre;
    private Long idEntraineur;
    private String dateTime; // en ISO string, ex: "2025-07-01T14:00:00"

    // Constructeur sans arguments (obligatoire pour Jackson)
    public SeanceRequest() {}

    // Getters & Setters
    public Long getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(Long idMembre) {
        this.idMembre = idMembre;
    }

    public Long getIdEntraineur() {
        return idEntraineur;
    }

    public void setidEntraineur(Long idEntraineur) {
        this.idEntraineur = idEntraineur;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


}
