package org.FilRouge.backend.Repository;

import org.FilRouge.backend.Model.Membre;
import org.FilRouge.backend.Model.Reservation;
import org.FilRouge.backend.Model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    boolean existsByMembreAndSeance(Membre membre, Seance seance);

    long countBySeance(Seance seance);
}
