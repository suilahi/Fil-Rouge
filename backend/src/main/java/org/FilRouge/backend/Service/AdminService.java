package org.FilRouge.backend.Service;

import org.FilRouge.backend.Model.*;
import org.FilRouge.backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private MaterielRepository materielRepository;

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private EntraineurRepository entraineurRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    // Méthodes liées aux matériels

    public Materiel Addmateriel(Materiel materiel) {
        return materielRepository.save(materiel);
    }

    public List<Materiel> getAllMateriel() {
        return materielRepository.findAll();
    }

    public Materiel getMateriel(Long id) {
        return materielRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matériel non trouvé avec l'ID : " + id));
    }

    public List<Materiel> getMaterielsStockBas(int seuil) {
        return materielRepository.findByQuantiteLessThan(seuil);
    }

    // Méthodes liées aux membres

    public Membre addMembre(Membre membre) {
        membre.setPassword(membre.getPassword()); // à encoder si besoin
        membre.setRole("MEMBRE");
        return membreRepository.save(membre);
    }

    public List<Membre> getAllMembre() {
        return membreRepository.findAll();
    }

    public Membre getMembre(Long id) {
        return membreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'ID : " + id));
    }

    public void deleteMembre(Long id) {
        if (!membreRepository.existsById(id)) {
            throw new RuntimeException("Membre non trouvé avec l'ID : " + id);
        }
        membreRepository.deleteById(id);
    }

    // Méthodes liées aux entraîneurs

    public Entraineur addEntraineur(Entraineur entraineur) {
        entraineur.setPassword(entraineur.getPassword()); // à encoder si besoin
        entraineur.setRole("ENTRAINEUR");
        return entraineurRepository.save(entraineur);
    }

    public List<Entraineur> getAllEntraineur() {
        return entraineurRepository.findAll();
    }

    public Entraineur getEntraineur(Long id) {
        return entraineurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entraîneur non trouvé avec l'ID : " + id));
    }

    public void deleteEntraineur(Long id) {
        if (!entraineurRepository.existsById(id)) {
            throw new RuntimeException("Entraîneur non trouvé avec l'ID : " + id);
        }
        entraineurRepository.deleteById(id);
    }

    // Méthodes liées aux séances

    public Seance planifierSeance(Long idMembre, Long idCoach, LocalDateTime dateTime, Integer capaciteMax) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Impossible de planifier une séance dans le passé");
        }

        Membre membre = membreRepository.findById(idMembre)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'ID : " + idMembre));

        Entraineur entraineur = entraineurRepository.findById(idCoach)
                .orElseThrow(() -> new RuntimeException("Entraîneur non trouvé avec l'ID : " + idCoach));

        List<Seance> seancesEntraineur = seanceRepository.findByEntraineurAndDateBetween(
                entraineur,
                dateTime.minusHours(1),
                dateTime.plusHours(1)
        );

        if (!seancesEntraineur.isEmpty()) {
            throw new RuntimeException("L'entraîneur a déjà une séance prévue à cette heure");
        }

        Seance seance = new Seance();
        seance.setMembre(membre);
        seance.setEntraineur(entraineur);
        seance.setDate(dateTime);
        seance.setCapaciteMax(capaciteMax != null ? capaciteMax : 1);

        return seanceRepository.save(seance);
    }

    public Seance modifierSeance(Long idSeance, Long idMembre, Long idCoach, LocalDateTime date, Integer capaciteMax) {
        Seance seance = seanceRepository.findById(idSeance)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée avec l'ID : " + idSeance));

        Membre membre = membreRepository.findById(idMembre)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'ID : " + idMembre));

        Entraineur entraineur = entraineurRepository.findById(idCoach)
                .orElseThrow(() -> new RuntimeException("Entraîneur non trouvé avec l'ID : " + idCoach));

        seance.setDate(date);
        seance.setMembre(membre);
        seance.setEntraineur(entraineur);
        seance.setCapaciteMax(capaciteMax);

        return seanceRepository.save(seance);
    }


    public void annulerSeance(Long idSeance) {
        if (!seanceRepository.existsById(idSeance)) {
            throw new RuntimeException("Séance non trouvée avec l'ID : " + idSeance);
        }
        seanceRepository.deleteById(idSeance);
    }

    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    public Seance getSeance(Long id) {
        return seanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée avec l'ID : " + id));
    }
}
