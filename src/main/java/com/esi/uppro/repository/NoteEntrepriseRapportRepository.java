package com.esi.uppro.repository;

import com.esi.uppro.domain.NoteEntrepriseRapport;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NoteEntrepriseRapport entity.
 */
@Repository
public interface NoteEntrepriseRapportRepository extends JpaRepository<NoteEntrepriseRapport, Long> {
    default Optional<NoteEntrepriseRapport> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<NoteEntrepriseRapport> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<NoteEntrepriseRapport> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct noteEntrepriseRapport from NoteEntrepriseRapport noteEntrepriseRapport left join fetch noteEntrepriseRapport.entreprise left join fetch noteEntrepriseRapport.projet",
        countQuery = "select count(distinct noteEntrepriseRapport) from NoteEntrepriseRapport noteEntrepriseRapport"
    )
    Page<NoteEntrepriseRapport> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct noteEntrepriseRapport from NoteEntrepriseRapport noteEntrepriseRapport left join fetch noteEntrepriseRapport.entreprise left join fetch noteEntrepriseRapport.projet"
    )
    List<NoteEntrepriseRapport> findAllWithToOneRelationships();

    @Query(
        "select noteEntrepriseRapport from NoteEntrepriseRapport noteEntrepriseRapport left join fetch noteEntrepriseRapport.entreprise left join fetch noteEntrepriseRapport.projet where noteEntrepriseRapport.id =:id"
    )
    Optional<NoteEntrepriseRapport> findOneWithToOneRelationships(@Param("id") Long id);
}
