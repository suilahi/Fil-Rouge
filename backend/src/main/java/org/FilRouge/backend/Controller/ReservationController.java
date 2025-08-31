    package org.FilRouge.backend.Controller;


    import org.FilRouge.backend.Dto.SeanceResponse;
    import org.FilRouge.backend.Model.Reservation;
    import org.FilRouge.backend.Service.ReservationService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.stream.Collectors;

    @RestController
    @RequestMapping("/api/reservation")
    public class ReservationController {

        @Autowired
        private ReservationService reservationService;

        @GetMapping("/seances")
        public List<SeanceResponse> getAllSeances() {
            return reservationService.getAllSeances()
                    .stream()
                    .map(SeanceResponse::new)
                    .collect(Collectors.toList());
        }

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



        @DeleteMapping("/seance/{seanceId}/reservation/{reservationId}")
        public void annuleReservation(
                @PathVariable Long seanceId,
                @PathVariable Long reservationId) {
            reservationService.annulerReservation(reservationId);
        }


        @GetMapping("/membre/{membreId}/seances")
        public List<SeanceResponse> getSeancesByMembre(@PathVariable Long membreId) {
            return reservationService.getReservationsByMembre(membreId)
                    .stream()
                    .map(res -> new SeanceResponse(res.getSeance(), res.getId()))
                    .collect(Collectors.toList());
        }
        
    }
