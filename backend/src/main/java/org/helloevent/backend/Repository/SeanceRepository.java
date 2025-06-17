package org.helloevent.backend.Repository;

import org.helloevent.backend.Model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeanceRepository extends JpaRepository<Seance, Integer> {
}
