package com.esi.uppro.service;

import com.esi.uppro.service.dto.NoteEntrepriseRapportDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.esi.uppro.domain.NoteEntrepriseRapport}.
 */
public interface NoteEntrepriseRapportService {
    /**
     * Save a noteEntrepriseRapport.
     *
     * @param noteEntrepriseRapportDTO the entity to save.
     * @return the persisted entity.
     */
    NoteEntrepriseRapportDTO save(NoteEntrepriseRapportDTO noteEntrepriseRapportDTO);

    /**
     * Updates a noteEntrepriseRapport.
     *
     * @param noteEntrepriseRapportDTO the entity to update.
     * @return the persisted entity.
     */
    NoteEntrepriseRapportDTO update(NoteEntrepriseRapportDTO noteEntrepriseRapportDTO);

    /**
     * Partially updates a noteEntrepriseRapport.
     *
     * @param noteEntrepriseRapportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NoteEntrepriseRapportDTO> partialUpdate(NoteEntrepriseRapportDTO noteEntrepriseRapportDTO);

    /**
     * Get all the noteEntrepriseRapports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NoteEntrepriseRapportDTO> findAll(Pageable pageable);

    /**
     * Get all the noteEntrepriseRapports with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NoteEntrepriseRapportDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" noteEntrepriseRapport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoteEntrepriseRapportDTO> findOne(Long id);

    /**
     * Delete the "id" noteEntrepriseRapport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
