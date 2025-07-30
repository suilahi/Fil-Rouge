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

    public String reserverSeance(Long membreId, Long seanceId) {
        Optional<Membre> membreOpt = membreRepository.findById(membreId);
        Optional<Seance> seanceOpt = seanceRepository.findById(seanceId);

        if (membreOpt.isEmpty()) {
            return "Membre non trouvé";
        }
        if (seanceOpt.isEmpty()) {
            return "Séance non trouvée";
        }

        Membre membre = membreOpt.get();
        Seance seance = seanceOpt.get();

        // Vérifier si le membre a déjà réservé la séance
        if (reservationRepository.existsByMembreAndSeance(membre, seance)) {
            return "Vous avez déjà réservé cette séance.";
        }

        // Vérifier la capacité max de la séance
        long nbReservations = reservationRepository.countBySeance(seance);
        if (nbReservations >= seance.getCapaciteMax()) {
            return "La séance est complète.";
        }

        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setMembre(membre);
        reservation.setSeance(seance);

        reservationRepository.save(reservation);

        return "Réservation réussie.";
    }

}
