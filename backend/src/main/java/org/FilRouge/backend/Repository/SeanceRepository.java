package org.FilRouge.backend.Repository;

import org.FilRouge.backend.Model.Entraineur;
import org.FilRouge.backend.Model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
    List<Seance> findByEntraineurAndDateBetween(Entraineur entraineur, LocalDateTime start, LocalDateTime end);

    List<Seance> findByIdSeance(Seance seance);



}
