package com.esi.uppro.web.rest;

import com.esi.uppro.domain.Eleve;
import com.esi.uppro.repository.EleveRepository;
import com.esi.uppro.service.EleveService;
import com.esi.uppro.service.dto.EleveDTO;
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
 * REST controller for managing {@link com.esi.uppro.domain.Eleve}.
 */
@RestController
@RequestMapping("/api")
public class EleveResource {

    private final Logger log = LoggerFactory.getLogger(EleveResource.class);

    private static final String ENTITY_NAME = "eleve";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EleveService eleveService;

    private final EleveRepository eleveRepository;

    public EleveResource(EleveService eleveService, EleveRepository eleveRepository) {
        this.eleveService = eleveService;
        this.eleveRepository = eleveRepository;
    }

    /**
     * {@code POST  /eleves} : Create a new eleve.
     *
     * @param eleveDTO the eleveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eleveDTO, or with status {@code 400 (Bad Request)} if the eleve has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eleves")
    public ResponseEntity<EleveDTO> createEleve(@Valid @RequestBody EleveDTO eleveDTO) throws URISyntaxException {
        log.debug("REST request to save Eleve : {}", eleveDTO);
        if (eleveDTO.getId() != null) {
            throw new BadRequestAlertException("A new eleve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EleveDTO result = eleveService.save(eleveDTO);
        return ResponseEntity
            .created(new URI("/api/eleves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eleves/:id} : Updates an existing eleve.
     *
     * @param id the id of the eleveDTO to save.
     * @param eleveDTO the eleveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eleveDTO,
     * or with status {@code 400 (Bad Request)} if the eleveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eleveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eleves/{id}")
    public ResponseEntity<EleveDTO> updateEleve(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EleveDTO eleveDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Eleve : {}, {}", id, eleveDTO);
        if (eleveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eleveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eleveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EleveDTO result = eleveService.update(eleveDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eleveDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /eleves/:id} : Partial updates given fields of an existing eleve, field will ignore if it is null
     *
     * @param id the id of the eleveDTO to save.
     * @param eleveDTO the eleveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eleveDTO,
     * or with status {@code 400 (Bad Request)} if the eleveDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eleveDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eleveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eleves/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EleveDTO> partialUpdateEleve(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EleveDTO eleveDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eleve partially : {}, {}", id, eleveDTO);
        if (eleveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eleveDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eleveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EleveDTO> result = eleveService.partialUpdate(eleveDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eleveDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /eleves} : get all the eleves.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eleves in body.
     */
    @GetMapping("/eleves")
    public ResponseEntity<List<EleveDTO>> getAllEleves(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Eleves");
        Page<EleveDTO> page;
        page = eleveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eleves/:id} : get the "id" eleve.
     *
     * @param id the id of the eleveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eleveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eleves/{id}")
    public ResponseEntity<EleveDTO> getEleve(@PathVariable Long id) {
        log.debug("REST request to get Eleve : {}", id);
        Optional<EleveDTO> eleveDTO = eleveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eleveDTO);
    }

    /**
     * {@code DELETE  /eleves/:id} : delete the "id" eleve.
     *
     * @param id the id of the eleveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eleves/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable Long id) {
        log.debug("REST request to delete Eleve : {}", id);
        eleveService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/eleves/soutenance")
    public ResponseEntity<List<Eleve>> getAllElevesSoutenance() {
        log.debug("REST request to get a page of Eleves");
        List<Eleve> result = eleveService.getEleves();

        return ResponseEntity.ok().body(result);
    }
}
