package org.FilRouge.backend.Controller;

import org.FilRouge.backend.Dto.SeanceResponse;
import org.FilRouge.backend.Model.Entraineur;
import org.FilRouge.backend.Service.EntraineurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entraineurs")
@CrossOrigin(origins = "*")
public class EntraineurController {

    @Autowired
    private EntraineurService entraineurService;

    // Annuler une séance
    @DeleteMapping("/seances/{id}")
    public void annulerSeance(@PathVariable("id") Long seanceId) {
        entraineurService.annulerSeance(seanceId);
    }

    // Modifier un entraîneur
    @PutMapping("/{id}")
    public Entraineur modifierInfo(@PathVariable Long id, @RequestBody Entraineur entraineur) {
        return entraineurService.modifierEntraineur(id, entraineur);
    }


    @GetMapping("/seances")
    public List<SeanceResponse> getAllSeances() {
        return entraineurService.getAllSeances()
                .stream()
                .map(SeanceResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/mes-seances")
    public List<SeanceResponse> getMesSeances() {
        return entraineurService.getSeancesForCurrentEntraineur()
                .stream()
                .map(SeanceResponse::new)
                .collect(Collectors.toList());
    }

}
