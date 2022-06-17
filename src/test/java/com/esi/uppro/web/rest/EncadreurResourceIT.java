package com.esi.uppro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esi.uppro.IntegrationTest;
import com.esi.uppro.domain.Encadreur;
import com.esi.uppro.repository.EncadreurRepository;
import com.esi.uppro.service.dto.EncadreurDTO;
import com.esi.uppro.service.mapper.EncadreurMapper;
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
 * Integration tests for the {@link EncadreurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EncadreurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOMS = "AAAAAAAAAA";
    private static final String UPDATED_PRENOMS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/encadreurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EncadreurRepository encadreurRepository;

    @Autowired
    private EncadreurMapper encadreurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEncadreurMockMvc;

    private Encadreur encadreur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encadreur createEntity(EntityManager em) {
        Encadreur encadreur = new Encadreur().nom(DEFAULT_NOM).prenoms(DEFAULT_PRENOMS).contact(DEFAULT_CONTACT).email(DEFAULT_EMAIL);
        return encadreur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encadreur createUpdatedEntity(EntityManager em) {
        Encadreur encadreur = new Encadreur().nom(UPDATED_NOM).prenoms(UPDATED_PRENOMS).contact(UPDATED_CONTACT).email(UPDATED_EMAIL);
        return encadreur;
    }

    @BeforeEach
    public void initTest() {
        encadreur = createEntity(em);
    }

    @Test
    @Transactional
    void createEncadreur() throws Exception {
        int databaseSizeBeforeCreate = encadreurRepository.findAll().size();
        // Create the Encadreur
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);
        restEncadreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encadreurDTO)))
            .andExpect(status().isCreated());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeCreate + 1);
        Encadreur testEncadreur = encadreurList.get(encadreurList.size() - 1);
        assertThat(testEncadreur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEncadreur.getPrenoms()).isEqualTo(DEFAULT_PRENOMS);
        assertThat(testEncadreur.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testEncadreur.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void createEncadreurWithExistingId() throws Exception {
        // Create the Encadreur with an existing ID
        encadreur.setId(1L);
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        int databaseSizeBeforeCreate = encadreurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncadreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encadreurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = encadreurRepository.findAll().size();
        // set the field null
        encadreur.setNom(null);

        // Create the Encadreur, which fails.
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        restEncadreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encadreurDTO)))
            .andExpect(status().isBadRequest());

        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomsIsRequired() throws Exception {
        int databaseSizeBeforeTest = encadreurRepository.findAll().size();
        // set the field null
        encadreur.setPrenoms(null);

        // Create the Encadreur, which fails.
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        restEncadreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encadreurDTO)))
            .andExpect(status().isBadRequest());

        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = encadreurRepository.findAll().size();
        // set the field null
        encadreur.setContact(null);

        // Create the Encadreur, which fails.
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        restEncadreurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encadreurDTO)))
            .andExpect(status().isBadRequest());

        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEncadreurs() throws Exception {
        // Initialize the database
        encadreurRepository.saveAndFlush(encadreur);

        // Get all the encadreurList
        restEncadreurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encadreur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getEncadreur() throws Exception {
        // Initialize the database
        encadreurRepository.saveAndFlush(encadreur);

        // Get the encadreur
        restEncadreurMockMvc
            .perform(get(ENTITY_API_URL_ID, encadreur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(encadreur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenoms").value(DEFAULT_PRENOMS))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingEncadreur() throws Exception {
        // Get the encadreur
        restEncadreurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEncadreur() throws Exception {
        // Initialize the database
        encadreurRepository.saveAndFlush(encadreur);

        int databaseSizeBeforeUpdate = encadreurRepository.findAll().size();

        // Update the encadreur
        Encadreur updatedEncadreur = encadreurRepository.findById(encadreur.getId()).get();
        // Disconnect from session so that the updates on updatedEncadreur are not directly saved in db
        em.detach(updatedEncadreur);
        updatedEncadreur.nom(UPDATED_NOM).prenoms(UPDATED_PRENOMS).contact(UPDATED_CONTACT).email(UPDATED_EMAIL);
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(updatedEncadreur);

        restEncadreurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, encadreurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encadreurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeUpdate);
        Encadreur testEncadreur = encadreurList.get(encadreurList.size() - 1);
        assertThat(testEncadreur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEncadreur.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testEncadreur.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testEncadreur.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingEncadreur() throws Exception {
        int databaseSizeBeforeUpdate = encadreurRepository.findAll().size();
        encadreur.setId(count.incrementAndGet());

        // Create the Encadreur
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncadreurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, encadreurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encadreurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEncadreur() throws Exception {
        int databaseSizeBeforeUpdate = encadreurRepository.findAll().size();
        encadreur.setId(count.incrementAndGet());

        // Create the Encadreur
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncadreurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encadreurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEncadreur() throws Exception {
        int databaseSizeBeforeUpdate = encadreurRepository.findAll().size();
        encadreur.setId(count.incrementAndGet());

        // Create the Encadreur
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncadreurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encadreurDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEncadreurWithPatch() throws Exception {
        // Initialize the database
        encadreurRepository.saveAndFlush(encadreur);

        int databaseSizeBeforeUpdate = encadreurRepository.findAll().size();

        // Update the encadreur using partial update
        Encadreur partialUpdatedEncadreur = new Encadreur();
        partialUpdatedEncadreur.setId(encadreur.getId());

        partialUpdatedEncadreur.nom(UPDATED_NOM);

        restEncadreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncadreur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEncadreur))
            )
            .andExpect(status().isOk());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeUpdate);
        Encadreur testEncadreur = encadreurList.get(encadreurList.size() - 1);
        assertThat(testEncadreur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEncadreur.getPrenoms()).isEqualTo(DEFAULT_PRENOMS);
        assertThat(testEncadreur.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testEncadreur.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateEncadreurWithPatch() throws Exception {
        // Initialize the database
        encadreurRepository.saveAndFlush(encadreur);

        int databaseSizeBeforeUpdate = encadreurRepository.findAll().size();

        // Update the encadreur using partial update
        Encadreur partialUpdatedEncadreur = new Encadreur();
        partialUpdatedEncadreur.setId(encadreur.getId());

        partialUpdatedEncadreur.nom(UPDATED_NOM).prenoms(UPDATED_PRENOMS).contact(UPDATED_CONTACT).email(UPDATED_EMAIL);

        restEncadreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncadreur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEncadreur))
            )
            .andExpect(status().isOk());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeUpdate);
        Encadreur testEncadreur = encadreurList.get(encadreurList.size() - 1);
        assertThat(testEncadreur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEncadreur.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testEncadreur.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testEncadreur.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingEncadreur() throws Exception {
        int databaseSizeBeforeUpdate = encadreurRepository.findAll().size();
        encadreur.setId(count.incrementAndGet());

        // Create the Encadreur
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncadreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, encadreurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(encadreurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEncadreur() throws Exception {
        int databaseSizeBeforeUpdate = encadreurRepository.findAll().size();
        encadreur.setId(count.incrementAndGet());

        // Create the Encadreur
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncadreurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(encadreurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEncadreur() throws Exception {
        int databaseSizeBeforeUpdate = encadreurRepository.findAll().size();
        encadreur.setId(count.incrementAndGet());

        // Create the Encadreur
        EncadreurDTO encadreurDTO = encadreurMapper.toDto(encadreur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncadreurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(encadreurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Encadreur in the database
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEncadreur() throws Exception {
        // Initialize the database
        encadreurRepository.saveAndFlush(encadreur);

        int databaseSizeBeforeDelete = encadreurRepository.findAll().size();

        // Delete the encadreur
        restEncadreurMockMvc
            .perform(delete(ENTITY_API_URL_ID, encadreur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Encadreur> encadreurList = encadreurRepository.findAll();
        assertThat(encadreurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
