package org.FilRouge.backend.Repository;

import org.FilRouge.backend.Model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembreRepository extends JpaRepository<Membre, Long> {
}
