package org.FilRouge.backend.Service;

import org.FilRouge.backend.Model.Entraineur;
import org.FilRouge.backend.Model.Membre;
import org.FilRouge.backend.Model.Seance;
import org.FilRouge.backend.Repository.EntraineurRepository;
import org.FilRouge.backend.Repository.MembreRepository;
import org.FilRouge.backend.Repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeanceService {

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private EntraineurRepository entraineurRepository;

    @Autowired
    private MembreRepository membreRepository;

    public Seance planifierSeance(String nomSeance, Long idCoach, LocalDateTime dateTime, Integer capaciteMax) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Impossible de planifier une séance dans le passé");
        }

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
        seance.setNomSeance(nomSeance);
        seance.setEntraineur(entraineur);
        seance.setDate(dateTime);
        seance.setCapaciteMax(capaciteMax != null ? capaciteMax : 1);

        return seanceRepository.save(seance);
    }


    public Seance modifierSeance(String nomSeance,Long idSeance, Long idMembre, Long idCoach, LocalDateTime date, Integer capaciteMax) {
        Seance seance = seanceRepository.findById(idSeance)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée avec l'ID : " + idSeance));

        Membre membre = membreRepository.findById(idMembre)
                .orElseThrow(() -> new RuntimeException("Membre non trouvé avec l'ID : " + idMembre));

        Entraineur entraineur = entraineurRepository.findById(idCoach)
                .orElseThrow(() -> new RuntimeException("Entraîneur non trouvé avec l'ID : " + idCoach));

        seance.setNomSeance(nomSeance);
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

    public List<Seance> findByFullName(String fullName) {
        return seanceRepository.findByFullName(fullName);
    }
}
