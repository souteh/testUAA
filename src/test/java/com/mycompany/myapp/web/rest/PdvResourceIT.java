package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Pdv;
import com.mycompany.myapp.repository.PdvRepository;
import com.mycompany.myapp.service.PdvService;
import com.mycompany.myapp.service.dto.PdvCriteria;
import com.mycompany.myapp.service.PdvQueryService;

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
 * Integration tests for the {@link PdvResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PdvResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;
    private static final Integer SMALLER_NUMERO = 1 - 1;

    private static final String DEFAULT_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    @Autowired
    private PdvRepository pdvRepository;

    @Autowired
    private PdvService pdvService;

    @Autowired
    private PdvQueryService pdvQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPdvMockMvc;

    private Pdv pdv;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pdv createEntity(EntityManager em) {
        Pdv pdv = new Pdv()
            .numero(DEFAULT_NUMERO)
            .agence(DEFAULT_AGENCE)
            .designation(DEFAULT_DESIGNATION)
            .ville(DEFAULT_VILLE)
            .statut(DEFAULT_STATUT);
        return pdv;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pdv createUpdatedEntity(EntityManager em) {
        Pdv pdv = new Pdv()
            .numero(UPDATED_NUMERO)
            .agence(UPDATED_AGENCE)
            .designation(UPDATED_DESIGNATION)
            .ville(UPDATED_VILLE)
            .statut(UPDATED_STATUT);
        return pdv;
    }

    @BeforeEach
    public void initTest() {
        pdv = createEntity(em);
    }

    @Test
    @Transactional
    public void createPdv() throws Exception {
        int databaseSizeBeforeCreate = pdvRepository.findAll().size();
        // Create the Pdv
        restPdvMockMvc.perform(post("/api/pdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isCreated());

        // Validate the Pdv in the database
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeCreate + 1);
        Pdv testPdv = pdvList.get(pdvList.size() - 1);
        assertThat(testPdv.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testPdv.getAgence()).isEqualTo(DEFAULT_AGENCE);
        assertThat(testPdv.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testPdv.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testPdv.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    public void createPdvWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pdvRepository.findAll().size();

        // Create the Pdv with an existing ID
        pdv.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPdvMockMvc.perform(post("/api/pdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isBadRequest());

        // Validate the Pdv in the database
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = pdvRepository.findAll().size();
        // set the field null
        pdv.setNumero(null);

        // Create the Pdv, which fails.


        restPdvMockMvc.perform(post("/api/pdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isBadRequest());

        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPdvs() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList
        restPdvMockMvc.perform(get("/api/pdvs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pdv.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].agence").value(hasItem(DEFAULT_AGENCE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));
    }
    
    @Test
    @Transactional
    public void getPdv() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get the pdv
        restPdvMockMvc.perform(get("/api/pdvs/{id}", pdv.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pdv.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.agence").value(DEFAULT_AGENCE))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT));
    }


    @Test
    @Transactional
    public void getPdvsByIdFiltering() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        Long id = pdv.getId();

        defaultPdvShouldBeFound("id.equals=" + id);
        defaultPdvShouldNotBeFound("id.notEquals=" + id);

        defaultPdvShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPdvShouldNotBeFound("id.greaterThan=" + id);

        defaultPdvShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPdvShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPdvsByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where numero equals to DEFAULT_NUMERO
        defaultPdvShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the pdvList where numero equals to UPDATED_NUMERO
        defaultPdvShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllPdvsByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where numero not equals to DEFAULT_NUMERO
        defaultPdvShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the pdvList where numero not equals to UPDATED_NUMERO
        defaultPdvShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllPdvsByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultPdvShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the pdvList where numero equals to UPDATED_NUMERO
        defaultPdvShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllPdvsByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where numero is not null
        defaultPdvShouldBeFound("numero.specified=true");

        // Get all the pdvList where numero is null
        defaultPdvShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    public void getAllPdvsByNumeroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where numero is greater than or equal to DEFAULT_NUMERO
        defaultPdvShouldBeFound("numero.greaterThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the pdvList where numero is greater than or equal to UPDATED_NUMERO
        defaultPdvShouldNotBeFound("numero.greaterThanOrEqual=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllPdvsByNumeroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where numero is less than or equal to DEFAULT_NUMERO
        defaultPdvShouldBeFound("numero.lessThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the pdvList where numero is less than or equal to SMALLER_NUMERO
        defaultPdvShouldNotBeFound("numero.lessThanOrEqual=" + SMALLER_NUMERO);
    }

    @Test
    @Transactional
    public void getAllPdvsByNumeroIsLessThanSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where numero is less than DEFAULT_NUMERO
        defaultPdvShouldNotBeFound("numero.lessThan=" + DEFAULT_NUMERO);

        // Get all the pdvList where numero is less than UPDATED_NUMERO
        defaultPdvShouldBeFound("numero.lessThan=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllPdvsByNumeroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where numero is greater than DEFAULT_NUMERO
        defaultPdvShouldNotBeFound("numero.greaterThan=" + DEFAULT_NUMERO);

        // Get all the pdvList where numero is greater than SMALLER_NUMERO
        defaultPdvShouldBeFound("numero.greaterThan=" + SMALLER_NUMERO);
    }


    @Test
    @Transactional
    public void getAllPdvsByAgenceIsEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where agence equals to DEFAULT_AGENCE
        defaultPdvShouldBeFound("agence.equals=" + DEFAULT_AGENCE);

        // Get all the pdvList where agence equals to UPDATED_AGENCE
        defaultPdvShouldNotBeFound("agence.equals=" + UPDATED_AGENCE);
    }

    @Test
    @Transactional
    public void getAllPdvsByAgenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where agence not equals to DEFAULT_AGENCE
        defaultPdvShouldNotBeFound("agence.notEquals=" + DEFAULT_AGENCE);

        // Get all the pdvList where agence not equals to UPDATED_AGENCE
        defaultPdvShouldBeFound("agence.notEquals=" + UPDATED_AGENCE);
    }

    @Test
    @Transactional
    public void getAllPdvsByAgenceIsInShouldWork() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where agence in DEFAULT_AGENCE or UPDATED_AGENCE
        defaultPdvShouldBeFound("agence.in=" + DEFAULT_AGENCE + "," + UPDATED_AGENCE);

        // Get all the pdvList where agence equals to UPDATED_AGENCE
        defaultPdvShouldNotBeFound("agence.in=" + UPDATED_AGENCE);
    }

    @Test
    @Transactional
    public void getAllPdvsByAgenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where agence is not null
        defaultPdvShouldBeFound("agence.specified=true");

        // Get all the pdvList where agence is null
        defaultPdvShouldNotBeFound("agence.specified=false");
    }
                @Test
    @Transactional
    public void getAllPdvsByAgenceContainsSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where agence contains DEFAULT_AGENCE
        defaultPdvShouldBeFound("agence.contains=" + DEFAULT_AGENCE);

        // Get all the pdvList where agence contains UPDATED_AGENCE
        defaultPdvShouldNotBeFound("agence.contains=" + UPDATED_AGENCE);
    }

    @Test
    @Transactional
    public void getAllPdvsByAgenceNotContainsSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where agence does not contain DEFAULT_AGENCE
        defaultPdvShouldNotBeFound("agence.doesNotContain=" + DEFAULT_AGENCE);

        // Get all the pdvList where agence does not contain UPDATED_AGENCE
        defaultPdvShouldBeFound("agence.doesNotContain=" + UPDATED_AGENCE);
    }


    @Test
    @Transactional
    public void getAllPdvsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where designation equals to DEFAULT_DESIGNATION
        defaultPdvShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the pdvList where designation equals to UPDATED_DESIGNATION
        defaultPdvShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllPdvsByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where designation not equals to DEFAULT_DESIGNATION
        defaultPdvShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the pdvList where designation not equals to UPDATED_DESIGNATION
        defaultPdvShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllPdvsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultPdvShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the pdvList where designation equals to UPDATED_DESIGNATION
        defaultPdvShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllPdvsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where designation is not null
        defaultPdvShouldBeFound("designation.specified=true");

        // Get all the pdvList where designation is null
        defaultPdvShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllPdvsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where designation contains DEFAULT_DESIGNATION
        defaultPdvShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the pdvList where designation contains UPDATED_DESIGNATION
        defaultPdvShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllPdvsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where designation does not contain DEFAULT_DESIGNATION
        defaultPdvShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the pdvList where designation does not contain UPDATED_DESIGNATION
        defaultPdvShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }


    @Test
    @Transactional
    public void getAllPdvsByVilleIsEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where ville equals to DEFAULT_VILLE
        defaultPdvShouldBeFound("ville.equals=" + DEFAULT_VILLE);

        // Get all the pdvList where ville equals to UPDATED_VILLE
        defaultPdvShouldNotBeFound("ville.equals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllPdvsByVilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where ville not equals to DEFAULT_VILLE
        defaultPdvShouldNotBeFound("ville.notEquals=" + DEFAULT_VILLE);

        // Get all the pdvList where ville not equals to UPDATED_VILLE
        defaultPdvShouldBeFound("ville.notEquals=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllPdvsByVilleIsInShouldWork() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where ville in DEFAULT_VILLE or UPDATED_VILLE
        defaultPdvShouldBeFound("ville.in=" + DEFAULT_VILLE + "," + UPDATED_VILLE);

        // Get all the pdvList where ville equals to UPDATED_VILLE
        defaultPdvShouldNotBeFound("ville.in=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllPdvsByVilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where ville is not null
        defaultPdvShouldBeFound("ville.specified=true");

        // Get all the pdvList where ville is null
        defaultPdvShouldNotBeFound("ville.specified=false");
    }
                @Test
    @Transactional
    public void getAllPdvsByVilleContainsSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where ville contains DEFAULT_VILLE
        defaultPdvShouldBeFound("ville.contains=" + DEFAULT_VILLE);

        // Get all the pdvList where ville contains UPDATED_VILLE
        defaultPdvShouldNotBeFound("ville.contains=" + UPDATED_VILLE);
    }

    @Test
    @Transactional
    public void getAllPdvsByVilleNotContainsSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where ville does not contain DEFAULT_VILLE
        defaultPdvShouldNotBeFound("ville.doesNotContain=" + DEFAULT_VILLE);

        // Get all the pdvList where ville does not contain UPDATED_VILLE
        defaultPdvShouldBeFound("ville.doesNotContain=" + UPDATED_VILLE);
    }


    @Test
    @Transactional
    public void getAllPdvsByStatutIsEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where statut equals to DEFAULT_STATUT
        defaultPdvShouldBeFound("statut.equals=" + DEFAULT_STATUT);

        // Get all the pdvList where statut equals to UPDATED_STATUT
        defaultPdvShouldNotBeFound("statut.equals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllPdvsByStatutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where statut not equals to DEFAULT_STATUT
        defaultPdvShouldNotBeFound("statut.notEquals=" + DEFAULT_STATUT);

        // Get all the pdvList where statut not equals to UPDATED_STATUT
        defaultPdvShouldBeFound("statut.notEquals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllPdvsByStatutIsInShouldWork() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where statut in DEFAULT_STATUT or UPDATED_STATUT
        defaultPdvShouldBeFound("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT);

        // Get all the pdvList where statut equals to UPDATED_STATUT
        defaultPdvShouldNotBeFound("statut.in=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllPdvsByStatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where statut is not null
        defaultPdvShouldBeFound("statut.specified=true");

        // Get all the pdvList where statut is null
        defaultPdvShouldNotBeFound("statut.specified=false");
    }
                @Test
    @Transactional
    public void getAllPdvsByStatutContainsSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where statut contains DEFAULT_STATUT
        defaultPdvShouldBeFound("statut.contains=" + DEFAULT_STATUT);

        // Get all the pdvList where statut contains UPDATED_STATUT
        defaultPdvShouldNotBeFound("statut.contains=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllPdvsByStatutNotContainsSomething() throws Exception {
        // Initialize the database
        pdvRepository.saveAndFlush(pdv);

        // Get all the pdvList where statut does not contain DEFAULT_STATUT
        defaultPdvShouldNotBeFound("statut.doesNotContain=" + DEFAULT_STATUT);

        // Get all the pdvList where statut does not contain UPDATED_STATUT
        defaultPdvShouldBeFound("statut.doesNotContain=" + UPDATED_STATUT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPdvShouldBeFound(String filter) throws Exception {
        restPdvMockMvc.perform(get("/api/pdvs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pdv.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].agence").value(hasItem(DEFAULT_AGENCE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));

        // Check, that the count call also returns 1
        restPdvMockMvc.perform(get("/api/pdvs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPdvShouldNotBeFound(String filter) throws Exception {
        restPdvMockMvc.perform(get("/api/pdvs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPdvMockMvc.perform(get("/api/pdvs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPdv() throws Exception {
        // Get the pdv
        restPdvMockMvc.perform(get("/api/pdvs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePdv() throws Exception {
        // Initialize the database
        pdvService.save(pdv);

        int databaseSizeBeforeUpdate = pdvRepository.findAll().size();

        // Update the pdv
        Pdv updatedPdv = pdvRepository.findById(pdv.getId()).get();
        // Disconnect from session so that the updates on updatedPdv are not directly saved in db
        em.detach(updatedPdv);
        updatedPdv
            .numero(UPDATED_NUMERO)
            .agence(UPDATED_AGENCE)
            .designation(UPDATED_DESIGNATION)
            .ville(UPDATED_VILLE)
            .statut(UPDATED_STATUT);

        restPdvMockMvc.perform(put("/api/pdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPdv)))
            .andExpect(status().isOk());

        // Validate the Pdv in the database
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeUpdate);
        Pdv testPdv = pdvList.get(pdvList.size() - 1);
        assertThat(testPdv.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testPdv.getAgence()).isEqualTo(UPDATED_AGENCE);
        assertThat(testPdv.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testPdv.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testPdv.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingPdv() throws Exception {
        int databaseSizeBeforeUpdate = pdvRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPdvMockMvc.perform(put("/api/pdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pdv)))
            .andExpect(status().isBadRequest());

        // Validate the Pdv in the database
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePdv() throws Exception {
        // Initialize the database
        pdvService.save(pdv);

        int databaseSizeBeforeDelete = pdvRepository.findAll().size();

        // Delete the pdv
        restPdvMockMvc.perform(delete("/api/pdvs/{id}", pdv.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pdv> pdvList = pdvRepository.findAll();
        assertThat(pdvList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
