package org.FilRouge.backend.Controller;
import org.FilRouge.backend.Dto.SeanceRequest;
import org.FilRouge.backend.Model.*;
import org.FilRouge.backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Membres

    @PostMapping("/membres")
    public Membre addMembre(@RequestBody Membre membre) {
        return adminService.addMembre(membre);
    }

    @DeleteMapping("/membres/{id}")
    public void deleteMembre(@PathVariable Long id) {
        adminService.deleteMembre(id);
    }

    // Entraîneurs

    @PostMapping("/entraineurs")
    public Entraineur addEntraineur(@RequestBody Entraineur entraineur) {
        return adminService.addEntraineur(entraineur);
    }

    @DeleteMapping("/entraineurs/{id}")
    public void deleteEntraineur(@PathVariable Long id) {
        adminService.deleteEntraineur(id);
    }

    // Séances

    @PostMapping("/seances")
    public Seance planifierSeance(@RequestBody SeanceRequest request) {
        LocalDateTime date = LocalDateTime.parse(request.getDateTime());
        return adminService.planifierSeance(request.getIdMembre(), request.getIdEntraineur(), date);
    }


    @PutMapping("/seances/{id}")
    public Seance modifierSeance(@PathVariable Long id, @RequestBody Seance seance) {
        return adminService.modifierSeance(id, seance);
    }

    @DeleteMapping("/seances/{id}")
    public void annulerSeance(@PathVariable Long id) {
        adminService.annulerSeance(id);
    }

    // Matériel

    @PostMapping("/materiels")
    public Materiel addMateriel(@RequestBody Materiel materiel) {
        return adminService.Addmateriel(materiel);
    }

    @GetMapping("/materiels")
    public List<Materiel> getAllMateriel() {
        return adminService.getAllMateriel();
    }

    @GetMapping("/materiels/{id}")
    public Materiel getMateriel(@PathVariable Long id) {
        return adminService.getMateriel(id);
    }

    @GetMapping("/materiels/stock-bas")
    public List<Materiel> getStockBas(@RequestParam int seuil) {
        return adminService.getMaterielsStockBas(seuil);
    }
}
