package com.esi.uppro.web.rest;

import com.esi.uppro.repository.NoteEntrepriseRapportRepository;
import com.esi.uppro.service.NoteEntrepriseRapportService;
import com.esi.uppro.service.dto.NoteEntrepriseRapportDTO;
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
 * REST controller for managing {@link com.esi.uppro.domain.NoteEntrepriseRapport}.
 */
@RestController
@RequestMapping("/api")
public class NoteEntrepriseRapportResource {

    private final Logger log = LoggerFactory.getLogger(NoteEntrepriseRapportResource.class);

    private static final String ENTITY_NAME = "noteEntrepriseRapport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoteEntrepriseRapportService noteEntrepriseRapportService;

    private final NoteEntrepriseRapportRepository noteEntrepriseRapportRepository;

    public NoteEntrepriseRapportResource(
        NoteEntrepriseRapportService noteEntrepriseRapportService,
        NoteEntrepriseRapportRepository noteEntrepriseRapportRepository
    ) {
        this.noteEntrepriseRapportService = noteEntrepriseRapportService;
        this.noteEntrepriseRapportRepository = noteEntrepriseRapportRepository;
    }

    /**
     * {@code POST  /note-entreprise-rapports} : Create a new noteEntrepriseRapport.
     *
     * @param noteEntrepriseRapportDTO the noteEntrepriseRapportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noteEntrepriseRapportDTO, or with status {@code 400 (Bad Request)} if the noteEntrepriseRapport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/note-entreprise-rapports")
    public ResponseEntity<NoteEntrepriseRapportDTO> createNoteEntrepriseRapport(
        @Valid @RequestBody NoteEntrepriseRapportDTO noteEntrepriseRapportDTO
    ) throws URISyntaxException {
        log.debug("REST request to save NoteEntrepriseRapport : {}", noteEntrepriseRapportDTO);
        if (noteEntrepriseRapportDTO.getId() != null) {
            throw new BadRequestAlertException("A new noteEntrepriseRapport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoteEntrepriseRapportDTO result = noteEntrepriseRapportService.save(noteEntrepriseRapportDTO);
        return ResponseEntity
            .created(new URI("/api/note-entreprise-rapports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /note-entreprise-rapports/:id} : Updates an existing noteEntrepriseRapport.
     *
     * @param id the id of the noteEntrepriseRapportDTO to save.
     * @param noteEntrepriseRapportDTO the noteEntrepriseRapportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noteEntrepriseRapportDTO,
     * or with status {@code 400 (Bad Request)} if the noteEntrepriseRapportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noteEntrepriseRapportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/note-entreprise-rapports/{id}")
    public ResponseEntity<NoteEntrepriseRapportDTO> updateNoteEntrepriseRapport(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NoteEntrepriseRapportDTO noteEntrepriseRapportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NoteEntrepriseRapport : {}, {}", id, noteEntrepriseRapportDTO);
        if (noteEntrepriseRapportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noteEntrepriseRapportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noteEntrepriseRapportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NoteEntrepriseRapportDTO result = noteEntrepriseRapportService.update(noteEntrepriseRapportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noteEntrepriseRapportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /note-entreprise-rapports/:id} : Partial updates given fields of an existing noteEntrepriseRapport, field will ignore if it is null
     *
     * @param id the id of the noteEntrepriseRapportDTO to save.
     * @param noteEntrepriseRapportDTO the noteEntrepriseRapportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noteEntrepriseRapportDTO,
     * or with status {@code 400 (Bad Request)} if the noteEntrepriseRapportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the noteEntrepriseRapportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the noteEntrepriseRapportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/note-entreprise-rapports/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NoteEntrepriseRapportDTO> partialUpdateNoteEntrepriseRapport(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NoteEntrepriseRapportDTO noteEntrepriseRapportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NoteEntrepriseRapport partially : {}, {}", id, noteEntrepriseRapportDTO);
        if (noteEntrepriseRapportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, noteEntrepriseRapportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!noteEntrepriseRapportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NoteEntrepriseRapportDTO> result = noteEntrepriseRapportService.partialUpdate(noteEntrepriseRapportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noteEntrepriseRapportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /note-entreprise-rapports} : get all the noteEntrepriseRapports.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noteEntrepriseRapports in body.
     */
    @GetMapping("/note-entreprise-rapports")
    public ResponseEntity<List<NoteEntrepriseRapportDTO>> getAllNoteEntrepriseRapports(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of NoteEntrepriseRapports");
        Page<NoteEntrepriseRapportDTO> page;
        if (eagerload) {
            page = noteEntrepriseRapportService.findAllWithEagerRelationships(pageable);
        } else {
            page = noteEntrepriseRapportService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /note-entreprise-rapports/:id} : get the "id" noteEntrepriseRapport.
     *
     * @param id the id of the noteEntrepriseRapportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noteEntrepriseRapportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/note-entreprise-rapports/{id}")
    public ResponseEntity<NoteEntrepriseRapportDTO> getNoteEntrepriseRapport(@PathVariable Long id) {
        log.debug("REST request to get NoteEntrepriseRapport : {}", id);
        Optional<NoteEntrepriseRapportDTO> noteEntrepriseRapportDTO = noteEntrepriseRapportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noteEntrepriseRapportDTO);
    }

    /**
     * {@code DELETE  /note-entreprise-rapports/:id} : delete the "id" noteEntrepriseRapport.
     *
     * @param id the id of the noteEntrepriseRapportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/note-entreprise-rapports/{id}")
    public ResponseEntity<Void> deleteNoteEntrepriseRapport(@PathVariable Long id) {
        log.debug("REST request to delete NoteEntrepriseRapport : {}", id);
        noteEntrepriseRapportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
