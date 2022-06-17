package com.esi.uppro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esi.uppro.IntegrationTest;
import com.esi.uppro.domain.Soutenance;
import com.esi.uppro.domain.enumeration.Mention;
import com.esi.uppro.repository.SoutenanceRepository;
import com.esi.uppro.service.SoutenanceService;
import com.esi.uppro.service.dto.SoutenanceDTO;
import com.esi.uppro.service.mapper.SoutenanceMapper;
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
 * Integration tests for the {@link SoutenanceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SoutenanceResourceIT {

    private static final Mention DEFAULT_MENTION = Mention.PASSABLE;
    private static final Mention UPDATED_MENTION = Mention.ASSEZ_BIEN;

    private static final Double DEFAULT_NOTE = 1D;
    private static final Double UPDATED_NOTE = 2D;

    private static final LocalDate DEFAULT_DATE_DU_JOUR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DU_JOUR = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REMARQUE = "AAAAAAAAAA";
    private static final String UPDATED_REMARQUE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_AJOUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AJOUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_MODIFICATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_MODIFICATION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/soutenances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SoutenanceRepository soutenanceRepository;

    @Mock
    private SoutenanceRepository soutenanceRepositoryMock;

    @Autowired
    private SoutenanceMapper soutenanceMapper;

    @Mock
    private SoutenanceService soutenanceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoutenanceMockMvc;

    private Soutenance soutenance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soutenance createEntity(EntityManager em) {
        Soutenance soutenance = new Soutenance()
            .mention(DEFAULT_MENTION)
            .note(DEFAULT_NOTE)
            .dateDuJour(DEFAULT_DATE_DU_JOUR)
            .remarque(DEFAULT_REMARQUE)
            .dateAjout(DEFAULT_DATE_AJOUT)
            .dateModification(DEFAULT_DATE_MODIFICATION);
        return soutenance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soutenance createUpdatedEntity(EntityManager em) {
        Soutenance soutenance = new Soutenance()
            .mention(UPDATED_MENTION)
            .note(UPDATED_NOTE)
            .dateDuJour(UPDATED_DATE_DU_JOUR)
            .remarque(UPDATED_REMARQUE)
            .dateAjout(UPDATED_DATE_AJOUT)
            .dateModification(UPDATED_DATE_MODIFICATION);
        return soutenance;
    }

    @BeforeEach
    public void initTest() {
        soutenance = createEntity(em);
    }

    @Test
    @Transactional
    void createSoutenance() throws Exception {
        int databaseSizeBeforeCreate = soutenanceRepository.findAll().size();
        // Create the Soutenance
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);
        restSoutenanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soutenanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeCreate + 1);
        Soutenance testSoutenance = soutenanceList.get(soutenanceList.size() - 1);
        assertThat(testSoutenance.getMention()).isEqualTo(DEFAULT_MENTION);
        assertThat(testSoutenance.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testSoutenance.getDateDuJour()).isEqualTo(DEFAULT_DATE_DU_JOUR);
        assertThat(testSoutenance.getRemarque()).isEqualTo(DEFAULT_REMARQUE);
        assertThat(testSoutenance.getDateAjout()).isEqualTo(DEFAULT_DATE_AJOUT);
        assertThat(testSoutenance.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    void createSoutenanceWithExistingId() throws Exception {
        // Create the Soutenance with an existing ID
        soutenance.setId(1L);
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        int databaseSizeBeforeCreate = soutenanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoutenanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soutenanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMentionIsRequired() throws Exception {
        int databaseSizeBeforeTest = soutenanceRepository.findAll().size();
        // set the field null
        soutenance.setMention(null);

        // Create the Soutenance, which fails.
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        restSoutenanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soutenanceDTO)))
            .andExpect(status().isBadRequest());

        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = soutenanceRepository.findAll().size();
        // set the field null
        soutenance.setNote(null);

        // Create the Soutenance, which fails.
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        restSoutenanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soutenanceDTO)))
            .andExpect(status().isBadRequest());

        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateDuJourIsRequired() throws Exception {
        int databaseSizeBeforeTest = soutenanceRepository.findAll().size();
        // set the field null
        soutenance.setDateDuJour(null);

        // Create the Soutenance, which fails.
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        restSoutenanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soutenanceDTO)))
            .andExpect(status().isBadRequest());

        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSoutenances() throws Exception {
        // Initialize the database
        soutenanceRepository.saveAndFlush(soutenance);

        // Get all the soutenanceList
        restSoutenanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soutenance.getId().intValue())))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateDuJour").value(hasItem(DEFAULT_DATE_DU_JOUR.toString())))
            .andExpect(jsonPath("$.[*].remarque").value(hasItem(DEFAULT_REMARQUE)))
            .andExpect(jsonPath("$.[*].dateAjout").value(hasItem(DEFAULT_DATE_AJOUT.toString())))
            .andExpect(jsonPath("$.[*].dateModification").value(hasItem(DEFAULT_DATE_MODIFICATION.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSoutenancesWithEagerRelationshipsIsEnabled() throws Exception {
        when(soutenanceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSoutenanceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(soutenanceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSoutenancesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(soutenanceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSoutenanceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(soutenanceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSoutenance() throws Exception {
        // Initialize the database
        soutenanceRepository.saveAndFlush(soutenance);

        // Get the soutenance
        restSoutenanceMockMvc
            .perform(get(ENTITY_API_URL_ID, soutenance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soutenance.getId().intValue()))
            .andExpect(jsonPath("$.mention").value(DEFAULT_MENTION.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.doubleValue()))
            .andExpect(jsonPath("$.dateDuJour").value(DEFAULT_DATE_DU_JOUR.toString()))
            .andExpect(jsonPath("$.remarque").value(DEFAULT_REMARQUE))
            .andExpect(jsonPath("$.dateAjout").value(DEFAULT_DATE_AJOUT.toString()))
            .andExpect(jsonPath("$.dateModification").value(DEFAULT_DATE_MODIFICATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSoutenance() throws Exception {
        // Get the soutenance
        restSoutenanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSoutenance() throws Exception {
        // Initialize the database
        soutenanceRepository.saveAndFlush(soutenance);

        int databaseSizeBeforeUpdate = soutenanceRepository.findAll().size();

        // Update the soutenance
        Soutenance updatedSoutenance = soutenanceRepository.findById(soutenance.getId()).get();
        // Disconnect from session so that the updates on updatedSoutenance are not directly saved in db
        em.detach(updatedSoutenance);
        updatedSoutenance
            .mention(UPDATED_MENTION)
            .note(UPDATED_NOTE)
            .dateDuJour(UPDATED_DATE_DU_JOUR)
            .remarque(UPDATED_REMARQUE)
            .dateAjout(UPDATED_DATE_AJOUT)
            .dateModification(UPDATED_DATE_MODIFICATION);
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(updatedSoutenance);

        restSoutenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soutenanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soutenanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeUpdate);
        Soutenance testSoutenance = soutenanceList.get(soutenanceList.size() - 1);
        assertThat(testSoutenance.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testSoutenance.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testSoutenance.getDateDuJour()).isEqualTo(UPDATED_DATE_DU_JOUR);
        assertThat(testSoutenance.getRemarque()).isEqualTo(UPDATED_REMARQUE);
        assertThat(testSoutenance.getDateAjout()).isEqualTo(UPDATED_DATE_AJOUT);
        assertThat(testSoutenance.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    void putNonExistingSoutenance() throws Exception {
        int databaseSizeBeforeUpdate = soutenanceRepository.findAll().size();
        soutenance.setId(count.incrementAndGet());

        // Create the Soutenance
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoutenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soutenanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soutenanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoutenance() throws Exception {
        int databaseSizeBeforeUpdate = soutenanceRepository.findAll().size();
        soutenance.setId(count.incrementAndGet());

        // Create the Soutenance
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoutenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soutenanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoutenance() throws Exception {
        int databaseSizeBeforeUpdate = soutenanceRepository.findAll().size();
        soutenance.setId(count.incrementAndGet());

        // Create the Soutenance
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoutenanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soutenanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoutenanceWithPatch() throws Exception {
        // Initialize the database
        soutenanceRepository.saveAndFlush(soutenance);

        int databaseSizeBeforeUpdate = soutenanceRepository.findAll().size();

        // Update the soutenance using partial update
        Soutenance partialUpdatedSoutenance = new Soutenance();
        partialUpdatedSoutenance.setId(soutenance.getId());

        partialUpdatedSoutenance.mention(UPDATED_MENTION).dateDuJour(UPDATED_DATE_DU_JOUR);

        restSoutenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoutenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoutenance))
            )
            .andExpect(status().isOk());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeUpdate);
        Soutenance testSoutenance = soutenanceList.get(soutenanceList.size() - 1);
        assertThat(testSoutenance.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testSoutenance.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testSoutenance.getDateDuJour()).isEqualTo(UPDATED_DATE_DU_JOUR);
        assertThat(testSoutenance.getRemarque()).isEqualTo(DEFAULT_REMARQUE);
        assertThat(testSoutenance.getDateAjout()).isEqualTo(DEFAULT_DATE_AJOUT);
        assertThat(testSoutenance.getDateModification()).isEqualTo(DEFAULT_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    void fullUpdateSoutenanceWithPatch() throws Exception {
        // Initialize the database
        soutenanceRepository.saveAndFlush(soutenance);

        int databaseSizeBeforeUpdate = soutenanceRepository.findAll().size();

        // Update the soutenance using partial update
        Soutenance partialUpdatedSoutenance = new Soutenance();
        partialUpdatedSoutenance.setId(soutenance.getId());

        partialUpdatedSoutenance
            .mention(UPDATED_MENTION)
            .note(UPDATED_NOTE)
            .dateDuJour(UPDATED_DATE_DU_JOUR)
            .remarque(UPDATED_REMARQUE)
            .dateAjout(UPDATED_DATE_AJOUT)
            .dateModification(UPDATED_DATE_MODIFICATION);

        restSoutenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoutenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoutenance))
            )
            .andExpect(status().isOk());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeUpdate);
        Soutenance testSoutenance = soutenanceList.get(soutenanceList.size() - 1);
        assertThat(testSoutenance.getMention()).isEqualTo(UPDATED_MENTION);
        assertThat(testSoutenance.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testSoutenance.getDateDuJour()).isEqualTo(UPDATED_DATE_DU_JOUR);
        assertThat(testSoutenance.getRemarque()).isEqualTo(UPDATED_REMARQUE);
        assertThat(testSoutenance.getDateAjout()).isEqualTo(UPDATED_DATE_AJOUT);
        assertThat(testSoutenance.getDateModification()).isEqualTo(UPDATED_DATE_MODIFICATION);
    }

    @Test
    @Transactional
    void patchNonExistingSoutenance() throws Exception {
        int databaseSizeBeforeUpdate = soutenanceRepository.findAll().size();
        soutenance.setId(count.incrementAndGet());

        // Create the Soutenance
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoutenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soutenanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soutenanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoutenance() throws Exception {
        int databaseSizeBeforeUpdate = soutenanceRepository.findAll().size();
        soutenance.setId(count.incrementAndGet());

        // Create the Soutenance
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoutenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soutenanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoutenance() throws Exception {
        int databaseSizeBeforeUpdate = soutenanceRepository.findAll().size();
        soutenance.setId(count.incrementAndGet());

        // Create the Soutenance
        SoutenanceDTO soutenanceDTO = soutenanceMapper.toDto(soutenance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoutenanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(soutenanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soutenance in the database
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoutenance() throws Exception {
        // Initialize the database
        soutenanceRepository.saveAndFlush(soutenance);

        int databaseSizeBeforeDelete = soutenanceRepository.findAll().size();

        // Delete the soutenance
        restSoutenanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, soutenance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Soutenance> soutenanceList = soutenanceRepository.findAll();
        assertThat(soutenanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
