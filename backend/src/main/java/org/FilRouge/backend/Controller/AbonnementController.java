package org.FilRouge.backend.Controller;

import org.FilRouge.backend.Model.Abonnement;
import org.FilRouge.backend.Repository.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/abonnements")
public class AbonnementController {

    @Autowired
    private AbonnementRepository abonnementRepository;

    // Récupérer tous les abonnements
    @GetMapping
    public List<Abonnement> getAllAbonnements() {
        return abonnementRepository.findAll();
    }

    // Récupérer un abonnement par ID
    @GetMapping("/{id}")
    public Abonnement getAbonnementById(@PathVariable Long id) {
        return abonnementRepository.findById(id).orElse(null);
    }

    // Créer un nouvel abonnement
    @PostMapping
    public Abonnement createAbonnement(@RequestBody Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }

    // Mettre à jour un abonnement
    @PutMapping("/{id}")
    public Abonnement updateAbonnement(@PathVariable Long id, @RequestBody Abonnement abonnement) {
        abonnement.setIdAbonnement(id);
        return abonnementRepository.save(abonnement);
    }

    // Supprimer un abonnement
    @DeleteMapping("/{id}")
    public void deleteAbonnement(@PathVariable Long id) {
        abonnementRepository.deleteById(id);
    }
}
