package com.esi.uppro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esi.uppro.IntegrationTest;
import com.esi.uppro.domain.Jury;
import com.esi.uppro.repository.JuryRepository;
import com.esi.uppro.service.JuryService;
import com.esi.uppro.service.dto.JuryDTO;
import com.esi.uppro.service.mapper.JuryMapper;
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
 * Integration tests for the {@link JuryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class JuryResourceIT {

    private static final String DEFAULT_NOM_PRESIDENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PRESIDENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_RAPPORTEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_RAPPORTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PROF_ANGLAIS = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PROF_ANGLAIS = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_SALLE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_SALLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/juries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JuryRepository juryRepository;

    @Mock
    private JuryRepository juryRepositoryMock;

    @Autowired
    private JuryMapper juryMapper;

    @Mock
    private JuryService juryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJuryMockMvc;

    private Jury jury;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jury createEntity(EntityManager em) {
        Jury jury = new Jury()
            .nomPresident(DEFAULT_NOM_PRESIDENT)
            .nomRapporteur(DEFAULT_NOM_RAPPORTEUR)
            .nomProfAnglais(DEFAULT_NOM_PROF_ANGLAIS)
            .numeroSalle(DEFAULT_NUMERO_SALLE);
        return jury;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jury createUpdatedEntity(EntityManager em) {
        Jury jury = new Jury()
            .nomPresident(UPDATED_NOM_PRESIDENT)
            .nomRapporteur(UPDATED_NOM_RAPPORTEUR)
            .nomProfAnglais(UPDATED_NOM_PROF_ANGLAIS)
            .numeroSalle(UPDATED_NUMERO_SALLE);
        return jury;
    }

    @BeforeEach
    public void initTest() {
        jury = createEntity(em);
    }

    @Test
    @Transactional
    void createJury() throws Exception {
        int databaseSizeBeforeCreate = juryRepository.findAll().size();
        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);
        restJuryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isCreated());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeCreate + 1);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getNomPresident()).isEqualTo(DEFAULT_NOM_PRESIDENT);
        assertThat(testJury.getNomRapporteur()).isEqualTo(DEFAULT_NOM_RAPPORTEUR);
        assertThat(testJury.getNomProfAnglais()).isEqualTo(DEFAULT_NOM_PROF_ANGLAIS);
        assertThat(testJury.getNumeroSalle()).isEqualTo(DEFAULT_NUMERO_SALLE);
    }

    @Test
    @Transactional
    void createJuryWithExistingId() throws Exception {
        // Create the Jury with an existing ID
        jury.setId(1L);
        JuryDTO juryDTO = juryMapper.toDto(jury);

        int databaseSizeBeforeCreate = juryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJuryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomPresidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = juryRepository.findAll().size();
        // set the field null
        jury.setNomPresident(null);

        // Create the Jury, which fails.
        JuryDTO juryDTO = juryMapper.toDto(jury);

        restJuryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isBadRequest());

        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomRapporteurIsRequired() throws Exception {
        int databaseSizeBeforeTest = juryRepository.findAll().size();
        // set the field null
        jury.setNomRapporteur(null);

        // Create the Jury, which fails.
        JuryDTO juryDTO = juryMapper.toDto(jury);

        restJuryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isBadRequest());

        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomProfAnglaisIsRequired() throws Exception {
        int databaseSizeBeforeTest = juryRepository.findAll().size();
        // set the field null
        jury.setNomProfAnglais(null);

        // Create the Jury, which fails.
        JuryDTO juryDTO = juryMapper.toDto(jury);

        restJuryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isBadRequest());

        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllJuries() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        // Get all the juryList
        restJuryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jury.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomPresident").value(hasItem(DEFAULT_NOM_PRESIDENT)))
            .andExpect(jsonPath("$.[*].nomRapporteur").value(hasItem(DEFAULT_NOM_RAPPORTEUR)))
            .andExpect(jsonPath("$.[*].nomProfAnglais").value(hasItem(DEFAULT_NOM_PROF_ANGLAIS)))
            .andExpect(jsonPath("$.[*].numeroSalle").value(hasItem(DEFAULT_NUMERO_SALLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllJuriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(juryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJuryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(juryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllJuriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(juryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJuryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(juryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        // Get the jury
        restJuryMockMvc
            .perform(get(ENTITY_API_URL_ID, jury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jury.getId().intValue()))
            .andExpect(jsonPath("$.nomPresident").value(DEFAULT_NOM_PRESIDENT))
            .andExpect(jsonPath("$.nomRapporteur").value(DEFAULT_NOM_RAPPORTEUR))
            .andExpect(jsonPath("$.nomProfAnglais").value(DEFAULT_NOM_PROF_ANGLAIS))
            .andExpect(jsonPath("$.numeroSalle").value(DEFAULT_NUMERO_SALLE));
    }

    @Test
    @Transactional
    void getNonExistingJury() throws Exception {
        // Get the jury
        restJuryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Update the jury
        Jury updatedJury = juryRepository.findById(jury.getId()).get();
        // Disconnect from session so that the updates on updatedJury are not directly saved in db
        em.detach(updatedJury);
        updatedJury
            .nomPresident(UPDATED_NOM_PRESIDENT)
            .nomRapporteur(UPDATED_NOM_RAPPORTEUR)
            .nomProfAnglais(UPDATED_NOM_PROF_ANGLAIS)
            .numeroSalle(UPDATED_NUMERO_SALLE);
        JuryDTO juryDTO = juryMapper.toDto(updatedJury);

        restJuryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, juryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getNomPresident()).isEqualTo(UPDATED_NOM_PRESIDENT);
        assertThat(testJury.getNomRapporteur()).isEqualTo(UPDATED_NOM_RAPPORTEUR);
        assertThat(testJury.getNomProfAnglais()).isEqualTo(UPDATED_NOM_PROF_ANGLAIS);
        assertThat(testJury.getNumeroSalle()).isEqualTo(UPDATED_NUMERO_SALLE);
    }

    @Test
    @Transactional
    void putNonExistingJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, juryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJuryWithPatch() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Update the jury using partial update
        Jury partialUpdatedJury = new Jury();
        partialUpdatedJury.setId(jury.getId());

        restJuryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJury.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJury))
            )
            .andExpect(status().isOk());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getNomPresident()).isEqualTo(DEFAULT_NOM_PRESIDENT);
        assertThat(testJury.getNomRapporteur()).isEqualTo(DEFAULT_NOM_RAPPORTEUR);
        assertThat(testJury.getNomProfAnglais()).isEqualTo(DEFAULT_NOM_PROF_ANGLAIS);
        assertThat(testJury.getNumeroSalle()).isEqualTo(DEFAULT_NUMERO_SALLE);
    }

    @Test
    @Transactional
    void fullUpdateJuryWithPatch() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Update the jury using partial update
        Jury partialUpdatedJury = new Jury();
        partialUpdatedJury.setId(jury.getId());

        partialUpdatedJury
            .nomPresident(UPDATED_NOM_PRESIDENT)
            .nomRapporteur(UPDATED_NOM_RAPPORTEUR)
            .nomProfAnglais(UPDATED_NOM_PROF_ANGLAIS)
            .numeroSalle(UPDATED_NUMERO_SALLE);

        restJuryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJury.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJury))
            )
            .andExpect(status().isOk());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getNomPresident()).isEqualTo(UPDATED_NOM_PRESIDENT);
        assertThat(testJury.getNomRapporteur()).isEqualTo(UPDATED_NOM_RAPPORTEUR);
        assertThat(testJury.getNomProfAnglais()).isEqualTo(UPDATED_NOM_PROF_ANGLAIS);
        assertThat(testJury.getNumeroSalle()).isEqualTo(UPDATED_NUMERO_SALLE);
    }

    @Test
    @Transactional
    void patchNonExistingJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, juryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(juryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();
        jury.setId(count.incrementAndGet());

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJuryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeDelete = juryRepository.findAll().size();

        // Delete the jury
        restJuryMockMvc
            .perform(delete(ENTITY_API_URL_ID, jury.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
