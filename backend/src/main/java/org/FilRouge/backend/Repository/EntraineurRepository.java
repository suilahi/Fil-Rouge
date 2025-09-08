package org.FilRouge.backend.Repository;

import org.FilRouge.backend.Model.Entraineur;
import org.FilRouge.backend.Model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntraineurRepository extends JpaRepository<Entraineur, Long> {
    Optional<Entraineur> findByEmail(String email);


}
