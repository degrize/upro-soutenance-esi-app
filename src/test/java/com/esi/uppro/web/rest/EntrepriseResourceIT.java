package com.esi.uppro.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.esi.uppro.IntegrationTest;
import com.esi.uppro.domain.Entreprise;
import com.esi.uppro.repository.EntrepriseRepository;
import com.esi.uppro.service.dto.EntrepriseDTO;
import com.esi.uppro.service.mapper.EntrepriseMapper;
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
 * Integration tests for the {@link EntrepriseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntrepriseResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_E_NTREPRISE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_E_NTREPRISE = "BBBBBBBBBB";

    private static final String DEFAULT_SECTEUR_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_SECTEUR_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_DIRECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DIRECTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_DIRECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_DIRECTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_DIRECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_DIRECTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MAITRE_STAGE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MAITRE_STAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_MAITRE_STAGE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_MAITRE_STAGE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_MAITRE_STAGE = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_MAITRE_STAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/entreprises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private EntrepriseMapper entrepriseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntrepriseMockMvc;

    private Entreprise entreprise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .nom(DEFAULT_NOM)
            .codeENtreprise(DEFAULT_CODE_E_NTREPRISE)
            .secteurActivite(DEFAULT_SECTEUR_ACTIVITE)
            .adresse(DEFAULT_ADRESSE)
            .nomDirecteur(DEFAULT_NOM_DIRECTEUR)
            .contactDirecteur(DEFAULT_CONTACT_DIRECTEUR)
            .emailDirecteur(DEFAULT_EMAIL_DIRECTEUR)
            .nomMaitreStage(DEFAULT_NOM_MAITRE_STAGE)
            .contactMaitreStage(DEFAULT_CONTACT_MAITRE_STAGE)
            .emailMaitreStage(DEFAULT_EMAIL_MAITRE_STAGE);
        return entreprise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createUpdatedEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .nom(UPDATED_NOM)
            .codeENtreprise(UPDATED_CODE_E_NTREPRISE)
            .secteurActivite(UPDATED_SECTEUR_ACTIVITE)
            .adresse(UPDATED_ADRESSE)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .contactDirecteur(UPDATED_CONTACT_DIRECTEUR)
            .emailDirecteur(UPDATED_EMAIL_DIRECTEUR)
            .nomMaitreStage(UPDATED_NOM_MAITRE_STAGE)
            .contactMaitreStage(UPDATED_CONTACT_MAITRE_STAGE)
            .emailMaitreStage(UPDATED_EMAIL_MAITRE_STAGE);
        return entreprise;
    }

    @BeforeEach
    public void initTest() {
        entreprise = createEntity(em);
    }

    @Test
    @Transactional
    void createEntreprise() throws Exception {
        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();
        // Create the Entreprise
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);
        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entrepriseDTO)))
            .andExpect(status().isCreated());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate + 1);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEntreprise.getCodeENtreprise()).isEqualTo(DEFAULT_CODE_E_NTREPRISE);
        assertThat(testEntreprise.getSecteurActivite()).isEqualTo(DEFAULT_SECTEUR_ACTIVITE);
        assertThat(testEntreprise.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEntreprise.getNomDirecteur()).isEqualTo(DEFAULT_NOM_DIRECTEUR);
        assertThat(testEntreprise.getContactDirecteur()).isEqualTo(DEFAULT_CONTACT_DIRECTEUR);
        assertThat(testEntreprise.getEmailDirecteur()).isEqualTo(DEFAULT_EMAIL_DIRECTEUR);
        assertThat(testEntreprise.getNomMaitreStage()).isEqualTo(DEFAULT_NOM_MAITRE_STAGE);
        assertThat(testEntreprise.getContactMaitreStage()).isEqualTo(DEFAULT_CONTACT_MAITRE_STAGE);
        assertThat(testEntreprise.getEmailMaitreStage()).isEqualTo(DEFAULT_EMAIL_MAITRE_STAGE);
    }

    @Test
    @Transactional
    void createEntrepriseWithExistingId() throws Exception {
        // Create the Entreprise with an existing ID
        entreprise.setId(1L);
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);

        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entrepriseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setNom(null);

        // Create the Entreprise, which fails.
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);

        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entrepriseDTO)))
            .andExpect(status().isBadRequest());

        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeENtrepriseIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrepriseRepository.findAll().size();
        // set the field null
        entreprise.setCodeENtreprise(null);

        // Create the Entreprise, which fails.
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);

        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entrepriseDTO)))
            .andExpect(status().isBadRequest());

        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntreprises() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get all the entrepriseList
        restEntrepriseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].codeENtreprise").value(hasItem(DEFAULT_CODE_E_NTREPRISE)))
            .andExpect(jsonPath("$.[*].secteurActivite").value(hasItem(DEFAULT_SECTEUR_ACTIVITE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].nomDirecteur").value(hasItem(DEFAULT_NOM_DIRECTEUR)))
            .andExpect(jsonPath("$.[*].contactDirecteur").value(hasItem(DEFAULT_CONTACT_DIRECTEUR)))
            .andExpect(jsonPath("$.[*].emailDirecteur").value(hasItem(DEFAULT_EMAIL_DIRECTEUR)))
            .andExpect(jsonPath("$.[*].nomMaitreStage").value(hasItem(DEFAULT_NOM_MAITRE_STAGE)))
            .andExpect(jsonPath("$.[*].contactMaitreStage").value(hasItem(DEFAULT_CONTACT_MAITRE_STAGE)))
            .andExpect(jsonPath("$.[*].emailMaitreStage").value(hasItem(DEFAULT_EMAIL_MAITRE_STAGE)));
    }

    @Test
    @Transactional
    void getEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get the entreprise
        restEntrepriseMockMvc
            .perform(get(ENTITY_API_URL_ID, entreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entreprise.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.codeENtreprise").value(DEFAULT_CODE_E_NTREPRISE))
            .andExpect(jsonPath("$.secteurActivite").value(DEFAULT_SECTEUR_ACTIVITE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.nomDirecteur").value(DEFAULT_NOM_DIRECTEUR))
            .andExpect(jsonPath("$.contactDirecteur").value(DEFAULT_CONTACT_DIRECTEUR))
            .andExpect(jsonPath("$.emailDirecteur").value(DEFAULT_EMAIL_DIRECTEUR))
            .andExpect(jsonPath("$.nomMaitreStage").value(DEFAULT_NOM_MAITRE_STAGE))
            .andExpect(jsonPath("$.contactMaitreStage").value(DEFAULT_CONTACT_MAITRE_STAGE))
            .andExpect(jsonPath("$.emailMaitreStage").value(DEFAULT_EMAIL_MAITRE_STAGE));
    }

    @Test
    @Transactional
    void getNonExistingEntreprise() throws Exception {
        // Get the entreprise
        restEntrepriseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise
        Entreprise updatedEntreprise = entrepriseRepository.findById(entreprise.getId()).get();
        // Disconnect from session so that the updates on updatedEntreprise are not directly saved in db
        em.detach(updatedEntreprise);
        updatedEntreprise
            .nom(UPDATED_NOM)
            .codeENtreprise(UPDATED_CODE_E_NTREPRISE)
            .secteurActivite(UPDATED_SECTEUR_ACTIVITE)
            .adresse(UPDATED_ADRESSE)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .contactDirecteur(UPDATED_CONTACT_DIRECTEUR)
            .emailDirecteur(UPDATED_EMAIL_DIRECTEUR)
            .nomMaitreStage(UPDATED_NOM_MAITRE_STAGE)
            .contactMaitreStage(UPDATED_CONTACT_MAITRE_STAGE)
            .emailMaitreStage(UPDATED_EMAIL_MAITRE_STAGE);
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(updatedEntreprise);

        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entrepriseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entrepriseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntreprise.getCodeENtreprise()).isEqualTo(UPDATED_CODE_E_NTREPRISE);
        assertThat(testEntreprise.getSecteurActivite()).isEqualTo(UPDATED_SECTEUR_ACTIVITE);
        assertThat(testEntreprise.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEntreprise.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testEntreprise.getContactDirecteur()).isEqualTo(UPDATED_CONTACT_DIRECTEUR);
        assertThat(testEntreprise.getEmailDirecteur()).isEqualTo(UPDATED_EMAIL_DIRECTEUR);
        assertThat(testEntreprise.getNomMaitreStage()).isEqualTo(UPDATED_NOM_MAITRE_STAGE);
        assertThat(testEntreprise.getContactMaitreStage()).isEqualTo(UPDATED_CONTACT_MAITRE_STAGE);
        assertThat(testEntreprise.getEmailMaitreStage()).isEqualTo(UPDATED_EMAIL_MAITRE_STAGE);
    }

    @Test
    @Transactional
    void putNonExistingEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // Create the Entreprise
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entrepriseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entrepriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // Create the Entreprise
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entrepriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // Create the Entreprise
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entrepriseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntrepriseWithPatch() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise using partial update
        Entreprise partialUpdatedEntreprise = new Entreprise();
        partialUpdatedEntreprise.setId(entreprise.getId());

        partialUpdatedEntreprise
            .nom(UPDATED_NOM)
            .codeENtreprise(UPDATED_CODE_E_NTREPRISE)
            .secteurActivite(UPDATED_SECTEUR_ACTIVITE)
            .emailDirecteur(UPDATED_EMAIL_DIRECTEUR)
            .emailMaitreStage(UPDATED_EMAIL_MAITRE_STAGE);

        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntreprise.getCodeENtreprise()).isEqualTo(UPDATED_CODE_E_NTREPRISE);
        assertThat(testEntreprise.getSecteurActivite()).isEqualTo(UPDATED_SECTEUR_ACTIVITE);
        assertThat(testEntreprise.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEntreprise.getNomDirecteur()).isEqualTo(DEFAULT_NOM_DIRECTEUR);
        assertThat(testEntreprise.getContactDirecteur()).isEqualTo(DEFAULT_CONTACT_DIRECTEUR);
        assertThat(testEntreprise.getEmailDirecteur()).isEqualTo(UPDATED_EMAIL_DIRECTEUR);
        assertThat(testEntreprise.getNomMaitreStage()).isEqualTo(DEFAULT_NOM_MAITRE_STAGE);
        assertThat(testEntreprise.getContactMaitreStage()).isEqualTo(DEFAULT_CONTACT_MAITRE_STAGE);
        assertThat(testEntreprise.getEmailMaitreStage()).isEqualTo(UPDATED_EMAIL_MAITRE_STAGE);
    }

    @Test
    @Transactional
    void fullUpdateEntrepriseWithPatch() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise using partial update
        Entreprise partialUpdatedEntreprise = new Entreprise();
        partialUpdatedEntreprise.setId(entreprise.getId());

        partialUpdatedEntreprise
            .nom(UPDATED_NOM)
            .codeENtreprise(UPDATED_CODE_E_NTREPRISE)
            .secteurActivite(UPDATED_SECTEUR_ACTIVITE)
            .adresse(UPDATED_ADRESSE)
            .nomDirecteur(UPDATED_NOM_DIRECTEUR)
            .contactDirecteur(UPDATED_CONTACT_DIRECTEUR)
            .emailDirecteur(UPDATED_EMAIL_DIRECTEUR)
            .nomMaitreStage(UPDATED_NOM_MAITRE_STAGE)
            .contactMaitreStage(UPDATED_CONTACT_MAITRE_STAGE)
            .emailMaitreStage(UPDATED_EMAIL_MAITRE_STAGE);

        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEntreprise.getCodeENtreprise()).isEqualTo(UPDATED_CODE_E_NTREPRISE);
        assertThat(testEntreprise.getSecteurActivite()).isEqualTo(UPDATED_SECTEUR_ACTIVITE);
        assertThat(testEntreprise.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEntreprise.getNomDirecteur()).isEqualTo(UPDATED_NOM_DIRECTEUR);
        assertThat(testEntreprise.getContactDirecteur()).isEqualTo(UPDATED_CONTACT_DIRECTEUR);
        assertThat(testEntreprise.getEmailDirecteur()).isEqualTo(UPDATED_EMAIL_DIRECTEUR);
        assertThat(testEntreprise.getNomMaitreStage()).isEqualTo(UPDATED_NOM_MAITRE_STAGE);
        assertThat(testEntreprise.getContactMaitreStage()).isEqualTo(UPDATED_CONTACT_MAITRE_STAGE);
        assertThat(testEntreprise.getEmailMaitreStage()).isEqualTo(UPDATED_EMAIL_MAITRE_STAGE);
    }

    @Test
    @Transactional
    void patchNonExistingEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // Create the Entreprise
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entrepriseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entrepriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // Create the Entreprise
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entrepriseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // Create the Entreprise
        EntrepriseDTO entrepriseDTO = entrepriseMapper.toDto(entreprise);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entrepriseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeDelete = entrepriseRepository.findAll().size();

        // Delete the entreprise
        restEntrepriseMockMvc
            .perform(delete(ENTITY_API_URL_ID, entreprise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
