package org.FilRouge.backend.Service;

import org.FilRouge.backend.Model.Seance;
import org.FilRouge.backend.Repository.MembreRepository;
import org.FilRouge.backend.Repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembreService {
    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private MembreRepository membreRepository;

    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }





}
