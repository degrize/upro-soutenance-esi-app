package com.esi.uppro.service;

import com.esi.uppro.service.dto.GenieDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.esi.uppro.domain.Genie}.
 */
public interface GenieService {
    /**
     * Save a genie.
     *
     * @param genieDTO the entity to save.
     * @return the persisted entity.
     */
    GenieDTO save(GenieDTO genieDTO);

    /**
     * Updates a genie.
     *
     * @param genieDTO the entity to update.
     * @return the persisted entity.
     */
    GenieDTO update(GenieDTO genieDTO);

    /**
     * Partially updates a genie.
     *
     * @param genieDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GenieDTO> partialUpdate(GenieDTO genieDTO);

    /**
     * Get all the genies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GenieDTO> findAll(Pageable pageable);

    /**
     * Get the "id" genie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GenieDTO> findOne(Long id);

    /**
     * Delete the "id" genie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
