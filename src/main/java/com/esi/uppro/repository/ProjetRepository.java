package com.esi.uppro.repository;

import com.esi.uppro.domain.Projet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Projet entity.
 */
@Repository
public interface ProjetRepository extends ProjetRepositoryWithBagRelationships, JpaRepository<Projet, Long> {
    default Optional<Projet> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Projet> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Projet> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct projet from Projet projet left join fetch projet.anneeAcademique",
        countQuery = "select count(distinct projet) from Projet projet"
    )
    Page<Projet> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct projet from Projet projet left join fetch projet.anneeAcademique")
    List<Projet> findAllWithToOneRelationships();

    @Query("select projet from Projet projet left join fetch projet.anneeAcademique where projet.id =:id")
    Optional<Projet> findOneWithToOneRelationships(@Param("id") Long id);
}
