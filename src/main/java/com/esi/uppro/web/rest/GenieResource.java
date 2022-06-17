package com.esi.uppro.web.rest;

import com.esi.uppro.repository.GenieRepository;
import com.esi.uppro.service.GenieService;
import com.esi.uppro.service.dto.GenieDTO;
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
 * REST controller for managing {@link com.esi.uppro.domain.Genie}.
 */
@RestController
@RequestMapping("/api")
public class GenieResource {

    private final Logger log = LoggerFactory.getLogger(GenieResource.class);

    private static final String ENTITY_NAME = "genie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenieService genieService;

    private final GenieRepository genieRepository;

    public GenieResource(GenieService genieService, GenieRepository genieRepository) {
        this.genieService = genieService;
        this.genieRepository = genieRepository;
    }

    /**
     * {@code POST  /genies} : Create a new genie.
     *
     * @param genieDTO the genieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new genieDTO, or with status {@code 400 (Bad Request)} if the genie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/genies")
    public ResponseEntity<GenieDTO> createGenie(@Valid @RequestBody GenieDTO genieDTO) throws URISyntaxException {
        log.debug("REST request to save Genie : {}", genieDTO);
        if (genieDTO.getId() != null) {
            throw new BadRequestAlertException("A new genie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenieDTO result = genieService.save(genieDTO);
        return ResponseEntity
            .created(new URI("/api/genies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /genies/:id} : Updates an existing genie.
     *
     * @param id the id of the genieDTO to save.
     * @param genieDTO the genieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genieDTO,
     * or with status {@code 400 (Bad Request)} if the genieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the genieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/genies/{id}")
    public ResponseEntity<GenieDTO> updateGenie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GenieDTO genieDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Genie : {}, {}", id, genieDTO);
        if (genieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GenieDTO result = genieService.update(genieDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, genieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /genies/:id} : Partial updates given fields of an existing genie, field will ignore if it is null
     *
     * @param id the id of the genieDTO to save.
     * @param genieDTO the genieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genieDTO,
     * or with status {@code 400 (Bad Request)} if the genieDTO is not valid,
     * or with status {@code 404 (Not Found)} if the genieDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the genieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/genies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GenieDTO> partialUpdateGenie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GenieDTO genieDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Genie partially : {}, {}", id, genieDTO);
        if (genieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GenieDTO> result = genieService.partialUpdate(genieDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, genieDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /genies} : get all the genies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of genies in body.
     */
    @GetMapping("/genies")
    public ResponseEntity<List<GenieDTO>> getAllGenies(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Genies");
        Page<GenieDTO> page = genieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /genies/:id} : get the "id" genie.
     *
     * @param id the id of the genieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the genieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/genies/{id}")
    public ResponseEntity<GenieDTO> getGenie(@PathVariable Long id) {
        log.debug("REST request to get Genie : {}", id);
        Optional<GenieDTO> genieDTO = genieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(genieDTO);
    }

    /**
     * {@code DELETE  /genies/:id} : delete the "id" genie.
     *
     * @param id the id of the genieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/genies/{id}")
    public ResponseEntity<Void> deleteGenie(@PathVariable Long id) {
        log.debug("REST request to delete Genie : {}", id);
        genieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
