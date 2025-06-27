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

    public void deleteEntraineur(Long id) {
        if (!entraineurRepository.existsById(id)) {
            throw new RuntimeException("Entraîneur non trouvé avec l'ID : " + id);
        }
        entraineurRepository.deleteById(id);
    }

    // Méthodes liées aux séances

    public Seance planifierSeance(Long idMembre, Long idCoach, LocalDateTime dateTime) {
        Membre membre = membreRepository.findById(idMembre)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'ID : " + idMembre));

        Entraineur entraineur = entraineurRepository.findById(idCoach)
                .orElseThrow(() -> new RuntimeException("Entraîneur non trouvé avec l'ID : " + idCoach));

        Seance seance = new Seance();
        seance.setMembre(membre);
        seance.setEntraineur(entraineur);
        seance.setDate(dateTime.toLocalDate().atStartOfDay()); // Conversion LocalDateTime → LocalDate

        return seanceRepository.save(seance);
    }

    public Seance modifierSeance(Long idSeance, Seance nouvellesInfos) {
        Seance seance = seanceRepository.findById(idSeance)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée avec l'ID : " + idSeance));

        seance.setDate(nouvellesInfos.getDate());
        seance.setEntraineur(nouvellesInfos.getEntraineur());
        seance.setMembre(nouvellesInfos.getMembre());

        return seanceRepository.save(seance);
    }

    public void annulerSeance(Long idSeance) {
        if (!seanceRepository.existsById(idSeance)) {
            throw new RuntimeException("Séance non trouvée avec l'ID : " + idSeance);
        }
        seanceRepository.deleteById(idSeance);
    }
}
