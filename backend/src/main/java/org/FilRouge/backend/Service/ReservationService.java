package org.FilRouge.backend.Service;

import org.FilRouge.backend.Model.Membre;
import org.FilRouge.backend.Model.Reservation;
import org.FilRouge.backend.Model.Seance;
import org.FilRouge.backend.Repository.MembreRepository;
import org.FilRouge.backend.Repository.ReservationRepository;
import org.FilRouge.backend.Repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MembreRepository membreRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    public Reservation reserverSeance(Long membreId, Long seanceId) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));

        Seance seance = seanceRepository.findById(seanceId)
                .orElseThrow(() -> new RuntimeException("Séance introuvable"));

        // Vérifier si le membre a déjà une réservation pour cette séance
        boolean alreadyReserved = reservationRepository.existsByMembreIdAndSeanceId(membreId, seanceId);
        if (alreadyReserved) {
            throw new RuntimeException("Vous avez déjà réservé cette séance.");
        }

        long countReservations = reservationRepository.countBySeanceId(seanceId);
        if (countReservations >= seance.getCapaciteMax()) {
            throw new RuntimeException("Capacité maximale atteinte");
        }

        Reservation reservation = new Reservation(membre, seance);
        return reservationRepository.save(reservation);
    }
}
