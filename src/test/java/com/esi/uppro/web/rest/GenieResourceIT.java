package com.esi.uppro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esi.uppro.IntegrationTest;
import com.esi.uppro.domain.Genie;
import com.esi.uppro.repository.GenieRepository;
import com.esi.uppro.service.dto.GenieDTO;
import com.esi.uppro.service.mapper.GenieMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GenieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GenieResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_DIRECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DIRECTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_DIRECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_DIRECTEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/genies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GenieRepository genieRepository;

    @Autowired
    private GenieMapper genieMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGenieMockMvc;

    private Genie genie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genie createEntity(EntityManager em) {
        Genie genie = new Genie().nom(DEFAULT_NOM).nomDirecteur(DEFAULT_NOM_DIRECTEUR).contactDirecteur(DEFAULT_CONTACT_DIRECTEUR);
        return genie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genie createUpdatedEntity(EntityManager em) {
        Genie genie = new Genie().nom(UPDATED_NOM).nomDirecteur(UPDATED_NOM_DIRECTEUR).contactDirecteur(UPDATED_CONTACT_DIRECTEUR);
        return genie;
    }

    @BeforeEach
    public void initTest() {
        genie = createEntity(em);
    }

    @Test
    @Transactional
    void createGenie() throws Exception {
        int databaseSizeBeforeCreate = genieRepository.findAll().size();
        // Create the Genie
        GenieDTO genieDTO = genieMapper.toDto(genie);
        restGenieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genieDTO)))
            .andExpect(status().isCreated());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeCreate + 1);
        Genie testGenie = genieList.get(genieList.size() - 1);
        assertThat(testGenie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testGenie.getNomDirecteur()).isEqualTo(DEFAULT_NOM_DIRECTEUR);
        assertThat(testGenie.getContactDirecteur()).isEqualTo(DEFAULT_CONTACT_DIRECTEUR);
    }

    @Test
    @Transactional
    void createGenieWithExistingId() throws Exception {
        // Create the Genie with an existing ID
        genie.setId(1L);
        GenieDTO genieDTO = genieMapper.toDto(genie);

        int databaseSizeBeforeCreate = genieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGenieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = genieRepository.findAll().size();
        // set the field null
        genie.setNom(null);

        // Create the Genie, which fails.
        GenieDTO genieDTO = genieMapper.toDto(genie);

        restGenieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genieDTO)))
            .andExpect(status().isBadRequest());

        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGenies() throws Exception {
        // Initialize the database
        genieRepository.saveAndFlush(genie);

        // Get all the genieList
        restGenieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomDirecteur").value(hasItem(DEFAULT_NOM_DIRECTEUR)))
            .andExpect(jsonPath("$.[*].contactDirecteur").value(hasItem(DEFAULT_CONTACT_DIRECTEUR)));
    }

    @Test
    @Transactional
    void getGenie() throws Exception {
        // Initialize the database
        genieRepository.saveAndFlush(genie);

        // Get the genie
        restGenieMockMvc
            .perform(get(ENTITY_API_URL_ID, genie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(genie.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomDirecteur").value(DEFAULT_NOM_DIRECTEUR))
            .andExpect(jsonPath("$.contactDirecteur").value(DEFAULT_CONTACT_DIRECTEUR));
    }

    @Test
    @Transactional
    void getNonExistingGenie() throws Exception {
        // Get the genie
        restGenieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGenie() throws Exception {
        // Initialize the database
        genieRepository.saveAndFlush(genie);

        int databaseSizeBeforeUpdate = genieRepository.findAll().size();

        // Update the genie
        Genie updatedGenie = genieRepository.findById(genie.getId()).get();
        // Disconnect from session so that the updates on updatedGenie are not directly saved in db
        em.detach(updatedGenie);
        updatedGenie.nom(UPDATED_NOM).nomDirecteur(UPDATED_NOM_DIRECTEUR).contactDirecteur(UPDATED_CONTACT_DIRECTEUR);
        GenieDTO genieDTO = genieMapper.toDto(updatedGenie);

        restGenieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, genieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(genieDTO))
            )
            .andExpect(status().isOk());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeUpdate);
        Genie testGenie = genieList.get(genieList.size() - 1);
        assertThat(testGenie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testGenie.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testGenie.getContactDirecteur()).isEqualTo(UPDATED_CONTACT_DIRECTEUR);
    }

    @Test
    @Transactional
    void putNonExistingGenie() throws Exception {
        int databaseSizeBeforeUpdate = genieRepository.findAll().size();
        genie.setId(count.incrementAndGet());

        // Create the Genie
        GenieDTO genieDTO = genieMapper.toDto(genie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, genieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(genieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGenie() throws Exception {
        int databaseSizeBeforeUpdate = genieRepository.findAll().size();
        genie.setId(count.incrementAndGet());

        // Create the Genie
        GenieDTO genieDTO = genieMapper.toDto(genie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(genieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGenie() throws Exception {
        int databaseSizeBeforeUpdate = genieRepository.findAll().size();
        genie.setId(count.incrementAndGet());

        // Create the Genie
        GenieDTO genieDTO = genieMapper.toDto(genie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(genieDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGenieWithPatch() throws Exception {
        // Initialize the database
        genieRepository.saveAndFlush(genie);

        int databaseSizeBeforeUpdate = genieRepository.findAll().size();

        // Update the genie using partial update
        Genie partialUpdatedGenie = new Genie();
        partialUpdatedGenie.setId(genie.getId());

        restGenieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGenie))
            )
            .andExpect(status().isOk());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeUpdate);
        Genie testGenie = genieList.get(genieList.size() - 1);
        assertThat(testGenie.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testGenie.getNomDirecteur()).isEqualTo(DEFAULT_NOM_DIRECTEUR);
        assertThat(testGenie.getContactDirecteur()).isEqualTo(DEFAULT_CONTACT_DIRECTEUR);
    }

    @Test
    @Transactional
    void fullUpdateGenieWithPatch() throws Exception {
        // Initialize the database
        genieRepository.saveAndFlush(genie);

        int databaseSizeBeforeUpdate = genieRepository.findAll().size();

        // Update the genie using partial update
        Genie partialUpdatedGenie = new Genie();
        partialUpdatedGenie.setId(genie.getId());

        partialUpdatedGenie.nom(UPDATED_NOM).nomDirecteur(UPDATED_NOM_DIRECTEUR).contactDirecteur(UPDATED_CONTACT_DIRECTEUR);

        restGenieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGenie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGenie))
            )
            .andExpect(status().isOk());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeUpdate);
        Genie testGenie = genieList.get(genieList.size() - 1);
        assertThat(testGenie.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testGenie.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testGenie.getContactDirecteur()).isEqualTo(UPDATED_CONTACT_DIRECTEUR);
    }

    @Test
    @Transactional
    void patchNonExistingGenie() throws Exception {
        int databaseSizeBeforeUpdate = genieRepository.findAll().size();
        genie.setId(count.incrementAndGet());

        // Create the Genie
        GenieDTO genieDTO = genieMapper.toDto(genie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGenieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, genieDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(genieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGenie() throws Exception {
        int databaseSizeBeforeUpdate = genieRepository.findAll().size();
        genie.setId(count.incrementAndGet());

        // Create the Genie
        GenieDTO genieDTO = genieMapper.toDto(genie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(genieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGenie() throws Exception {
        int databaseSizeBeforeUpdate = genieRepository.findAll().size();
        genie.setId(count.incrementAndGet());

        // Create the Genie
        GenieDTO genieDTO = genieMapper.toDto(genie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGenieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(genieDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Genie in the database
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGenie() throws Exception {
        // Initialize the database
        genieRepository.saveAndFlush(genie);

        int databaseSizeBeforeDelete = genieRepository.findAll().size();

        // Delete the genie
        restGenieMockMvc
            .perform(delete(ENTITY_API_URL_ID, genie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Genie> genieList = genieRepository.findAll();
        assertThat(genieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
