package org.FilRouge.backend.Model;

import org.FilRouge.backend.Service.AdminService;
import org.FilRouge.backend.Model.Seance;
import org.FilRouge.backend.Model.Membre;
import org.FilRouge.backend.Model.Entraineur;
import org.FilRouge.backend.Repository.MembreRepository;
import org.FilRouge.backend.Repository.EntraineurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class SeanceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private EntraineurRepository entraineurRepository;

    @Test
    void createSeance() {
        // Créer un membre de test
        Membre membre = new Membre();
        membre.setEmail("membre@test.com");
        membre.setPassword("1234");
        Membre savedMembre = membreRepository.save(membre);

        // Créer un entraîneur de test
        Entraineur entraineur = new Entraineur();
        entraineur.setEmail("coach@test.com");
        entraineur.setPassword("1234");
        Entraineur savedCoach = entraineurRepository.save(entraineur);

        // Planifier une séance
        LocalDateTime dateSeance = LocalDateTime.now().plusDays(1);
        int capacite = 10;

        Seance seanceCree = adminService.planifierSeance(
                savedMembre.getId(),
                savedCoach.getId(),
                dateSeance,
                capacite
        );


        assertNotNull(seanceCree);
        assertEquals(dateSeance, seanceCree.getDate());
        assertEquals(savedMembre.getId(), seanceCree.getMembre().getId());
        assertEquals(savedCoach.getId(), seanceCree.getEntraineur().getId());
        assertEquals(capacite, seanceCree.getCapaciteMax());
    }
}
