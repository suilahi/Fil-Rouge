package org.FilRouge.backend.Repository;

import org.FilRouge.backend.Model.Reservation;
import org.FilRouge.backend.Model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.seance.idSeance = :seanceId")
    long countBySeanceId(@Param("seanceId") Long seanceId);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.membre.id = :membreId AND r.seance.idSeance = :seanceId")
    boolean existsByMembreIdAndSeanceId(@Param("membreId") Long membreId, @Param("seanceId") Long seanceId);

    @Query("SELECT r.seance FROM Reservation r WHERE r.membre.id = :membreId")
    List<Seance> findSeancesByMembreId(@Param("membreId") Long membreId);

    List<Reservation> findByMembreId(Long membreId);

}
