package org.FilRouge.backend.Controller;

import jakarta.validation.Valid;
import org.FilRouge.backend.Dto.SeanceRequest;
import org.FilRouge.backend.Dto.SeanceResponse;
import org.FilRouge.backend.Model.Reservation;
import org.FilRouge.backend.Model.Seance;
import org.FilRouge.backend.Repository.ReservationRepository;
import org.FilRouge.backend.Service.ReservationService;
import org.FilRouge.backend.Service.SeanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seances")
@PreAuthorize("hasRole('ADMIN')")
public class SeanceController {
    @Autowired
    private SeanceService seanceService;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/entraineurs/{fullname}")
    public List<Seance> getseances(@PathVariable String fullname){
        return seanceService.findByFullName(fullname);
    }

    @PostMapping("/seances")
    public Seance planifierSeance(@Valid @RequestBody SeanceRequest request) {
        LocalDateTime date = LocalDateTime.parse(request.getDateTime());
        return seanceService.planifierSeance(request.getNomSeance(), request.getIdEntraineur(), date, request.getCapaciteMax());
    }


    @GetMapping("/seances")
    public List<SeanceResponse> getAllSeances() {
        return seanceService.getAllSeances()
                .stream()
                .map(SeanceResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/membre/{membreId}/seances")
    public List<SeanceResponse> getSeancesByMembre(@PathVariable Long membreId) {
        List<Reservation> reservations = reservationRepository.findByMembreId(membreId);

        return reservations.stream()
                .map(res -> new SeanceResponse(res.getSeance(), res.getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/seances/{id}")
    public Seance getSeance(@PathVariable Long id) {
        return seanceService.getSeance(id);
    }

    @PutMapping("/seances/{id}")
    public Seance modifierSeance(@PathVariable Long id, @Valid @RequestBody SeanceRequest request) {
        LocalDateTime date = LocalDateTime.parse(request.getDateTime());
        return seanceService.modifierSeance(request.getNomSeance(),id, request.getIdMembre(), request.getIdEntraineur(), date, request.getCapaciteMax());
    }


    @DeleteMapping("/seances/{id}")
    public void annulerSeance(@PathVariable Long id) {
        seanceService.annulerSeance(id);
    }


}
