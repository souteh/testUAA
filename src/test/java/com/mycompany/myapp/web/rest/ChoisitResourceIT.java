package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Choisit;
import com.mycompany.myapp.domain.Attributaire;
import com.mycompany.myapp.repository.ChoisitRepository;
import com.mycompany.myapp.service.ChoisitService;
import com.mycompany.myapp.service.dto.ChoisitCriteria;
import com.mycompany.myapp.service.ChoisitQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChoisitResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ChoisitResourceIT {

    private static final Integer DEFAULT_IDPRODUIT = 1;
    private static final Integer UPDATED_IDPRODUIT = 2;
    private static final Integer SMALLER_IDPRODUIT = 1 - 1;

    @Autowired
    private ChoisitRepository choisitRepository;

    @Autowired
    private ChoisitService choisitService;

    @Autowired
    private ChoisitQueryService choisitQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChoisitMockMvc;

    private Choisit choisit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Choisit createEntity(EntityManager em) {
        Choisit choisit = new Choisit()
            .idproduit(DEFAULT_IDPRODUIT);
        // Add required entity
        Attributaire attributaire;
        if (TestUtil.findAll(em, Attributaire.class).isEmpty()) {
            attributaire = AttributaireResourceIT.createEntity(em);
            em.persist(attributaire);
            em.flush();
        } else {
            attributaire = TestUtil.findAll(em, Attributaire.class).get(0);
        }
        choisit.setIdattributaire(attributaire);
        return choisit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Choisit createUpdatedEntity(EntityManager em) {
        Choisit choisit = new Choisit()
            .idproduit(UPDATED_IDPRODUIT);
        // Add required entity
        Attributaire attributaire;
        if (TestUtil.findAll(em, Attributaire.class).isEmpty()) {
            attributaire = AttributaireResourceIT.createUpdatedEntity(em);
            em.persist(attributaire);
            em.flush();
        } else {
            attributaire = TestUtil.findAll(em, Attributaire.class).get(0);
        }
        choisit.setIdattributaire(attributaire);
        return choisit;
    }

    @BeforeEach
    public void initTest() {
        choisit = createEntity(em);
    }

    @Test
    @Transactional
    public void createChoisit() throws Exception {
        int databaseSizeBeforeCreate = choisitRepository.findAll().size();
        // Create the Choisit
        restChoisitMockMvc.perform(post("/api/choisits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(choisit)))
            .andExpect(status().isCreated());

        // Validate the Choisit in the database
        List<Choisit> choisitList = choisitRepository.findAll();
        assertThat(choisitList).hasSize(databaseSizeBeforeCreate + 1);
        Choisit testChoisit = choisitList.get(choisitList.size() - 1);
        assertThat(testChoisit.getIdproduit()).isEqualTo(DEFAULT_IDPRODUIT);
    }

    @Test
    @Transactional
    public void createChoisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = choisitRepository.findAll().size();

        // Create the Choisit with an existing ID
        choisit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChoisitMockMvc.perform(post("/api/choisits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(choisit)))
            .andExpect(status().isBadRequest());

        // Validate the Choisit in the database
        List<Choisit> choisitList = choisitRepository.findAll();
        assertThat(choisitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdproduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = choisitRepository.findAll().size();
        // set the field null
        choisit.setIdproduit(null);

        // Create the Choisit, which fails.


        restChoisitMockMvc.perform(post("/api/choisits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(choisit)))
            .andExpect(status().isBadRequest());

        List<Choisit> choisitList = choisitRepository.findAll();
        assertThat(choisitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChoisits() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get all the choisitList
        restChoisitMockMvc.perform(get("/api/choisits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].idproduit").value(hasItem(DEFAULT_IDPRODUIT)));
    }
    
    @Test
    @Transactional
    public void getChoisit() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get the choisit
        restChoisitMockMvc.perform(get("/api/choisits/{id}", choisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(choisit.getId().intValue()))
            .andExpect(jsonPath("$.idproduit").value(DEFAULT_IDPRODUIT));
    }


    @Test
    @Transactional
    public void getChoisitsByIdFiltering() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        Long id = choisit.getId();

        defaultChoisitShouldBeFound("id.equals=" + id);
        defaultChoisitShouldNotBeFound("id.notEquals=" + id);

        defaultChoisitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChoisitShouldNotBeFound("id.greaterThan=" + id);

        defaultChoisitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChoisitShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllChoisitsByIdproduitIsEqualToSomething() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get all the choisitList where idproduit equals to DEFAULT_IDPRODUIT
        defaultChoisitShouldBeFound("idproduit.equals=" + DEFAULT_IDPRODUIT);

        // Get all the choisitList where idproduit equals to UPDATED_IDPRODUIT
        defaultChoisitShouldNotBeFound("idproduit.equals=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllChoisitsByIdproduitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get all the choisitList where idproduit not equals to DEFAULT_IDPRODUIT
        defaultChoisitShouldNotBeFound("idproduit.notEquals=" + DEFAULT_IDPRODUIT);

        // Get all the choisitList where idproduit not equals to UPDATED_IDPRODUIT
        defaultChoisitShouldBeFound("idproduit.notEquals=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllChoisitsByIdproduitIsInShouldWork() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get all the choisitList where idproduit in DEFAULT_IDPRODUIT or UPDATED_IDPRODUIT
        defaultChoisitShouldBeFound("idproduit.in=" + DEFAULT_IDPRODUIT + "," + UPDATED_IDPRODUIT);

        // Get all the choisitList where idproduit equals to UPDATED_IDPRODUIT
        defaultChoisitShouldNotBeFound("idproduit.in=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllChoisitsByIdproduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get all the choisitList where idproduit is not null
        defaultChoisitShouldBeFound("idproduit.specified=true");

        // Get all the choisitList where idproduit is null
        defaultChoisitShouldNotBeFound("idproduit.specified=false");
    }

    @Test
    @Transactional
    public void getAllChoisitsByIdproduitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get all the choisitList where idproduit is greater than or equal to DEFAULT_IDPRODUIT
        defaultChoisitShouldBeFound("idproduit.greaterThanOrEqual=" + DEFAULT_IDPRODUIT);

        // Get all the choisitList where idproduit is greater than or equal to UPDATED_IDPRODUIT
        defaultChoisitShouldNotBeFound("idproduit.greaterThanOrEqual=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllChoisitsByIdproduitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get all the choisitList where idproduit is less than or equal to DEFAULT_IDPRODUIT
        defaultChoisitShouldBeFound("idproduit.lessThanOrEqual=" + DEFAULT_IDPRODUIT);

        // Get all the choisitList where idproduit is less than or equal to SMALLER_IDPRODUIT
        defaultChoisitShouldNotBeFound("idproduit.lessThanOrEqual=" + SMALLER_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllChoisitsByIdproduitIsLessThanSomething() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get all the choisitList where idproduit is less than DEFAULT_IDPRODUIT
        defaultChoisitShouldNotBeFound("idproduit.lessThan=" + DEFAULT_IDPRODUIT);

        // Get all the choisitList where idproduit is less than UPDATED_IDPRODUIT
        defaultChoisitShouldBeFound("idproduit.lessThan=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllChoisitsByIdproduitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        choisitRepository.saveAndFlush(choisit);

        // Get all the choisitList where idproduit is greater than DEFAULT_IDPRODUIT
        defaultChoisitShouldNotBeFound("idproduit.greaterThan=" + DEFAULT_IDPRODUIT);

        // Get all the choisitList where idproduit is greater than SMALLER_IDPRODUIT
        defaultChoisitShouldBeFound("idproduit.greaterThan=" + SMALLER_IDPRODUIT);
    }


    @Test
    @Transactional
    public void getAllChoisitsByIdattributaireIsEqualToSomething() throws Exception {
        // Get already existing entity
        Attributaire idattributaire = choisit.getIdattributaire();
        choisitRepository.saveAndFlush(choisit);
        Long idattributaireId = idattributaire.getId();

        // Get all the choisitList where idattributaire equals to idattributaireId
        defaultChoisitShouldBeFound("idattributaireId.equals=" + idattributaireId);

        // Get all the choisitList where idattributaire equals to idattributaireId + 1
        defaultChoisitShouldNotBeFound("idattributaireId.equals=" + (idattributaireId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChoisitShouldBeFound(String filter) throws Exception {
        restChoisitMockMvc.perform(get("/api/choisits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].idproduit").value(hasItem(DEFAULT_IDPRODUIT)));

        // Check, that the count call also returns 1
        restChoisitMockMvc.perform(get("/api/choisits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChoisitShouldNotBeFound(String filter) throws Exception {
        restChoisitMockMvc.perform(get("/api/choisits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChoisitMockMvc.perform(get("/api/choisits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingChoisit() throws Exception {
        // Get the choisit
        restChoisitMockMvc.perform(get("/api/choisits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChoisit() throws Exception {
        // Initialize the database
        choisitService.save(choisit);

        int databaseSizeBeforeUpdate = choisitRepository.findAll().size();

        // Update the choisit
        Choisit updatedChoisit = choisitRepository.findById(choisit.getId()).get();
        // Disconnect from session so that the updates on updatedChoisit are not directly saved in db
        em.detach(updatedChoisit);
        updatedChoisit
            .idproduit(UPDATED_IDPRODUIT);

        restChoisitMockMvc.perform(put("/api/choisits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChoisit)))
            .andExpect(status().isOk());

        // Validate the Choisit in the database
        List<Choisit> choisitList = choisitRepository.findAll();
        assertThat(choisitList).hasSize(databaseSizeBeforeUpdate);
        Choisit testChoisit = choisitList.get(choisitList.size() - 1);
        assertThat(testChoisit.getIdproduit()).isEqualTo(UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void updateNonExistingChoisit() throws Exception {
        int databaseSizeBeforeUpdate = choisitRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChoisitMockMvc.perform(put("/api/choisits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(choisit)))
            .andExpect(status().isBadRequest());

        // Validate the Choisit in the database
        List<Choisit> choisitList = choisitRepository.findAll();
        assertThat(choisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChoisit() throws Exception {
        // Initialize the database
        choisitService.save(choisit);

        int databaseSizeBeforeDelete = choisitRepository.findAll().size();

        // Delete the choisit
        restChoisitMockMvc.perform(delete("/api/choisits/{id}", choisit.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Choisit> choisitList = choisitRepository.findAll();
        assertThat(choisitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
