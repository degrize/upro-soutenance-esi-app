package com.esi.uppro.service;

import com.esi.uppro.service.dto.AnneeAcademiqueDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.esi.uppro.domain.AnneeAcademique}.
 */
public interface AnneeAcademiqueService {
    /**
     * Save a anneeAcademique.
     *
     * @param anneeAcademiqueDTO the entity to save.
     * @return the persisted entity.
     */
    AnneeAcademiqueDTO save(AnneeAcademiqueDTO anneeAcademiqueDTO);

    /**
     * Updates a anneeAcademique.
     *
     * @param anneeAcademiqueDTO the entity to update.
     * @return the persisted entity.
     */
    AnneeAcademiqueDTO update(AnneeAcademiqueDTO anneeAcademiqueDTO);

    /**
     * Partially updates a anneeAcademique.
     *
     * @param anneeAcademiqueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnneeAcademiqueDTO> partialUpdate(AnneeAcademiqueDTO anneeAcademiqueDTO);

    /**
     * Get all the anneeAcademiques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnneeAcademiqueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" anneeAcademique.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnneeAcademiqueDTO> findOne(Long id);

    /**
     * Delete the "id" anneeAcademique.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
