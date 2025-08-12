package org.FilRouge.backend.Controller;


import org.FilRouge.backend.Model.Reservation;
import org.FilRouge.backend.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/membre/{membreId}/seance/{seanceId}")
    public ResponseEntity<String> reserverSeance(
            @PathVariable Long membreId,
            @PathVariable Long seanceId) {

        try {
            Reservation result = reservationService.reserverSeance(membreId, seanceId);
            return ResponseEntity.ok("Réservation réussie.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    }
