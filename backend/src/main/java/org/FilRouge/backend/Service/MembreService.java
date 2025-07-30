package org.FilRouge.backend.Service;

import org.FilRouge.backend.Model.Seance;
import org.FilRouge.backend.Repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembreService {
    @Autowired
    private SeanceRepository seanceRepository;

    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    public Seance getSeanceById(Long id) {
        return seanceRepository.findById(id).orElse(null); // ou lancer une exception personnalisée
    }

    public Seance reserveSeance(Seance seance) {

    return seanceRepository.save(seance);
    }



}
