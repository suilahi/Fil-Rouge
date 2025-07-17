package org.FilRouge.backend.Controller;
import jakarta.validation.Valid;
import org.FilRouge.backend.Dto.SeanceRequest;
import org.FilRouge.backend.Model.*;
import org.FilRouge.backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PreAuthorize("hasRole('ADMIN')")
    // Test endpoint
    @GetMapping("/test")
    public String test() {
        return "Backend is working!";
    }

    // Membres

    @PostMapping("/membres")
    public Membre addMembre(@RequestBody Membre membre) {
        return adminService.addMembre(membre);
    }

    @GetMapping("/membres")
    public List<Membre> getAllMembres() {
        return adminService.getAllMembre();
    }

    @GetMapping("/membres/{id}")
    public Membre getMembre(@PathVariable Long id) {
        return adminService.getMembre(id);
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

    @GetMapping("/entraineurs")
    public List<Entraineur> getAllEntraineurs() {
        return adminService.getAllEntraineur();
    }

    @GetMapping("/entraineurs/{id}")
    public Entraineur getEntraineur(@PathVariable Long id) {
        return adminService.getEntraineur(id);
    }

    @DeleteMapping("/entraineurs/{id}")
    public void deleteEntraineur(@PathVariable Long id) {
        adminService.deleteEntraineur(id);
    }

    // Séances
    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping("/seances")
    public Seance planifierSeance(@Valid @RequestBody SeanceRequest request) {
        LocalDateTime date = LocalDateTime.parse(request.getDateTime());
        return adminService.planifierSeance(request.getIdMembre(), request.getIdEntraineur(), date, request.getCapaciteMax());
    }

    @GetMapping("/seances")
    public List<Seance> getAllSeances() {
        return adminService.getAllSeances();
    }

    @GetMapping("/seances/{id}")
    public Seance getSeance(@PathVariable Long id) {
        return adminService.getSeance(id);
    }

    @PutMapping("/seances/{id}")
    public Seance modifierSeance(@PathVariable Long id, @Valid @RequestBody SeanceRequest request) {
        LocalDateTime date = LocalDateTime.parse(request.getDateTime());
        return adminService.modifierSeance(id, request.getIdMembre(), request.getIdEntraineur(), date, request.getCapaciteMax());
    }


    @DeleteMapping("/seances/{id}")
    public void annulerSeance(@PathVariable Long id) {
        adminService.annulerSeance(id);
    }

    // Matériel

    @PostMapping("/materiels")
    public Materiel addMateriel(@Valid @RequestBody Materiel materiel) {
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
