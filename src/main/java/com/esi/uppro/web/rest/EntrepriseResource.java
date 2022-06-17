package com.esi.uppro.web.rest;

import com.esi.uppro.repository.EntrepriseRepository;
import com.esi.uppro.service.EntrepriseService;
import com.esi.uppro.service.dto.EntrepriseDTO;
import com.esi.uppro.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.esi.uppro.domain.Entreprise}.
 */
@RestController
@RequestMapping("/api")
public class EntrepriseResource {

    private final Logger log = LoggerFactory.getLogger(EntrepriseResource.class);

    private static final String ENTITY_NAME = "entreprise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntrepriseService entrepriseService;

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseResource(EntrepriseService entrepriseService, EntrepriseRepository entrepriseRepository) {
        this.entrepriseService = entrepriseService;
        this.entrepriseRepository = entrepriseRepository;
    }

    /**
     * {@code POST  /entreprises} : Create a new entreprise.
     *
     * @param entrepriseDTO the entrepriseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entrepriseDTO, or with status {@code 400 (Bad Request)} if the entreprise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entreprises")
    public ResponseEntity<EntrepriseDTO> createEntreprise(@Valid @RequestBody EntrepriseDTO entrepriseDTO) throws URISyntaxException {
        log.debug("REST request to save Entreprise : {}", entrepriseDTO);
        if (entrepriseDTO.getId() != null) {
            throw new BadRequestAlertException("A new entreprise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntrepriseDTO result = entrepriseService.save(entrepriseDTO);
        return ResponseEntity
            .created(new URI("/api/entreprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entreprises/:id} : Updates an existing entreprise.
     *
     * @param id the id of the entrepriseDTO to save.
     * @param entrepriseDTO the entrepriseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entrepriseDTO,
     * or with status {@code 400 (Bad Request)} if the entrepriseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entrepriseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entreprises/{id}")
    public ResponseEntity<EntrepriseDTO> updateEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EntrepriseDTO entrepriseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Entreprise : {}, {}", id, entrepriseDTO);
        if (entrepriseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entrepriseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntrepriseDTO result = entrepriseService.update(entrepriseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entrepriseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entreprises/:id} : Partial updates given fields of an existing entreprise, field will ignore if it is null
     *
     * @param id the id of the entrepriseDTO to save.
     * @param entrepriseDTO the entrepriseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entrepriseDTO,
     * or with status {@code 400 (Bad Request)} if the entrepriseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entrepriseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entrepriseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entreprises/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntrepriseDTO> partialUpdateEntreprise(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EntrepriseDTO entrepriseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entreprise partially : {}, {}", id, entrepriseDTO);
        if (entrepriseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entrepriseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntrepriseDTO> result = entrepriseService.partialUpdate(entrepriseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entrepriseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entreprises} : get all the entreprises.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entreprises in body.
     */
    @GetMapping("/entreprises")
    public ResponseEntity<List<EntrepriseDTO>> getAllEntreprises(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Entreprises");
        Page<EntrepriseDTO> page = entrepriseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entreprises/:id} : get the "id" entreprise.
     *
     * @param id the id of the entrepriseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entrepriseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entreprises/{id}")
    public ResponseEntity<EntrepriseDTO> getEntreprise(@PathVariable Long id) {
        log.debug("REST request to get Entreprise : {}", id);
        Optional<EntrepriseDTO> entrepriseDTO = entrepriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entrepriseDTO);
    }

    /**
     * {@code DELETE  /entreprises/:id} : delete the "id" entreprise.
     *
     * @param id the id of the entrepriseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entreprises/{id}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Long id) {
        log.debug("REST request to delete Entreprise : {}", id);
        entrepriseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
