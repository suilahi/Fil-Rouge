package org.FilRouge.backend.Service;

import org.FilRouge.backend.Model.Entraineur;
import org.FilRouge.backend.Repository.EntraineurRepository;
import org.FilRouge.backend.Repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntraineurService {

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private EntraineurRepository entraineurRepository;

    // Annuler une séance
    public void annulerSeance(Long seanceId) {
        if (!seanceRepository.existsById(seanceId)) {
            throw new RuntimeException("Séance non trouvée avec l'ID : " + seanceId);
        }
        seanceRepository.deleteById(seanceId);
    }

    // Modifier un entraîneur
    public Entraineur modifierEntraineur(Long id, Entraineur newEntraineur) {
        Entraineur existingEntraineur = entraineurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entraineur non trouvé avec l'ID : " + id));

        existingEntraineur.setFullName(newEntraineur.getFullName());
        existingEntraineur.setEmail(newEntraineur.getEmail());
        existingEntraineur.setPassword(newEntraineur.getPassword());
        existingEntraineur.setSpecialite(newEntraineur.getSpecialite());
        existingEntraineur.setSeances(newEntraineur.getSeances());

        return entraineurRepository.save(existingEntraineur);
    }
}
