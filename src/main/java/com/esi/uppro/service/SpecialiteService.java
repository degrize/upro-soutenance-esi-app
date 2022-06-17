package com.esi.uppro.service;

import com.esi.uppro.service.dto.SpecialiteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.esi.uppro.domain.Specialite}.
 */
public interface SpecialiteService {
    /**
     * Save a specialite.
     *
     * @param specialiteDTO the entity to save.
     * @return the persisted entity.
     */
    SpecialiteDTO save(SpecialiteDTO specialiteDTO);

    /**
     * Updates a specialite.
     *
     * @param specialiteDTO the entity to update.
     * @return the persisted entity.
     */
    SpecialiteDTO update(SpecialiteDTO specialiteDTO);

    /**
     * Partially updates a specialite.
     *
     * @param specialiteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SpecialiteDTO> partialUpdate(SpecialiteDTO specialiteDTO);

    /**
     * Get all the specialites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpecialiteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" specialite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpecialiteDTO> findOne(Long id);

    /**
     * Delete the "id" specialite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
