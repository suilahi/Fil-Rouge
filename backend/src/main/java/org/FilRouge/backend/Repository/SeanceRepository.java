package org.FilRouge.backend.Repository;

import org.FilRouge.backend.Model.Entraineur;
import org.FilRouge.backend.Model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
    List<Seance> findByEntraineurAndDateBetween(Entraineur entraineur, LocalDateTime start, LocalDateTime end);

    List<Seance> findByIdSeance(Seance seance);

    @Query(value = " select * from seance\n" +
            " inner join utilisateur \n" +
            " on seance.entraineur_id= utilisateur.id\n" +
            " where utilisateur.full_name= \"rida\";",nativeQuery = true)
    List<Seance> findByFullName(String fullName);

    List<Seance> findByEntraineurId(Long entraineurId);

}


