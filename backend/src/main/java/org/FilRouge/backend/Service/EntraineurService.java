package org.FilRouge.backend.Service;

import org.FilRouge.backend.Model.Entraineur;
import org.FilRouge.backend.Model.Seance;
import org.FilRouge.backend.Repository.EntraineurRepository;
import org.FilRouge.backend.Repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    public List<Seance> getSeancesForCurrentEntraineur() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email stocké dans le JWT

        Entraineur entraineur = entraineurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Entraîneur non trouvé avec l'email : " + email));

        return seanceRepository.findByEntraineurId(entraineur.getId());
    }
}
