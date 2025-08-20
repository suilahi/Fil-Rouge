package org.FilRouge.backend.Controller;

import org.FilRouge.backend.Dto.SeanceResponse;
import org.FilRouge.backend.Model.Reservation;
import org.FilRouge.backend.Repository.ReservationRepository;
import org.FilRouge.backend.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MembreController {
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/membre/{membreId}/seances")
    public List<SeanceResponse> getSeancesByMembre(@PathVariable Long membreId) {
        List<Reservation> reservations = reservationRepository.findByMembreId(membreId);

        return reservations.stream()
                .map(res -> new SeanceResponse(res.getSeance(), res.getId()))
                .collect(Collectors.toList());
    }
}
