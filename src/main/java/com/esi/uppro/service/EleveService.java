package com.esi.uppro.service;

import com.esi.uppro.domain.Eleve;
import com.esi.uppro.service.dto.EleveDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.esi.uppro.domain.Eleve}.
 */
public interface EleveService {
    /**
     * Save a eleve.
     *
     * @param eleveDTO the entity to save.
     * @return the persisted entity.
     */
    EleveDTO save(EleveDTO eleveDTO);

    /**
     * Updates a eleve.
     *
     * @param eleveDTO the entity to update.
     * @return the persisted entity.
     */
    EleveDTO update(EleveDTO eleveDTO);

    /**
     * Partially updates a eleve.
     *
     * @param eleveDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EleveDTO> partialUpdate(EleveDTO eleveDTO);

    /**
     * Get all the eleves.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EleveDTO> findAll(Pageable pageable);

    /**
     * Get all the eleves with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EleveDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" eleve.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EleveDTO> findOne(Long id);

    /**
     * Delete the "id" eleve.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Eleve> getEleves();
}
