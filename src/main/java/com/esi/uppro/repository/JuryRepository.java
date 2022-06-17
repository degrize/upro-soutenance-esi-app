package com.esi.uppro.repository;

import com.esi.uppro.domain.Jury;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Jury entity.
 */
@Repository
public interface JuryRepository extends JpaRepository<Jury, Long> {
    default Optional<Jury> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Jury> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Jury> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct jury from Jury jury left join fetch jury.anneeAcademique left join fetch jury.genie",
        countQuery = "select count(distinct jury) from Jury jury"
    )
    Page<Jury> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct jury from Jury jury left join fetch jury.anneeAcademique left join fetch jury.genie")
    List<Jury> findAllWithToOneRelationships();

    @Query("select jury from Jury jury left join fetch jury.anneeAcademique left join fetch jury.genie where jury.id =:id")
    Optional<Jury> findOneWithToOneRelationships(@Param("id") Long id);
}
