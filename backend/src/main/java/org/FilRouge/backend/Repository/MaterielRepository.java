package org.FilRouge.backend.Repository;

import org.FilRouge.backend.Model.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterielRepository extends JpaRepository<Materiel, Long> {
    List<Materiel> findByQuantiteLessThan (int seuil);
}
