package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Paiementlots;
import com.mycompany.myapp.repository.PaiementlotsRepository;
import com.mycompany.myapp.service.PaiementlotsService;
import com.mycompany.myapp.service.dto.PaiementlotsCriteria;
import com.mycompany.myapp.service.PaiementlotsQueryService;

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
 * Integration tests for the {@link PaiementlotsResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PaiementlotsResourceIT {

    private static final Integer DEFAULT_IDLOTPAIEMENT = 1;
    private static final Integer UPDATED_IDLOTPAIEMENT = 2;
    private static final Integer SMALLER_IDLOTPAIEMENT = 1 - 1;

    private static final String DEFAULT_CODEPAIEMENT = "AAAAAAAAAA";
    private static final String UPDATED_CODEPAIEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEUIL = 1;
    private static final Integer UPDATED_SEUIL = 2;
    private static final Integer SMALLER_SEUIL = 1 - 1;

    private static final Integer DEFAULT_MONTANTAVANCE = 1;
    private static final Integer UPDATED_MONTANTAVANCE = 2;
    private static final Integer SMALLER_MONTANTAVANCE = 1 - 1;

    private static final Integer DEFAULT_DELAIPURGE = 1;
    private static final Integer UPDATED_DELAIPURGE = 2;
    private static final Integer SMALLER_DELAIPURGE = 1 - 1;

    private static final String DEFAULT_LIEUAUTORISE = "AAAAAAAAAA";
    private static final String UPDATED_LIEUAUTORISE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEUANNULATION = "AAAAAAAAAA";
    private static final String UPDATED_LIEUANNULATION = "BBBBBBBBBB";

    @Autowired
    private PaiementlotsRepository paiementlotsRepository;

    @Autowired
    private PaiementlotsService paiementlotsService;

    @Autowired
    private PaiementlotsQueryService paiementlotsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaiementlotsMockMvc;

    private Paiementlots paiementlots;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paiementlots createEntity(EntityManager em) {
        Paiementlots paiementlots = new Paiementlots()
            .idlotpaiement(DEFAULT_IDLOTPAIEMENT)
            .codepaiement(DEFAULT_CODEPAIEMENT)
            .type(DEFAULT_TYPE)
            .seuil(DEFAULT_SEUIL)
            .montantavance(DEFAULT_MONTANTAVANCE)
            .delaipurge(DEFAULT_DELAIPURGE)
            .lieuautorise(DEFAULT_LIEUAUTORISE)
            .lieuannulation(DEFAULT_LIEUANNULATION);
        return paiementlots;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paiementlots createUpdatedEntity(EntityManager em) {
        Paiementlots paiementlots = new Paiementlots()
            .idlotpaiement(UPDATED_IDLOTPAIEMENT)
            .codepaiement(UPDATED_CODEPAIEMENT)
            .type(UPDATED_TYPE)
            .seuil(UPDATED_SEUIL)
            .montantavance(UPDATED_MONTANTAVANCE)
            .delaipurge(UPDATED_DELAIPURGE)
            .lieuautorise(UPDATED_LIEUAUTORISE)
            .lieuannulation(UPDATED_LIEUANNULATION);
        return paiementlots;
    }

    @BeforeEach
    public void initTest() {
        paiementlots = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaiementlots() throws Exception {
        int databaseSizeBeforeCreate = paiementlotsRepository.findAll().size();
        // Create the Paiementlots
        restPaiementlotsMockMvc.perform(post("/api/paiementlots").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementlots)))
            .andExpect(status().isCreated());

        // Validate the Paiementlots in the database
        List<Paiementlots> paiementlotsList = paiementlotsRepository.findAll();
        assertThat(paiementlotsList).hasSize(databaseSizeBeforeCreate + 1);
        Paiementlots testPaiementlots = paiementlotsList.get(paiementlotsList.size() - 1);
        assertThat(testPaiementlots.getIdlotpaiement()).isEqualTo(DEFAULT_IDLOTPAIEMENT);
        assertThat(testPaiementlots.getCodepaiement()).isEqualTo(DEFAULT_CODEPAIEMENT);
        assertThat(testPaiementlots.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPaiementlots.getSeuil()).isEqualTo(DEFAULT_SEUIL);
        assertThat(testPaiementlots.getMontantavance()).isEqualTo(DEFAULT_MONTANTAVANCE);
        assertThat(testPaiementlots.getDelaipurge()).isEqualTo(DEFAULT_DELAIPURGE);
        assertThat(testPaiementlots.getLieuautorise()).isEqualTo(DEFAULT_LIEUAUTORISE);
        assertThat(testPaiementlots.getLieuannulation()).isEqualTo(DEFAULT_LIEUANNULATION);
    }

    @Test
    @Transactional
    public void createPaiementlotsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paiementlotsRepository.findAll().size();

        // Create the Paiementlots with an existing ID
        paiementlots.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaiementlotsMockMvc.perform(post("/api/paiementlots").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementlots)))
            .andExpect(status().isBadRequest());

        // Validate the Paiementlots in the database
        List<Paiementlots> paiementlotsList = paiementlotsRepository.findAll();
        assertThat(paiementlotsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdlotpaiementIsRequired() throws Exception {
        int databaseSizeBeforeTest = paiementlotsRepository.findAll().size();
        // set the field null
        paiementlots.setIdlotpaiement(null);

        // Create the Paiementlots, which fails.


        restPaiementlotsMockMvc.perform(post("/api/paiementlots").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementlots)))
            .andExpect(status().isBadRequest());

        List<Paiementlots> paiementlotsList = paiementlotsRepository.findAll();
        assertThat(paiementlotsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaiementlots() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList
        restPaiementlotsMockMvc.perform(get("/api/paiementlots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiementlots.getId().intValue())))
            .andExpect(jsonPath("$.[*].idlotpaiement").value(hasItem(DEFAULT_IDLOTPAIEMENT)))
            .andExpect(jsonPath("$.[*].codepaiement").value(hasItem(DEFAULT_CODEPAIEMENT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].seuil").value(hasItem(DEFAULT_SEUIL)))
            .andExpect(jsonPath("$.[*].montantavance").value(hasItem(DEFAULT_MONTANTAVANCE)))
            .andExpect(jsonPath("$.[*].delaipurge").value(hasItem(DEFAULT_DELAIPURGE)))
            .andExpect(jsonPath("$.[*].lieuautorise").value(hasItem(DEFAULT_LIEUAUTORISE)))
            .andExpect(jsonPath("$.[*].lieuannulation").value(hasItem(DEFAULT_LIEUANNULATION)));
    }
    
    @Test
    @Transactional
    public void getPaiementlots() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get the paiementlots
        restPaiementlotsMockMvc.perform(get("/api/paiementlots/{id}", paiementlots.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paiementlots.getId().intValue()))
            .andExpect(jsonPath("$.idlotpaiement").value(DEFAULT_IDLOTPAIEMENT))
            .andExpect(jsonPath("$.codepaiement").value(DEFAULT_CODEPAIEMENT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.seuil").value(DEFAULT_SEUIL))
            .andExpect(jsonPath("$.montantavance").value(DEFAULT_MONTANTAVANCE))
            .andExpect(jsonPath("$.delaipurge").value(DEFAULT_DELAIPURGE))
            .andExpect(jsonPath("$.lieuautorise").value(DEFAULT_LIEUAUTORISE))
            .andExpect(jsonPath("$.lieuannulation").value(DEFAULT_LIEUANNULATION));
    }


    @Test
    @Transactional
    public void getPaiementlotsByIdFiltering() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        Long id = paiementlots.getId();

        defaultPaiementlotsShouldBeFound("id.equals=" + id);
        defaultPaiementlotsShouldNotBeFound("id.notEquals=" + id);

        defaultPaiementlotsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaiementlotsShouldNotBeFound("id.greaterThan=" + id);

        defaultPaiementlotsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaiementlotsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaiementlotsByIdlotpaiementIsEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where idlotpaiement equals to DEFAULT_IDLOTPAIEMENT
        defaultPaiementlotsShouldBeFound("idlotpaiement.equals=" + DEFAULT_IDLOTPAIEMENT);

        // Get all the paiementlotsList where idlotpaiement equals to UPDATED_IDLOTPAIEMENT
        defaultPaiementlotsShouldNotBeFound("idlotpaiement.equals=" + UPDATED_IDLOTPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByIdlotpaiementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where idlotpaiement not equals to DEFAULT_IDLOTPAIEMENT
        defaultPaiementlotsShouldNotBeFound("idlotpaiement.notEquals=" + DEFAULT_IDLOTPAIEMENT);

        // Get all the paiementlotsList where idlotpaiement not equals to UPDATED_IDLOTPAIEMENT
        defaultPaiementlotsShouldBeFound("idlotpaiement.notEquals=" + UPDATED_IDLOTPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByIdlotpaiementIsInShouldWork() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where idlotpaiement in DEFAULT_IDLOTPAIEMENT or UPDATED_IDLOTPAIEMENT
        defaultPaiementlotsShouldBeFound("idlotpaiement.in=" + DEFAULT_IDLOTPAIEMENT + "," + UPDATED_IDLOTPAIEMENT);

        // Get all the paiementlotsList where idlotpaiement equals to UPDATED_IDLOTPAIEMENT
        defaultPaiementlotsShouldNotBeFound("idlotpaiement.in=" + UPDATED_IDLOTPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByIdlotpaiementIsNullOrNotNull() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where idlotpaiement is not null
        defaultPaiementlotsShouldBeFound("idlotpaiement.specified=true");

        // Get all the paiementlotsList where idlotpaiement is null
        defaultPaiementlotsShouldNotBeFound("idlotpaiement.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByIdlotpaiementIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where idlotpaiement is greater than or equal to DEFAULT_IDLOTPAIEMENT
        defaultPaiementlotsShouldBeFound("idlotpaiement.greaterThanOrEqual=" + DEFAULT_IDLOTPAIEMENT);

        // Get all the paiementlotsList where idlotpaiement is greater than or equal to UPDATED_IDLOTPAIEMENT
        defaultPaiementlotsShouldNotBeFound("idlotpaiement.greaterThanOrEqual=" + UPDATED_IDLOTPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByIdlotpaiementIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where idlotpaiement is less than or equal to DEFAULT_IDLOTPAIEMENT
        defaultPaiementlotsShouldBeFound("idlotpaiement.lessThanOrEqual=" + DEFAULT_IDLOTPAIEMENT);

        // Get all the paiementlotsList where idlotpaiement is less than or equal to SMALLER_IDLOTPAIEMENT
        defaultPaiementlotsShouldNotBeFound("idlotpaiement.lessThanOrEqual=" + SMALLER_IDLOTPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByIdlotpaiementIsLessThanSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where idlotpaiement is less than DEFAULT_IDLOTPAIEMENT
        defaultPaiementlotsShouldNotBeFound("idlotpaiement.lessThan=" + DEFAULT_IDLOTPAIEMENT);

        // Get all the paiementlotsList where idlotpaiement is less than UPDATED_IDLOTPAIEMENT
        defaultPaiementlotsShouldBeFound("idlotpaiement.lessThan=" + UPDATED_IDLOTPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByIdlotpaiementIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where idlotpaiement is greater than DEFAULT_IDLOTPAIEMENT
        defaultPaiementlotsShouldNotBeFound("idlotpaiement.greaterThan=" + DEFAULT_IDLOTPAIEMENT);

        // Get all the paiementlotsList where idlotpaiement is greater than SMALLER_IDLOTPAIEMENT
        defaultPaiementlotsShouldBeFound("idlotpaiement.greaterThan=" + SMALLER_IDLOTPAIEMENT);
    }


    @Test
    @Transactional
    public void getAllPaiementlotsByCodepaiementIsEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where codepaiement equals to DEFAULT_CODEPAIEMENT
        defaultPaiementlotsShouldBeFound("codepaiement.equals=" + DEFAULT_CODEPAIEMENT);

        // Get all the paiementlotsList where codepaiement equals to UPDATED_CODEPAIEMENT
        defaultPaiementlotsShouldNotBeFound("codepaiement.equals=" + UPDATED_CODEPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByCodepaiementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where codepaiement not equals to DEFAULT_CODEPAIEMENT
        defaultPaiementlotsShouldNotBeFound("codepaiement.notEquals=" + DEFAULT_CODEPAIEMENT);

        // Get all the paiementlotsList where codepaiement not equals to UPDATED_CODEPAIEMENT
        defaultPaiementlotsShouldBeFound("codepaiement.notEquals=" + UPDATED_CODEPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByCodepaiementIsInShouldWork() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where codepaiement in DEFAULT_CODEPAIEMENT or UPDATED_CODEPAIEMENT
        defaultPaiementlotsShouldBeFound("codepaiement.in=" + DEFAULT_CODEPAIEMENT + "," + UPDATED_CODEPAIEMENT);

        // Get all the paiementlotsList where codepaiement equals to UPDATED_CODEPAIEMENT
        defaultPaiementlotsShouldNotBeFound("codepaiement.in=" + UPDATED_CODEPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByCodepaiementIsNullOrNotNull() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where codepaiement is not null
        defaultPaiementlotsShouldBeFound("codepaiement.specified=true");

        // Get all the paiementlotsList where codepaiement is null
        defaultPaiementlotsShouldNotBeFound("codepaiement.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaiementlotsByCodepaiementContainsSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where codepaiement contains DEFAULT_CODEPAIEMENT
        defaultPaiementlotsShouldBeFound("codepaiement.contains=" + DEFAULT_CODEPAIEMENT);

        // Get all the paiementlotsList where codepaiement contains UPDATED_CODEPAIEMENT
        defaultPaiementlotsShouldNotBeFound("codepaiement.contains=" + UPDATED_CODEPAIEMENT);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByCodepaiementNotContainsSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where codepaiement does not contain DEFAULT_CODEPAIEMENT
        defaultPaiementlotsShouldNotBeFound("codepaiement.doesNotContain=" + DEFAULT_CODEPAIEMENT);

        // Get all the paiementlotsList where codepaiement does not contain UPDATED_CODEPAIEMENT
        defaultPaiementlotsShouldBeFound("codepaiement.doesNotContain=" + UPDATED_CODEPAIEMENT);
    }


    @Test
    @Transactional
    public void getAllPaiementlotsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where type equals to DEFAULT_TYPE
        defaultPaiementlotsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the paiementlotsList where type equals to UPDATED_TYPE
        defaultPaiementlotsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where type not equals to DEFAULT_TYPE
        defaultPaiementlotsShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the paiementlotsList where type not equals to UPDATED_TYPE
        defaultPaiementlotsShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPaiementlotsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the paiementlotsList where type equals to UPDATED_TYPE
        defaultPaiementlotsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where type is not null
        defaultPaiementlotsShouldBeFound("type.specified=true");

        // Get all the paiementlotsList where type is null
        defaultPaiementlotsShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaiementlotsByTypeContainsSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where type contains DEFAULT_TYPE
        defaultPaiementlotsShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the paiementlotsList where type contains UPDATED_TYPE
        defaultPaiementlotsShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where type does not contain DEFAULT_TYPE
        defaultPaiementlotsShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the paiementlotsList where type does not contain UPDATED_TYPE
        defaultPaiementlotsShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllPaiementlotsBySeuilIsEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where seuil equals to DEFAULT_SEUIL
        defaultPaiementlotsShouldBeFound("seuil.equals=" + DEFAULT_SEUIL);

        // Get all the paiementlotsList where seuil equals to UPDATED_SEUIL
        defaultPaiementlotsShouldNotBeFound("seuil.equals=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsBySeuilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where seuil not equals to DEFAULT_SEUIL
        defaultPaiementlotsShouldNotBeFound("seuil.notEquals=" + DEFAULT_SEUIL);

        // Get all the paiementlotsList where seuil not equals to UPDATED_SEUIL
        defaultPaiementlotsShouldBeFound("seuil.notEquals=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsBySeuilIsInShouldWork() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where seuil in DEFAULT_SEUIL or UPDATED_SEUIL
        defaultPaiementlotsShouldBeFound("seuil.in=" + DEFAULT_SEUIL + "," + UPDATED_SEUIL);

        // Get all the paiementlotsList where seuil equals to UPDATED_SEUIL
        defaultPaiementlotsShouldNotBeFound("seuil.in=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsBySeuilIsNullOrNotNull() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where seuil is not null
        defaultPaiementlotsShouldBeFound("seuil.specified=true");

        // Get all the paiementlotsList where seuil is null
        defaultPaiementlotsShouldNotBeFound("seuil.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaiementlotsBySeuilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where seuil is greater than or equal to DEFAULT_SEUIL
        defaultPaiementlotsShouldBeFound("seuil.greaterThanOrEqual=" + DEFAULT_SEUIL);

        // Get all the paiementlotsList where seuil is greater than or equal to UPDATED_SEUIL
        defaultPaiementlotsShouldNotBeFound("seuil.greaterThanOrEqual=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsBySeuilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where seuil is less than or equal to DEFAULT_SEUIL
        defaultPaiementlotsShouldBeFound("seuil.lessThanOrEqual=" + DEFAULT_SEUIL);

        // Get all the paiementlotsList where seuil is less than or equal to SMALLER_SEUIL
        defaultPaiementlotsShouldNotBeFound("seuil.lessThanOrEqual=" + SMALLER_SEUIL);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsBySeuilIsLessThanSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where seuil is less than DEFAULT_SEUIL
        defaultPaiementlotsShouldNotBeFound("seuil.lessThan=" + DEFAULT_SEUIL);

        // Get all the paiementlotsList where seuil is less than UPDATED_SEUIL
        defaultPaiementlotsShouldBeFound("seuil.lessThan=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsBySeuilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where seuil is greater than DEFAULT_SEUIL
        defaultPaiementlotsShouldNotBeFound("seuil.greaterThan=" + DEFAULT_SEUIL);

        // Get all the paiementlotsList where seuil is greater than SMALLER_SEUIL
        defaultPaiementlotsShouldBeFound("seuil.greaterThan=" + SMALLER_SEUIL);
    }


    @Test
    @Transactional
    public void getAllPaiementlotsByMontantavanceIsEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where montantavance equals to DEFAULT_MONTANTAVANCE
        defaultPaiementlotsShouldBeFound("montantavance.equals=" + DEFAULT_MONTANTAVANCE);

        // Get all the paiementlotsList where montantavance equals to UPDATED_MONTANTAVANCE
        defaultPaiementlotsShouldNotBeFound("montantavance.equals=" + UPDATED_MONTANTAVANCE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByMontantavanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where montantavance not equals to DEFAULT_MONTANTAVANCE
        defaultPaiementlotsShouldNotBeFound("montantavance.notEquals=" + DEFAULT_MONTANTAVANCE);

        // Get all the paiementlotsList where montantavance not equals to UPDATED_MONTANTAVANCE
        defaultPaiementlotsShouldBeFound("montantavance.notEquals=" + UPDATED_MONTANTAVANCE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByMontantavanceIsInShouldWork() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where montantavance in DEFAULT_MONTANTAVANCE or UPDATED_MONTANTAVANCE
        defaultPaiementlotsShouldBeFound("montantavance.in=" + DEFAULT_MONTANTAVANCE + "," + UPDATED_MONTANTAVANCE);

        // Get all the paiementlotsList where montantavance equals to UPDATED_MONTANTAVANCE
        defaultPaiementlotsShouldNotBeFound("montantavance.in=" + UPDATED_MONTANTAVANCE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByMontantavanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where montantavance is not null
        defaultPaiementlotsShouldBeFound("montantavance.specified=true");

        // Get all the paiementlotsList where montantavance is null
        defaultPaiementlotsShouldNotBeFound("montantavance.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByMontantavanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where montantavance is greater than or equal to DEFAULT_MONTANTAVANCE
        defaultPaiementlotsShouldBeFound("montantavance.greaterThanOrEqual=" + DEFAULT_MONTANTAVANCE);

        // Get all the paiementlotsList where montantavance is greater than or equal to UPDATED_MONTANTAVANCE
        defaultPaiementlotsShouldNotBeFound("montantavance.greaterThanOrEqual=" + UPDATED_MONTANTAVANCE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByMontantavanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where montantavance is less than or equal to DEFAULT_MONTANTAVANCE
        defaultPaiementlotsShouldBeFound("montantavance.lessThanOrEqual=" + DEFAULT_MONTANTAVANCE);

        // Get all the paiementlotsList where montantavance is less than or equal to SMALLER_MONTANTAVANCE
        defaultPaiementlotsShouldNotBeFound("montantavance.lessThanOrEqual=" + SMALLER_MONTANTAVANCE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByMontantavanceIsLessThanSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where montantavance is less than DEFAULT_MONTANTAVANCE
        defaultPaiementlotsShouldNotBeFound("montantavance.lessThan=" + DEFAULT_MONTANTAVANCE);

        // Get all the paiementlotsList where montantavance is less than UPDATED_MONTANTAVANCE
        defaultPaiementlotsShouldBeFound("montantavance.lessThan=" + UPDATED_MONTANTAVANCE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByMontantavanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where montantavance is greater than DEFAULT_MONTANTAVANCE
        defaultPaiementlotsShouldNotBeFound("montantavance.greaterThan=" + DEFAULT_MONTANTAVANCE);

        // Get all the paiementlotsList where montantavance is greater than SMALLER_MONTANTAVANCE
        defaultPaiementlotsShouldBeFound("montantavance.greaterThan=" + SMALLER_MONTANTAVANCE);
    }


    @Test
    @Transactional
    public void getAllPaiementlotsByDelaipurgeIsEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where delaipurge equals to DEFAULT_DELAIPURGE
        defaultPaiementlotsShouldBeFound("delaipurge.equals=" + DEFAULT_DELAIPURGE);

        // Get all the paiementlotsList where delaipurge equals to UPDATED_DELAIPURGE
        defaultPaiementlotsShouldNotBeFound("delaipurge.equals=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByDelaipurgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where delaipurge not equals to DEFAULT_DELAIPURGE
        defaultPaiementlotsShouldNotBeFound("delaipurge.notEquals=" + DEFAULT_DELAIPURGE);

        // Get all the paiementlotsList where delaipurge not equals to UPDATED_DELAIPURGE
        defaultPaiementlotsShouldBeFound("delaipurge.notEquals=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByDelaipurgeIsInShouldWork() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where delaipurge in DEFAULT_DELAIPURGE or UPDATED_DELAIPURGE
        defaultPaiementlotsShouldBeFound("delaipurge.in=" + DEFAULT_DELAIPURGE + "," + UPDATED_DELAIPURGE);

        // Get all the paiementlotsList where delaipurge equals to UPDATED_DELAIPURGE
        defaultPaiementlotsShouldNotBeFound("delaipurge.in=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByDelaipurgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where delaipurge is not null
        defaultPaiementlotsShouldBeFound("delaipurge.specified=true");

        // Get all the paiementlotsList where delaipurge is null
        defaultPaiementlotsShouldNotBeFound("delaipurge.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByDelaipurgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where delaipurge is greater than or equal to DEFAULT_DELAIPURGE
        defaultPaiementlotsShouldBeFound("delaipurge.greaterThanOrEqual=" + DEFAULT_DELAIPURGE);

        // Get all the paiementlotsList where delaipurge is greater than or equal to UPDATED_DELAIPURGE
        defaultPaiementlotsShouldNotBeFound("delaipurge.greaterThanOrEqual=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByDelaipurgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where delaipurge is less than or equal to DEFAULT_DELAIPURGE
        defaultPaiementlotsShouldBeFound("delaipurge.lessThanOrEqual=" + DEFAULT_DELAIPURGE);

        // Get all the paiementlotsList where delaipurge is less than or equal to SMALLER_DELAIPURGE
        defaultPaiementlotsShouldNotBeFound("delaipurge.lessThanOrEqual=" + SMALLER_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByDelaipurgeIsLessThanSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where delaipurge is less than DEFAULT_DELAIPURGE
        defaultPaiementlotsShouldNotBeFound("delaipurge.lessThan=" + DEFAULT_DELAIPURGE);

        // Get all the paiementlotsList where delaipurge is less than UPDATED_DELAIPURGE
        defaultPaiementlotsShouldBeFound("delaipurge.lessThan=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByDelaipurgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where delaipurge is greater than DEFAULT_DELAIPURGE
        defaultPaiementlotsShouldNotBeFound("delaipurge.greaterThan=" + DEFAULT_DELAIPURGE);

        // Get all the paiementlotsList where delaipurge is greater than SMALLER_DELAIPURGE
        defaultPaiementlotsShouldBeFound("delaipurge.greaterThan=" + SMALLER_DELAIPURGE);
    }


    @Test
    @Transactional
    public void getAllPaiementlotsByLieuautoriseIsEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuautorise equals to DEFAULT_LIEUAUTORISE
        defaultPaiementlotsShouldBeFound("lieuautorise.equals=" + DEFAULT_LIEUAUTORISE);

        // Get all the paiementlotsList where lieuautorise equals to UPDATED_LIEUAUTORISE
        defaultPaiementlotsShouldNotBeFound("lieuautorise.equals=" + UPDATED_LIEUAUTORISE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByLieuautoriseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuautorise not equals to DEFAULT_LIEUAUTORISE
        defaultPaiementlotsShouldNotBeFound("lieuautorise.notEquals=" + DEFAULT_LIEUAUTORISE);

        // Get all the paiementlotsList where lieuautorise not equals to UPDATED_LIEUAUTORISE
        defaultPaiementlotsShouldBeFound("lieuautorise.notEquals=" + UPDATED_LIEUAUTORISE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByLieuautoriseIsInShouldWork() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuautorise in DEFAULT_LIEUAUTORISE or UPDATED_LIEUAUTORISE
        defaultPaiementlotsShouldBeFound("lieuautorise.in=" + DEFAULT_LIEUAUTORISE + "," + UPDATED_LIEUAUTORISE);

        // Get all the paiementlotsList where lieuautorise equals to UPDATED_LIEUAUTORISE
        defaultPaiementlotsShouldNotBeFound("lieuautorise.in=" + UPDATED_LIEUAUTORISE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByLieuautoriseIsNullOrNotNull() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuautorise is not null
        defaultPaiementlotsShouldBeFound("lieuautorise.specified=true");

        // Get all the paiementlotsList where lieuautorise is null
        defaultPaiementlotsShouldNotBeFound("lieuautorise.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaiementlotsByLieuautoriseContainsSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuautorise contains DEFAULT_LIEUAUTORISE
        defaultPaiementlotsShouldBeFound("lieuautorise.contains=" + DEFAULT_LIEUAUTORISE);

        // Get all the paiementlotsList where lieuautorise contains UPDATED_LIEUAUTORISE
        defaultPaiementlotsShouldNotBeFound("lieuautorise.contains=" + UPDATED_LIEUAUTORISE);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByLieuautoriseNotContainsSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuautorise does not contain DEFAULT_LIEUAUTORISE
        defaultPaiementlotsShouldNotBeFound("lieuautorise.doesNotContain=" + DEFAULT_LIEUAUTORISE);

        // Get all the paiementlotsList where lieuautorise does not contain UPDATED_LIEUAUTORISE
        defaultPaiementlotsShouldBeFound("lieuautorise.doesNotContain=" + UPDATED_LIEUAUTORISE);
    }


    @Test
    @Transactional
    public void getAllPaiementlotsByLieuannulationIsEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuannulation equals to DEFAULT_LIEUANNULATION
        defaultPaiementlotsShouldBeFound("lieuannulation.equals=" + DEFAULT_LIEUANNULATION);

        // Get all the paiementlotsList where lieuannulation equals to UPDATED_LIEUANNULATION
        defaultPaiementlotsShouldNotBeFound("lieuannulation.equals=" + UPDATED_LIEUANNULATION);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByLieuannulationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuannulation not equals to DEFAULT_LIEUANNULATION
        defaultPaiementlotsShouldNotBeFound("lieuannulation.notEquals=" + DEFAULT_LIEUANNULATION);

        // Get all the paiementlotsList where lieuannulation not equals to UPDATED_LIEUANNULATION
        defaultPaiementlotsShouldBeFound("lieuannulation.notEquals=" + UPDATED_LIEUANNULATION);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByLieuannulationIsInShouldWork() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuannulation in DEFAULT_LIEUANNULATION or UPDATED_LIEUANNULATION
        defaultPaiementlotsShouldBeFound("lieuannulation.in=" + DEFAULT_LIEUANNULATION + "," + UPDATED_LIEUANNULATION);

        // Get all the paiementlotsList where lieuannulation equals to UPDATED_LIEUANNULATION
        defaultPaiementlotsShouldNotBeFound("lieuannulation.in=" + UPDATED_LIEUANNULATION);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByLieuannulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuannulation is not null
        defaultPaiementlotsShouldBeFound("lieuannulation.specified=true");

        // Get all the paiementlotsList where lieuannulation is null
        defaultPaiementlotsShouldNotBeFound("lieuannulation.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaiementlotsByLieuannulationContainsSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuannulation contains DEFAULT_LIEUANNULATION
        defaultPaiementlotsShouldBeFound("lieuannulation.contains=" + DEFAULT_LIEUANNULATION);

        // Get all the paiementlotsList where lieuannulation contains UPDATED_LIEUANNULATION
        defaultPaiementlotsShouldNotBeFound("lieuannulation.contains=" + UPDATED_LIEUANNULATION);
    }

    @Test
    @Transactional
    public void getAllPaiementlotsByLieuannulationNotContainsSomething() throws Exception {
        // Initialize the database
        paiementlotsRepository.saveAndFlush(paiementlots);

        // Get all the paiementlotsList where lieuannulation does not contain DEFAULT_LIEUANNULATION
        defaultPaiementlotsShouldNotBeFound("lieuannulation.doesNotContain=" + DEFAULT_LIEUANNULATION);

        // Get all the paiementlotsList where lieuannulation does not contain UPDATED_LIEUANNULATION
        defaultPaiementlotsShouldBeFound("lieuannulation.doesNotContain=" + UPDATED_LIEUANNULATION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaiementlotsShouldBeFound(String filter) throws Exception {
        restPaiementlotsMockMvc.perform(get("/api/paiementlots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiementlots.getId().intValue())))
            .andExpect(jsonPath("$.[*].idlotpaiement").value(hasItem(DEFAULT_IDLOTPAIEMENT)))
            .andExpect(jsonPath("$.[*].codepaiement").value(hasItem(DEFAULT_CODEPAIEMENT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].seuil").value(hasItem(DEFAULT_SEUIL)))
            .andExpect(jsonPath("$.[*].montantavance").value(hasItem(DEFAULT_MONTANTAVANCE)))
            .andExpect(jsonPath("$.[*].delaipurge").value(hasItem(DEFAULT_DELAIPURGE)))
            .andExpect(jsonPath("$.[*].lieuautorise").value(hasItem(DEFAULT_LIEUAUTORISE)))
            .andExpect(jsonPath("$.[*].lieuannulation").value(hasItem(DEFAULT_LIEUANNULATION)));

        // Check, that the count call also returns 1
        restPaiementlotsMockMvc.perform(get("/api/paiementlots/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaiementlotsShouldNotBeFound(String filter) throws Exception {
        restPaiementlotsMockMvc.perform(get("/api/paiementlots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaiementlotsMockMvc.perform(get("/api/paiementlots/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPaiementlots() throws Exception {
        // Get the paiementlots
        restPaiementlotsMockMvc.perform(get("/api/paiementlots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaiementlots() throws Exception {
        // Initialize the database
        paiementlotsService.save(paiementlots);

        int databaseSizeBeforeUpdate = paiementlotsRepository.findAll().size();

        // Update the paiementlots
        Paiementlots updatedPaiementlots = paiementlotsRepository.findById(paiementlots.getId()).get();
        // Disconnect from session so that the updates on updatedPaiementlots are not directly saved in db
        em.detach(updatedPaiementlots);
        updatedPaiementlots
            .idlotpaiement(UPDATED_IDLOTPAIEMENT)
            .codepaiement(UPDATED_CODEPAIEMENT)
            .type(UPDATED_TYPE)
            .seuil(UPDATED_SEUIL)
            .montantavance(UPDATED_MONTANTAVANCE)
            .delaipurge(UPDATED_DELAIPURGE)
            .lieuautorise(UPDATED_LIEUAUTORISE)
            .lieuannulation(UPDATED_LIEUANNULATION);

        restPaiementlotsMockMvc.perform(put("/api/paiementlots").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaiementlots)))
            .andExpect(status().isOk());

        // Validate the Paiementlots in the database
        List<Paiementlots> paiementlotsList = paiementlotsRepository.findAll();
        assertThat(paiementlotsList).hasSize(databaseSizeBeforeUpdate);
        Paiementlots testPaiementlots = paiementlotsList.get(paiementlotsList.size() - 1);
        assertThat(testPaiementlots.getIdlotpaiement()).isEqualTo(UPDATED_IDLOTPAIEMENT);
        assertThat(testPaiementlots.getCodepaiement()).isEqualTo(UPDATED_CODEPAIEMENT);
        assertThat(testPaiementlots.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPaiementlots.getSeuil()).isEqualTo(UPDATED_SEUIL);
        assertThat(testPaiementlots.getMontantavance()).isEqualTo(UPDATED_MONTANTAVANCE);
        assertThat(testPaiementlots.getDelaipurge()).isEqualTo(UPDATED_DELAIPURGE);
        assertThat(testPaiementlots.getLieuautorise()).isEqualTo(UPDATED_LIEUAUTORISE);
        assertThat(testPaiementlots.getLieuannulation()).isEqualTo(UPDATED_LIEUANNULATION);
    }

    @Test
    @Transactional
    public void updateNonExistingPaiementlots() throws Exception {
        int databaseSizeBeforeUpdate = paiementlotsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaiementlotsMockMvc.perform(put("/api/paiementlots").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paiementlots)))
            .andExpect(status().isBadRequest());

        // Validate the Paiementlots in the database
        List<Paiementlots> paiementlotsList = paiementlotsRepository.findAll();
        assertThat(paiementlotsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaiementlots() throws Exception {
        // Initialize the database
        paiementlotsService.save(paiementlots);

        int databaseSizeBeforeDelete = paiementlotsRepository.findAll().size();

        // Delete the paiementlots
        restPaiementlotsMockMvc.perform(delete("/api/paiementlots/{id}", paiementlots.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paiementlots> paiementlotsList = paiementlotsRepository.findAll();
        assertThat(paiementlotsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
