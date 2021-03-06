package com.esi.uppro.repository;

import com.esi.uppro.domain.Soutenance;
import com.esi.uppro.domain.enumeration.Mention;
import com.esi.uppro.service.dto.SoutenanceDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Soutenance entity.
 */
@Repository
public interface SoutenanceRepository extends JpaRepository<Soutenance, Long> {
    default Optional<Soutenance> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Soutenance> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Soutenance> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct soutenance from Soutenance soutenance left join fetch soutenance.projet left join fetch soutenance.jury left join fetch soutenance.anneeAcademique",
        countQuery = "select count(distinct soutenance) from Soutenance soutenance"
    )
    Page<Soutenance> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct soutenance from Soutenance soutenance left join fetch soutenance.projet left join fetch soutenance.jury left join fetch soutenance.anneeAcademique"
    )
    List<Soutenance> findAllWithToOneRelationships();

    @Query(
        "select soutenance from Soutenance soutenance left join fetch soutenance.projet left join fetch soutenance.jury left join fetch soutenance.anneeAcademique where soutenance.id =:id"
    )
    Optional<Soutenance> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        "select count(soutenance) from Soutenance soutenance where soutenance.note >= 12 and soutenance.dateDuJour between :startDate and :endDate"
    )
    int nbresoutenancevalide(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    int countByDateAjoutBetween(LocalDate startDate, LocalDate endDate);

    int countByDateDuJourBetween(LocalDate startDate, LocalDate endDate);

    @Query(
        "select count(soutenance) from Soutenance soutenance where soutenance.note < 12 and soutenance.dateDuJour between :startDate and :endDate"
    )
    int nbresoutenancevAjournee(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
        "select count(soutenance) from Soutenance soutenance where soutenance.rapportRendu = true and (soutenance.dateDuJour between :startDate and :endDate)"
    )
    int nbreRapportRendu(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
        "select count(soutenance) from Soutenance soutenance where (soutenance.note >= :note1 and soutenance.note < :note2)  and (soutenance.dateDuJour between :startDate and :endDate)"
    )
    int nbreSoutenanceMention(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("note1") Double note1,
        @Param("note2") Double note2
    );

    @Query("select soutenance from Soutenance soutenance where soutenance.note >= 12")
    List<Soutenance> soutenancesvalide();

    //@Query("select soutenance from Soutenance soutenance where soutenance.projet.id = :id")
    Soutenance findByProjetId(Long id);
}
