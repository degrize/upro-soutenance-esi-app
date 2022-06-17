package com.esi.uppro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esi.uppro.IntegrationTest;
import com.esi.uppro.domain.NoteEntrepriseRapport;
import com.esi.uppro.repository.NoteEntrepriseRapportRepository;
import com.esi.uppro.service.NoteEntrepriseRapportService;
import com.esi.uppro.service.dto.NoteEntrepriseRapportDTO;
import com.esi.uppro.service.mapper.NoteEntrepriseRapportMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NoteEntrepriseRapportResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NoteEntrepriseRapportResourceIT {

    private static final Double DEFAULT_NOTE = 1D;
    private static final Double UPDATED_NOTE = 2D;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_AJOUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AJOUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/note-entreprise-rapports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NoteEntrepriseRapportRepository noteEntrepriseRapportRepository;

    @Mock
    private NoteEntrepriseRapportRepository noteEntrepriseRapportRepositoryMock;

    @Autowired
    private NoteEntrepriseRapportMapper noteEntrepriseRapportMapper;

    @Mock
    private NoteEntrepriseRapportService noteEntrepriseRapportServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoteEntrepriseRapportMockMvc;

    private NoteEntrepriseRapport noteEntrepriseRapport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoteEntrepriseRapport createEntity(EntityManager em) {
        NoteEntrepriseRapport noteEntrepriseRapport = new NoteEntrepriseRapport()
            .note(DEFAULT_NOTE)
            .observation(DEFAULT_OBSERVATION)
            .dateAjout(DEFAULT_DATE_AJOUT)
            .dateModification(DEFAULT_DATE_MODIFICATION);
        return noteEntrepriseRapport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoteEntrepriseRapport createUpdatedEntity(EntityManager em) {
        NoteEntrepriseRapport noteEntrepriseRapport = new NoteEntrepriseRapport()
            .note(UPDATED_NOTE)
            .observation(UPDATED_OBSERVATION)
            .dateAjout(UPDATED_DATE_AJOUT)
            .dateModification(UPDATED_DATE_MODIFICATION);
        return noteEntrepriseRapport;
    }

    @BeforeEach
    public void initTest() {
        noteEntrepriseRapport = createEntity(em);
    }

    @Test
    @Transactional
    void createNoteEntrepriseRapport() throws Exception {
        int databaseSizeBeforeCreate = noteEntrepriseRapportRepository.findAll().size();
        // Create the NoteEntrepriseRapport
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);
        restNoteEntrepriseRapportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeCreate + 1);
        NoteEntrepriseRapport testNoteEntrepriseRapport = noteEntrepriseRapportList.get(noteEntrepriseRapportList.size() - 1);
        assertThat(testNoteEntrepriseRapport.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testNoteEntrepriseRapport.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testNoteEntrepriseRapport.getDateAjout()).isEqualTo(DEFAULT_DATE_AJOUT);
        assertThat(testNoteEntrepriseRapport.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    void createNoteEntrepriseRapportWithExistingId() throws Exception {
        // Create the NoteEntrepriseRapport with an existing ID
        noteEntrepriseRapport.setId(1L);
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);

        int databaseSizeBeforeCreate = noteEntrepriseRapportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoteEntrepriseRapportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = noteEntrepriseRapportRepository.findAll().size();
        // set the field null
        noteEntrepriseRapport.setNote(null);

        // Create the NoteEntrepriseRapport, which fails.
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);

        restNoteEntrepriseRapportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isBadRequest());

        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNoteEntrepriseRapports() throws Exception {
        // Initialize the database
        noteEntrepriseRapportRepository.saveAndFlush(noteEntrepriseRapport);

        // Get all the noteEntrepriseRapportList
        restNoteEntrepriseRapportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noteEntrepriseRapport.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.doubleValue())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].dateAjout").value(hasItem(DEFAULT_DATE_AJOUT.toString())))
            .andExpect(jsonPath("$.[*].dateModification").value(hasItem(DEFAULT_DATE_MODIFICATION.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNoteEntrepriseRapportsWithEagerRelationshipsIsEnabled() throws Exception {
        when(noteEntrepriseRapportServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNoteEntrepriseRapportMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(noteEntrepriseRapportServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNoteEntrepriseRapportsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(noteEntrepriseRapportServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNoteEntrepriseRapportMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(noteEntrepriseRapportServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getNoteEntrepriseRapport() throws Exception {
        // Initialize the database
        noteEntrepriseRapportRepository.saveAndFlush(noteEntrepriseRapport);

        // Get the noteEntrepriseRapport
        restNoteEntrepriseRapportMockMvc
            .perform(get(ENTITY_API_URL_ID, noteEntrepriseRapport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noteEntrepriseRapport.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.doubleValue()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.dateAjout").value(DEFAULT_DATE_AJOUT.toString()))
            .andExpect(jsonPath("$.dateModification").value(DEFAULT_DATE_MODIFICATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNoteEntrepriseRapport() throws Exception {
        // Get the noteEntrepriseRapport
        restNoteEntrepriseRapportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNoteEntrepriseRapport() throws Exception {
        // Initialize the database
        noteEntrepriseRapportRepository.saveAndFlush(noteEntrepriseRapport);

        int databaseSizeBeforeUpdate = noteEntrepriseRapportRepository.findAll().size();

        // Update the noteEntrepriseRapport
        NoteEntrepriseRapport updatedNoteEntrepriseRapport = noteEntrepriseRapportRepository.findById(noteEntrepriseRapport.getId()).get();
        // Disconnect from session so that the updates on updatedNoteEntrepriseRapport are not directly saved in db
        em.detach(updatedNoteEntrepriseRapport);
        updatedNoteEntrepriseRapport
            .note(UPDATED_NOTE)
            .observation(UPDATED_OBSERVATION)
            .dateAjout(UPDATED_DATE_AJOUT)
            .dateModification(UPDATED_DATE_MODIFICATION);
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(updatedNoteEntrepriseRapport);

        restNoteEntrepriseRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noteEntrepriseRapportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isOk());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeUpdate);
        NoteEntrepriseRapport testNoteEntrepriseRapport = noteEntrepriseRapportList.get(noteEntrepriseRapportList.size() - 1);
        assertThat(testNoteEntrepriseRapport.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testNoteEntrepriseRapport.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testNoteEntrepriseRapport.getDateAjout()).isEqualTo(UPDATED_DATE_AJOUT);
        assertThat(testNoteEntrepriseRapport.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    void putNonExistingNoteEntrepriseRapport() throws Exception {
        int databaseSizeBeforeUpdate = noteEntrepriseRapportRepository.findAll().size();
        noteEntrepriseRapport.setId(count.incrementAndGet());

        // Create the NoteEntrepriseRapport
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoteEntrepriseRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, noteEntrepriseRapportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNoteEntrepriseRapport() throws Exception {
        int databaseSizeBeforeUpdate = noteEntrepriseRapportRepository.findAll().size();
        noteEntrepriseRapport.setId(count.incrementAndGet());

        // Create the NoteEntrepriseRapport
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteEntrepriseRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNoteEntrepriseRapport() throws Exception {
        int databaseSizeBeforeUpdate = noteEntrepriseRapportRepository.findAll().size();
        noteEntrepriseRapport.setId(count.incrementAndGet());

        // Create the NoteEntrepriseRapport
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteEntrepriseRapportMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNoteEntrepriseRapportWithPatch() throws Exception {
        // Initialize the database
        noteEntrepriseRapportRepository.saveAndFlush(noteEntrepriseRapport);

        int databaseSizeBeforeUpdate = noteEntrepriseRapportRepository.findAll().size();

        // Update the noteEntrepriseRapport using partial update
        NoteEntrepriseRapport partialUpdatedNoteEntrepriseRapport = new NoteEntrepriseRapport();
        partialUpdatedNoteEntrepriseRapport.setId(noteEntrepriseRapport.getId());

        partialUpdatedNoteEntrepriseRapport.observation(UPDATED_OBSERVATION).dateAjout(UPDATED_DATE_AJOUT);

        restNoteEntrepriseRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoteEntrepriseRapport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoteEntrepriseRapport))
            )
            .andExpect(status().isOk());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeUpdate);
        NoteEntrepriseRapport testNoteEntrepriseRapport = noteEntrepriseRapportList.get(noteEntrepriseRapportList.size() - 1);
        assertThat(testNoteEntrepriseRapport.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testNoteEntrepriseRapport.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testNoteEntrepriseRapport.getDateAjout()).isEqualTo(UPDATED_DATE_AJOUT);
        assertThat(testNoteEntrepriseRapport.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    void fullUpdateNoteEntrepriseRapportWithPatch() throws Exception {
        // Initialize the database
        noteEntrepriseRapportRepository.saveAndFlush(noteEntrepriseRapport);

        int databaseSizeBeforeUpdate = noteEntrepriseRapportRepository.findAll().size();

        // Update the noteEntrepriseRapport using partial update
        NoteEntrepriseRapport partialUpdatedNoteEntrepriseRapport = new NoteEntrepriseRapport();
        partialUpdatedNoteEntrepriseRapport.setId(noteEntrepriseRapport.getId());

        partialUpdatedNoteEntrepriseRapport
            .note(UPDATED_NOTE)
            .observation(UPDATED_OBSERVATION)
            .dateAjout(UPDATED_DATE_AJOUT)
            .dateModification(UPDATED_DATE_MODIFICATION);

        restNoteEntrepriseRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNoteEntrepriseRapport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNoteEntrepriseRapport))
            )
            .andExpect(status().isOk());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeUpdate);
        NoteEntrepriseRapport testNoteEntrepriseRapport = noteEntrepriseRapportList.get(noteEntrepriseRapportList.size() - 1);
        assertThat(testNoteEntrepriseRapport.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testNoteEntrepriseRapport.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testNoteEntrepriseRapport.getDateAjout()).isEqualTo(UPDATED_DATE_AJOUT);
        assertThat(testNoteEntrepriseRapport.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    void patchNonExistingNoteEntrepriseRapport() throws Exception {
        int databaseSizeBeforeUpdate = noteEntrepriseRapportRepository.findAll().size();
        noteEntrepriseRapport.setId(count.incrementAndGet());

        // Create the NoteEntrepriseRapport
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoteEntrepriseRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, noteEntrepriseRapportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNoteEntrepriseRapport() throws Exception {
        int databaseSizeBeforeUpdate = noteEntrepriseRapportRepository.findAll().size();
        noteEntrepriseRapport.setId(count.incrementAndGet());

        // Create the NoteEntrepriseRapport
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteEntrepriseRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNoteEntrepriseRapport() throws Exception {
        int databaseSizeBeforeUpdate = noteEntrepriseRapportRepository.findAll().size();
        noteEntrepriseRapport.setId(count.incrementAndGet());

        // Create the NoteEntrepriseRapport
        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNoteEntrepriseRapportMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(noteEntrepriseRapportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NoteEntrepriseRapport in the database
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNoteEntrepriseRapport() throws Exception {
        // Initialize the database
        noteEntrepriseRapportRepository.saveAndFlush(noteEntrepriseRapport);

        int databaseSizeBeforeDelete = noteEntrepriseRapportRepository.findAll().size();

        // Delete the noteEntrepriseRapport
        restNoteEntrepriseRapportMockMvc
            .perform(delete(ENTITY_API_URL_ID, noteEntrepriseRapport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NoteEntrepriseRapport> noteEntrepriseRapportList = noteEntrepriseRapportRepository.findAll();
        assertThat(noteEntrepriseRapportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
