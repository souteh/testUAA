package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Typeterminal;
import com.mycompany.myapp.repository.TypeterminalRepository;
import com.mycompany.myapp.service.TypeterminalService;
import com.mycompany.myapp.service.dto.TypeterminalCriteria;
import com.mycompany.myapp.service.TypeterminalQueryService;

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
 * Integration tests for the {@link TypeterminalResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TypeterminalResourceIT {

    private static final Integer DEFAULT_IDTYPETERMINAL = 1;
    private static final Integer UPDATED_IDTYPETERMINAL = 2;
    private static final Integer SMALLER_IDTYPETERMINAL = 1 - 1;

    private static final String DEFAULT_CODETYPETERMINAL = "AAAAAAAAAA";
    private static final String UPDATED_CODETYPETERMINAL = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeterminalRepository typeterminalRepository;

    @Autowired
    private TypeterminalService typeterminalService;

    @Autowired
    private TypeterminalQueryService typeterminalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeterminalMockMvc;

    private Typeterminal typeterminal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typeterminal createEntity(EntityManager em) {
        Typeterminal typeterminal = new Typeterminal()
            .idtypeterminal(DEFAULT_IDTYPETERMINAL)
            .codetypeterminal(DEFAULT_CODETYPETERMINAL)
            .libelle(DEFAULT_LIBELLE);
        return typeterminal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typeterminal createUpdatedEntity(EntityManager em) {
        Typeterminal typeterminal = new Typeterminal()
            .idtypeterminal(UPDATED_IDTYPETERMINAL)
            .codetypeterminal(UPDATED_CODETYPETERMINAL)
            .libelle(UPDATED_LIBELLE);
        return typeterminal;
    }

    @BeforeEach
    public void initTest() {
        typeterminal = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeterminal() throws Exception {
        int databaseSizeBeforeCreate = typeterminalRepository.findAll().size();
        // Create the Typeterminal
        restTypeterminalMockMvc.perform(post("/api/typeterminals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeterminal)))
            .andExpect(status().isCreated());

        // Validate the Typeterminal in the database
        List<Typeterminal> typeterminalList = typeterminalRepository.findAll();
        assertThat(typeterminalList).hasSize(databaseSizeBeforeCreate + 1);
        Typeterminal testTypeterminal = typeterminalList.get(typeterminalList.size() - 1);
        assertThat(testTypeterminal.getIdtypeterminal()).isEqualTo(DEFAULT_IDTYPETERMINAL);
        assertThat(testTypeterminal.getCodetypeterminal()).isEqualTo(DEFAULT_CODETYPETERMINAL);
        assertThat(testTypeterminal.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeterminalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeterminalRepository.findAll().size();

        // Create the Typeterminal with an existing ID
        typeterminal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeterminalMockMvc.perform(post("/api/typeterminals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeterminal)))
            .andExpect(status().isBadRequest());

        // Validate the Typeterminal in the database
        List<Typeterminal> typeterminalList = typeterminalRepository.findAll();
        assertThat(typeterminalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdtypeterminalIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeterminalRepository.findAll().size();
        // set the field null
        typeterminal.setIdtypeterminal(null);

        // Create the Typeterminal, which fails.


        restTypeterminalMockMvc.perform(post("/api/typeterminals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeterminal)))
            .andExpect(status().isBadRequest());

        List<Typeterminal> typeterminalList = typeterminalRepository.findAll();
        assertThat(typeterminalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeterminals() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList
        restTypeterminalMockMvc.perform(get("/api/typeterminals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeterminal.getId().intValue())))
            .andExpect(jsonPath("$.[*].idtypeterminal").value(hasItem(DEFAULT_IDTYPETERMINAL)))
            .andExpect(jsonPath("$.[*].codetypeterminal").value(hasItem(DEFAULT_CODETYPETERMINAL)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getTypeterminal() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get the typeterminal
        restTypeterminalMockMvc.perform(get("/api/typeterminals/{id}", typeterminal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeterminal.getId().intValue()))
            .andExpect(jsonPath("$.idtypeterminal").value(DEFAULT_IDTYPETERMINAL))
            .andExpect(jsonPath("$.codetypeterminal").value(DEFAULT_CODETYPETERMINAL))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }


    @Test
    @Transactional
    public void getTypeterminalsByIdFiltering() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        Long id = typeterminal.getId();

        defaultTypeterminalShouldBeFound("id.equals=" + id);
        defaultTypeterminalShouldNotBeFound("id.notEquals=" + id);

        defaultTypeterminalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTypeterminalShouldNotBeFound("id.greaterThan=" + id);

        defaultTypeterminalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTypeterminalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTypeterminalsByIdtypeterminalIsEqualToSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where idtypeterminal equals to DEFAULT_IDTYPETERMINAL
        defaultTypeterminalShouldBeFound("idtypeterminal.equals=" + DEFAULT_IDTYPETERMINAL);

        // Get all the typeterminalList where idtypeterminal equals to UPDATED_IDTYPETERMINAL
        defaultTypeterminalShouldNotBeFound("idtypeterminal.equals=" + UPDATED_IDTYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByIdtypeterminalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where idtypeterminal not equals to DEFAULT_IDTYPETERMINAL
        defaultTypeterminalShouldNotBeFound("idtypeterminal.notEquals=" + DEFAULT_IDTYPETERMINAL);

        // Get all the typeterminalList where idtypeterminal not equals to UPDATED_IDTYPETERMINAL
        defaultTypeterminalShouldBeFound("idtypeterminal.notEquals=" + UPDATED_IDTYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByIdtypeterminalIsInShouldWork() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where idtypeterminal in DEFAULT_IDTYPETERMINAL or UPDATED_IDTYPETERMINAL
        defaultTypeterminalShouldBeFound("idtypeterminal.in=" + DEFAULT_IDTYPETERMINAL + "," + UPDATED_IDTYPETERMINAL);

        // Get all the typeterminalList where idtypeterminal equals to UPDATED_IDTYPETERMINAL
        defaultTypeterminalShouldNotBeFound("idtypeterminal.in=" + UPDATED_IDTYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByIdtypeterminalIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where idtypeterminal is not null
        defaultTypeterminalShouldBeFound("idtypeterminal.specified=true");

        // Get all the typeterminalList where idtypeterminal is null
        defaultTypeterminalShouldNotBeFound("idtypeterminal.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByIdtypeterminalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where idtypeterminal is greater than or equal to DEFAULT_IDTYPETERMINAL
        defaultTypeterminalShouldBeFound("idtypeterminal.greaterThanOrEqual=" + DEFAULT_IDTYPETERMINAL);

        // Get all the typeterminalList where idtypeterminal is greater than or equal to UPDATED_IDTYPETERMINAL
        defaultTypeterminalShouldNotBeFound("idtypeterminal.greaterThanOrEqual=" + UPDATED_IDTYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByIdtypeterminalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where idtypeterminal is less than or equal to DEFAULT_IDTYPETERMINAL
        defaultTypeterminalShouldBeFound("idtypeterminal.lessThanOrEqual=" + DEFAULT_IDTYPETERMINAL);

        // Get all the typeterminalList where idtypeterminal is less than or equal to SMALLER_IDTYPETERMINAL
        defaultTypeterminalShouldNotBeFound("idtypeterminal.lessThanOrEqual=" + SMALLER_IDTYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByIdtypeterminalIsLessThanSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where idtypeterminal is less than DEFAULT_IDTYPETERMINAL
        defaultTypeterminalShouldNotBeFound("idtypeterminal.lessThan=" + DEFAULT_IDTYPETERMINAL);

        // Get all the typeterminalList where idtypeterminal is less than UPDATED_IDTYPETERMINAL
        defaultTypeterminalShouldBeFound("idtypeterminal.lessThan=" + UPDATED_IDTYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByIdtypeterminalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where idtypeterminal is greater than DEFAULT_IDTYPETERMINAL
        defaultTypeterminalShouldNotBeFound("idtypeterminal.greaterThan=" + DEFAULT_IDTYPETERMINAL);

        // Get all the typeterminalList where idtypeterminal is greater than SMALLER_IDTYPETERMINAL
        defaultTypeterminalShouldBeFound("idtypeterminal.greaterThan=" + SMALLER_IDTYPETERMINAL);
    }


    @Test
    @Transactional
    public void getAllTypeterminalsByCodetypeterminalIsEqualToSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where codetypeterminal equals to DEFAULT_CODETYPETERMINAL
        defaultTypeterminalShouldBeFound("codetypeterminal.equals=" + DEFAULT_CODETYPETERMINAL);

        // Get all the typeterminalList where codetypeterminal equals to UPDATED_CODETYPETERMINAL
        defaultTypeterminalShouldNotBeFound("codetypeterminal.equals=" + UPDATED_CODETYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByCodetypeterminalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where codetypeterminal not equals to DEFAULT_CODETYPETERMINAL
        defaultTypeterminalShouldNotBeFound("codetypeterminal.notEquals=" + DEFAULT_CODETYPETERMINAL);

        // Get all the typeterminalList where codetypeterminal not equals to UPDATED_CODETYPETERMINAL
        defaultTypeterminalShouldBeFound("codetypeterminal.notEquals=" + UPDATED_CODETYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByCodetypeterminalIsInShouldWork() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where codetypeterminal in DEFAULT_CODETYPETERMINAL or UPDATED_CODETYPETERMINAL
        defaultTypeterminalShouldBeFound("codetypeterminal.in=" + DEFAULT_CODETYPETERMINAL + "," + UPDATED_CODETYPETERMINAL);

        // Get all the typeterminalList where codetypeterminal equals to UPDATED_CODETYPETERMINAL
        defaultTypeterminalShouldNotBeFound("codetypeterminal.in=" + UPDATED_CODETYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByCodetypeterminalIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where codetypeterminal is not null
        defaultTypeterminalShouldBeFound("codetypeterminal.specified=true");

        // Get all the typeterminalList where codetypeterminal is null
        defaultTypeterminalShouldNotBeFound("codetypeterminal.specified=false");
    }
                @Test
    @Transactional
    public void getAllTypeterminalsByCodetypeterminalContainsSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where codetypeterminal contains DEFAULT_CODETYPETERMINAL
        defaultTypeterminalShouldBeFound("codetypeterminal.contains=" + DEFAULT_CODETYPETERMINAL);

        // Get all the typeterminalList where codetypeterminal contains UPDATED_CODETYPETERMINAL
        defaultTypeterminalShouldNotBeFound("codetypeterminal.contains=" + UPDATED_CODETYPETERMINAL);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByCodetypeterminalNotContainsSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where codetypeterminal does not contain DEFAULT_CODETYPETERMINAL
        defaultTypeterminalShouldNotBeFound("codetypeterminal.doesNotContain=" + DEFAULT_CODETYPETERMINAL);

        // Get all the typeterminalList where codetypeterminal does not contain UPDATED_CODETYPETERMINAL
        defaultTypeterminalShouldBeFound("codetypeterminal.doesNotContain=" + UPDATED_CODETYPETERMINAL);
    }


    @Test
    @Transactional
    public void getAllTypeterminalsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where libelle equals to DEFAULT_LIBELLE
        defaultTypeterminalShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the typeterminalList where libelle equals to UPDATED_LIBELLE
        defaultTypeterminalShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where libelle not equals to DEFAULT_LIBELLE
        defaultTypeterminalShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the typeterminalList where libelle not equals to UPDATED_LIBELLE
        defaultTypeterminalShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultTypeterminalShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the typeterminalList where libelle equals to UPDATED_LIBELLE
        defaultTypeterminalShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where libelle is not null
        defaultTypeterminalShouldBeFound("libelle.specified=true");

        // Get all the typeterminalList where libelle is null
        defaultTypeterminalShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllTypeterminalsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where libelle contains DEFAULT_LIBELLE
        defaultTypeterminalShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the typeterminalList where libelle contains UPDATED_LIBELLE
        defaultTypeterminalShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllTypeterminalsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        typeterminalRepository.saveAndFlush(typeterminal);

        // Get all the typeterminalList where libelle does not contain DEFAULT_LIBELLE
        defaultTypeterminalShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the typeterminalList where libelle does not contain UPDATED_LIBELLE
        defaultTypeterminalShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTypeterminalShouldBeFound(String filter) throws Exception {
        restTypeterminalMockMvc.perform(get("/api/typeterminals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeterminal.getId().intValue())))
            .andExpect(jsonPath("$.[*].idtypeterminal").value(hasItem(DEFAULT_IDTYPETERMINAL)))
            .andExpect(jsonPath("$.[*].codetypeterminal").value(hasItem(DEFAULT_CODETYPETERMINAL)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restTypeterminalMockMvc.perform(get("/api/typeterminals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTypeterminalShouldNotBeFound(String filter) throws Exception {
        restTypeterminalMockMvc.perform(get("/api/typeterminals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeterminalMockMvc.perform(get("/api/typeterminals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTypeterminal() throws Exception {
        // Get the typeterminal
        restTypeterminalMockMvc.perform(get("/api/typeterminals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeterminal() throws Exception {
        // Initialize the database
        typeterminalService.save(typeterminal);

        int databaseSizeBeforeUpdate = typeterminalRepository.findAll().size();

        // Update the typeterminal
        Typeterminal updatedTypeterminal = typeterminalRepository.findById(typeterminal.getId()).get();
        // Disconnect from session so that the updates on updatedTypeterminal are not directly saved in db
        em.detach(updatedTypeterminal);
        updatedTypeterminal
            .idtypeterminal(UPDATED_IDTYPETERMINAL)
            .codetypeterminal(UPDATED_CODETYPETERMINAL)
            .libelle(UPDATED_LIBELLE);

        restTypeterminalMockMvc.perform(put("/api/typeterminals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeterminal)))
            .andExpect(status().isOk());

        // Validate the Typeterminal in the database
        List<Typeterminal> typeterminalList = typeterminalRepository.findAll();
        assertThat(typeterminalList).hasSize(databaseSizeBeforeUpdate);
        Typeterminal testTypeterminal = typeterminalList.get(typeterminalList.size() - 1);
        assertThat(testTypeterminal.getIdtypeterminal()).isEqualTo(UPDATED_IDTYPETERMINAL);
        assertThat(testTypeterminal.getCodetypeterminal()).isEqualTo(UPDATED_CODETYPETERMINAL);
        assertThat(testTypeterminal.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeterminal() throws Exception {
        int databaseSizeBeforeUpdate = typeterminalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeterminalMockMvc.perform(put("/api/typeterminals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeterminal)))
            .andExpect(status().isBadRequest());

        // Validate the Typeterminal in the database
        List<Typeterminal> typeterminalList = typeterminalRepository.findAll();
        assertThat(typeterminalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeterminal() throws Exception {
        // Initialize the database
        typeterminalService.save(typeterminal);

        int databaseSizeBeforeDelete = typeterminalRepository.findAll().size();

        // Delete the typeterminal
        restTypeterminalMockMvc.perform(delete("/api/typeterminals/{id}", typeterminal.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Typeterminal> typeterminalList = typeterminalRepository.findAll();
        assertThat(typeterminalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
