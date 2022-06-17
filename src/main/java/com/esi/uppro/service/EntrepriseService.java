package com.esi.uppro.service;

import com.esi.uppro.service.dto.EntrepriseDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.esi.uppro.domain.Entreprise}.
 */
public interface EntrepriseService {
    /**
     * Save a entreprise.
     *
     * @param entrepriseDTO the entity to save.
     * @return the persisted entity.
     */
    EntrepriseDTO save(EntrepriseDTO entrepriseDTO);

    /**
     * Updates a entreprise.
     *
     * @param entrepriseDTO the entity to update.
     * @return the persisted entity.
     */
    EntrepriseDTO update(EntrepriseDTO entrepriseDTO);

    /**
     * Partially updates a entreprise.
     *
     * @param entrepriseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EntrepriseDTO> partialUpdate(EntrepriseDTO entrepriseDTO);

    /**
     * Get all the entreprises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntrepriseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" entreprise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntrepriseDTO> findOne(Long id);

    /**
     * Delete the "id" entreprise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
