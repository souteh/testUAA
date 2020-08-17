package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Delaiannulation;
import com.mycompany.myapp.repository.DelaiannulationRepository;
import com.mycompany.myapp.service.DelaiannulationService;
import com.mycompany.myapp.service.dto.DelaiannulationCriteria;
import com.mycompany.myapp.service.DelaiannulationQueryService;

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
 * Integration tests for the {@link DelaiannulationResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DelaiannulationResourceIT {

    private static final Integer DEFAULT_IDDELAIANNULATION = 1;
    private static final Integer UPDATED_IDDELAIANNULATION = 2;
    private static final Integer SMALLER_IDDELAIANNULATION = 1 - 1;

    private static final String DEFAULT_CODEDELAIANNULATION = "AAAAAAAAAA";
    private static final String UPDATED_CODEDELAIANNULATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATIONDELAIANNULATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATIONDELAIANNULATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALEURDELAIANNULATION = 1;
    private static final Integer UPDATED_VALEURDELAIANNULATION = 2;
    private static final Integer SMALLER_VALEURDELAIANNULATION = 1 - 1;

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    @Autowired
    private DelaiannulationRepository delaiannulationRepository;

    @Autowired
    private DelaiannulationService delaiannulationService;

    @Autowired
    private DelaiannulationQueryService delaiannulationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDelaiannulationMockMvc;

    private Delaiannulation delaiannulation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delaiannulation createEntity(EntityManager em) {
        Delaiannulation delaiannulation = new Delaiannulation()
            .iddelaiannulation(DEFAULT_IDDELAIANNULATION)
            .codedelaiannulation(DEFAULT_CODEDELAIANNULATION)
            .designationdelaiannulation(DEFAULT_DESIGNATIONDELAIANNULATION)
            .valeurdelaiannulation(DEFAULT_VALEURDELAIANNULATION)
            .statut(DEFAULT_STATUT);
        return delaiannulation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delaiannulation createUpdatedEntity(EntityManager em) {
        Delaiannulation delaiannulation = new Delaiannulation()
            .iddelaiannulation(UPDATED_IDDELAIANNULATION)
            .codedelaiannulation(UPDATED_CODEDELAIANNULATION)
            .designationdelaiannulation(UPDATED_DESIGNATIONDELAIANNULATION)
            .valeurdelaiannulation(UPDATED_VALEURDELAIANNULATION)
            .statut(UPDATED_STATUT);
        return delaiannulation;
    }

    @BeforeEach
    public void initTest() {
        delaiannulation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDelaiannulation() throws Exception {
        int databaseSizeBeforeCreate = delaiannulationRepository.findAll().size();
        // Create the Delaiannulation
        restDelaiannulationMockMvc.perform(post("/api/delaiannulations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(delaiannulation)))
            .andExpect(status().isCreated());

        // Validate the Delaiannulation in the database
        List<Delaiannulation> delaiannulationList = delaiannulationRepository.findAll();
        assertThat(delaiannulationList).hasSize(databaseSizeBeforeCreate + 1);
        Delaiannulation testDelaiannulation = delaiannulationList.get(delaiannulationList.size() - 1);
        assertThat(testDelaiannulation.getIddelaiannulation()).isEqualTo(DEFAULT_IDDELAIANNULATION);
        assertThat(testDelaiannulation.getCodedelaiannulation()).isEqualTo(DEFAULT_CODEDELAIANNULATION);
        assertThat(testDelaiannulation.getDesignationdelaiannulation()).isEqualTo(DEFAULT_DESIGNATIONDELAIANNULATION);
        assertThat(testDelaiannulation.getValeurdelaiannulation()).isEqualTo(DEFAULT_VALEURDELAIANNULATION);
        assertThat(testDelaiannulation.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    public void createDelaiannulationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = delaiannulationRepository.findAll().size();

        // Create the Delaiannulation with an existing ID
        delaiannulation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDelaiannulationMockMvc.perform(post("/api/delaiannulations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(delaiannulation)))
            .andExpect(status().isBadRequest());

        // Validate the Delaiannulation in the database
        List<Delaiannulation> delaiannulationList = delaiannulationRepository.findAll();
        assertThat(delaiannulationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIddelaiannulationIsRequired() throws Exception {
        int databaseSizeBeforeTest = delaiannulationRepository.findAll().size();
        // set the field null
        delaiannulation.setIddelaiannulation(null);

        // Create the Delaiannulation, which fails.


        restDelaiannulationMockMvc.perform(post("/api/delaiannulations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(delaiannulation)))
            .andExpect(status().isBadRequest());

        List<Delaiannulation> delaiannulationList = delaiannulationRepository.findAll();
        assertThat(delaiannulationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDelaiannulations() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList
        restDelaiannulationMockMvc.perform(get("/api/delaiannulations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delaiannulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].iddelaiannulation").value(hasItem(DEFAULT_IDDELAIANNULATION)))
            .andExpect(jsonPath("$.[*].codedelaiannulation").value(hasItem(DEFAULT_CODEDELAIANNULATION)))
            .andExpect(jsonPath("$.[*].designationdelaiannulation").value(hasItem(DEFAULT_DESIGNATIONDELAIANNULATION)))
            .andExpect(jsonPath("$.[*].valeurdelaiannulation").value(hasItem(DEFAULT_VALEURDELAIANNULATION)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));
    }
    
    @Test
    @Transactional
    public void getDelaiannulation() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get the delaiannulation
        restDelaiannulationMockMvc.perform(get("/api/delaiannulations/{id}", delaiannulation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(delaiannulation.getId().intValue()))
            .andExpect(jsonPath("$.iddelaiannulation").value(DEFAULT_IDDELAIANNULATION))
            .andExpect(jsonPath("$.codedelaiannulation").value(DEFAULT_CODEDELAIANNULATION))
            .andExpect(jsonPath("$.designationdelaiannulation").value(DEFAULT_DESIGNATIONDELAIANNULATION))
            .andExpect(jsonPath("$.valeurdelaiannulation").value(DEFAULT_VALEURDELAIANNULATION))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT));
    }


    @Test
    @Transactional
    public void getDelaiannulationsByIdFiltering() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        Long id = delaiannulation.getId();

        defaultDelaiannulationShouldBeFound("id.equals=" + id);
        defaultDelaiannulationShouldNotBeFound("id.notEquals=" + id);

        defaultDelaiannulationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDelaiannulationShouldNotBeFound("id.greaterThan=" + id);

        defaultDelaiannulationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDelaiannulationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDelaiannulationsByIddelaiannulationIsEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where iddelaiannulation equals to DEFAULT_IDDELAIANNULATION
        defaultDelaiannulationShouldBeFound("iddelaiannulation.equals=" + DEFAULT_IDDELAIANNULATION);

        // Get all the delaiannulationList where iddelaiannulation equals to UPDATED_IDDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("iddelaiannulation.equals=" + UPDATED_IDDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByIddelaiannulationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where iddelaiannulation not equals to DEFAULT_IDDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("iddelaiannulation.notEquals=" + DEFAULT_IDDELAIANNULATION);

        // Get all the delaiannulationList where iddelaiannulation not equals to UPDATED_IDDELAIANNULATION
        defaultDelaiannulationShouldBeFound("iddelaiannulation.notEquals=" + UPDATED_IDDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByIddelaiannulationIsInShouldWork() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where iddelaiannulation in DEFAULT_IDDELAIANNULATION or UPDATED_IDDELAIANNULATION
        defaultDelaiannulationShouldBeFound("iddelaiannulation.in=" + DEFAULT_IDDELAIANNULATION + "," + UPDATED_IDDELAIANNULATION);

        // Get all the delaiannulationList where iddelaiannulation equals to UPDATED_IDDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("iddelaiannulation.in=" + UPDATED_IDDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByIddelaiannulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where iddelaiannulation is not null
        defaultDelaiannulationShouldBeFound("iddelaiannulation.specified=true");

        // Get all the delaiannulationList where iddelaiannulation is null
        defaultDelaiannulationShouldNotBeFound("iddelaiannulation.specified=false");
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByIddelaiannulationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where iddelaiannulation is greater than or equal to DEFAULT_IDDELAIANNULATION
        defaultDelaiannulationShouldBeFound("iddelaiannulation.greaterThanOrEqual=" + DEFAULT_IDDELAIANNULATION);

        // Get all the delaiannulationList where iddelaiannulation is greater than or equal to UPDATED_IDDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("iddelaiannulation.greaterThanOrEqual=" + UPDATED_IDDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByIddelaiannulationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where iddelaiannulation is less than or equal to DEFAULT_IDDELAIANNULATION
        defaultDelaiannulationShouldBeFound("iddelaiannulation.lessThanOrEqual=" + DEFAULT_IDDELAIANNULATION);

        // Get all the delaiannulationList where iddelaiannulation is less than or equal to SMALLER_IDDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("iddelaiannulation.lessThanOrEqual=" + SMALLER_IDDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByIddelaiannulationIsLessThanSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where iddelaiannulation is less than DEFAULT_IDDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("iddelaiannulation.lessThan=" + DEFAULT_IDDELAIANNULATION);

        // Get all the delaiannulationList where iddelaiannulation is less than UPDATED_IDDELAIANNULATION
        defaultDelaiannulationShouldBeFound("iddelaiannulation.lessThan=" + UPDATED_IDDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByIddelaiannulationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where iddelaiannulation is greater than DEFAULT_IDDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("iddelaiannulation.greaterThan=" + DEFAULT_IDDELAIANNULATION);

        // Get all the delaiannulationList where iddelaiannulation is greater than SMALLER_IDDELAIANNULATION
        defaultDelaiannulationShouldBeFound("iddelaiannulation.greaterThan=" + SMALLER_IDDELAIANNULATION);
    }


    @Test
    @Transactional
    public void getAllDelaiannulationsByCodedelaiannulationIsEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where codedelaiannulation equals to DEFAULT_CODEDELAIANNULATION
        defaultDelaiannulationShouldBeFound("codedelaiannulation.equals=" + DEFAULT_CODEDELAIANNULATION);

        // Get all the delaiannulationList where codedelaiannulation equals to UPDATED_CODEDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("codedelaiannulation.equals=" + UPDATED_CODEDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByCodedelaiannulationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where codedelaiannulation not equals to DEFAULT_CODEDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("codedelaiannulation.notEquals=" + DEFAULT_CODEDELAIANNULATION);

        // Get all the delaiannulationList where codedelaiannulation not equals to UPDATED_CODEDELAIANNULATION
        defaultDelaiannulationShouldBeFound("codedelaiannulation.notEquals=" + UPDATED_CODEDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByCodedelaiannulationIsInShouldWork() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where codedelaiannulation in DEFAULT_CODEDELAIANNULATION or UPDATED_CODEDELAIANNULATION
        defaultDelaiannulationShouldBeFound("codedelaiannulation.in=" + DEFAULT_CODEDELAIANNULATION + "," + UPDATED_CODEDELAIANNULATION);

        // Get all the delaiannulationList where codedelaiannulation equals to UPDATED_CODEDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("codedelaiannulation.in=" + UPDATED_CODEDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByCodedelaiannulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where codedelaiannulation is not null
        defaultDelaiannulationShouldBeFound("codedelaiannulation.specified=true");

        // Get all the delaiannulationList where codedelaiannulation is null
        defaultDelaiannulationShouldNotBeFound("codedelaiannulation.specified=false");
    }
                @Test
    @Transactional
    public void getAllDelaiannulationsByCodedelaiannulationContainsSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where codedelaiannulation contains DEFAULT_CODEDELAIANNULATION
        defaultDelaiannulationShouldBeFound("codedelaiannulation.contains=" + DEFAULT_CODEDELAIANNULATION);

        // Get all the delaiannulationList where codedelaiannulation contains UPDATED_CODEDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("codedelaiannulation.contains=" + UPDATED_CODEDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByCodedelaiannulationNotContainsSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where codedelaiannulation does not contain DEFAULT_CODEDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("codedelaiannulation.doesNotContain=" + DEFAULT_CODEDELAIANNULATION);

        // Get all the delaiannulationList where codedelaiannulation does not contain UPDATED_CODEDELAIANNULATION
        defaultDelaiannulationShouldBeFound("codedelaiannulation.doesNotContain=" + UPDATED_CODEDELAIANNULATION);
    }


    @Test
    @Transactional
    public void getAllDelaiannulationsByDesignationdelaiannulationIsEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where designationdelaiannulation equals to DEFAULT_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldBeFound("designationdelaiannulation.equals=" + DEFAULT_DESIGNATIONDELAIANNULATION);

        // Get all the delaiannulationList where designationdelaiannulation equals to UPDATED_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("designationdelaiannulation.equals=" + UPDATED_DESIGNATIONDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByDesignationdelaiannulationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where designationdelaiannulation not equals to DEFAULT_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("designationdelaiannulation.notEquals=" + DEFAULT_DESIGNATIONDELAIANNULATION);

        // Get all the delaiannulationList where designationdelaiannulation not equals to UPDATED_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldBeFound("designationdelaiannulation.notEquals=" + UPDATED_DESIGNATIONDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByDesignationdelaiannulationIsInShouldWork() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where designationdelaiannulation in DEFAULT_DESIGNATIONDELAIANNULATION or UPDATED_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldBeFound("designationdelaiannulation.in=" + DEFAULT_DESIGNATIONDELAIANNULATION + "," + UPDATED_DESIGNATIONDELAIANNULATION);

        // Get all the delaiannulationList where designationdelaiannulation equals to UPDATED_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("designationdelaiannulation.in=" + UPDATED_DESIGNATIONDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByDesignationdelaiannulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where designationdelaiannulation is not null
        defaultDelaiannulationShouldBeFound("designationdelaiannulation.specified=true");

        // Get all the delaiannulationList where designationdelaiannulation is null
        defaultDelaiannulationShouldNotBeFound("designationdelaiannulation.specified=false");
    }
                @Test
    @Transactional
    public void getAllDelaiannulationsByDesignationdelaiannulationContainsSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where designationdelaiannulation contains DEFAULT_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldBeFound("designationdelaiannulation.contains=" + DEFAULT_DESIGNATIONDELAIANNULATION);

        // Get all the delaiannulationList where designationdelaiannulation contains UPDATED_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("designationdelaiannulation.contains=" + UPDATED_DESIGNATIONDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByDesignationdelaiannulationNotContainsSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where designationdelaiannulation does not contain DEFAULT_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("designationdelaiannulation.doesNotContain=" + DEFAULT_DESIGNATIONDELAIANNULATION);

        // Get all the delaiannulationList where designationdelaiannulation does not contain UPDATED_DESIGNATIONDELAIANNULATION
        defaultDelaiannulationShouldBeFound("designationdelaiannulation.doesNotContain=" + UPDATED_DESIGNATIONDELAIANNULATION);
    }


    @Test
    @Transactional
    public void getAllDelaiannulationsByValeurdelaiannulationIsEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where valeurdelaiannulation equals to DEFAULT_VALEURDELAIANNULATION
        defaultDelaiannulationShouldBeFound("valeurdelaiannulation.equals=" + DEFAULT_VALEURDELAIANNULATION);

        // Get all the delaiannulationList where valeurdelaiannulation equals to UPDATED_VALEURDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("valeurdelaiannulation.equals=" + UPDATED_VALEURDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByValeurdelaiannulationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where valeurdelaiannulation not equals to DEFAULT_VALEURDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("valeurdelaiannulation.notEquals=" + DEFAULT_VALEURDELAIANNULATION);

        // Get all the delaiannulationList where valeurdelaiannulation not equals to UPDATED_VALEURDELAIANNULATION
        defaultDelaiannulationShouldBeFound("valeurdelaiannulation.notEquals=" + UPDATED_VALEURDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByValeurdelaiannulationIsInShouldWork() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where valeurdelaiannulation in DEFAULT_VALEURDELAIANNULATION or UPDATED_VALEURDELAIANNULATION
        defaultDelaiannulationShouldBeFound("valeurdelaiannulation.in=" + DEFAULT_VALEURDELAIANNULATION + "," + UPDATED_VALEURDELAIANNULATION);

        // Get all the delaiannulationList where valeurdelaiannulation equals to UPDATED_VALEURDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("valeurdelaiannulation.in=" + UPDATED_VALEURDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByValeurdelaiannulationIsNullOrNotNull() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where valeurdelaiannulation is not null
        defaultDelaiannulationShouldBeFound("valeurdelaiannulation.specified=true");

        // Get all the delaiannulationList where valeurdelaiannulation is null
        defaultDelaiannulationShouldNotBeFound("valeurdelaiannulation.specified=false");
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByValeurdelaiannulationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where valeurdelaiannulation is greater than or equal to DEFAULT_VALEURDELAIANNULATION
        defaultDelaiannulationShouldBeFound("valeurdelaiannulation.greaterThanOrEqual=" + DEFAULT_VALEURDELAIANNULATION);

        // Get all the delaiannulationList where valeurdelaiannulation is greater than or equal to UPDATED_VALEURDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("valeurdelaiannulation.greaterThanOrEqual=" + UPDATED_VALEURDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByValeurdelaiannulationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where valeurdelaiannulation is less than or equal to DEFAULT_VALEURDELAIANNULATION
        defaultDelaiannulationShouldBeFound("valeurdelaiannulation.lessThanOrEqual=" + DEFAULT_VALEURDELAIANNULATION);

        // Get all the delaiannulationList where valeurdelaiannulation is less than or equal to SMALLER_VALEURDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("valeurdelaiannulation.lessThanOrEqual=" + SMALLER_VALEURDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByValeurdelaiannulationIsLessThanSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where valeurdelaiannulation is less than DEFAULT_VALEURDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("valeurdelaiannulation.lessThan=" + DEFAULT_VALEURDELAIANNULATION);

        // Get all the delaiannulationList where valeurdelaiannulation is less than UPDATED_VALEURDELAIANNULATION
        defaultDelaiannulationShouldBeFound("valeurdelaiannulation.lessThan=" + UPDATED_VALEURDELAIANNULATION);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByValeurdelaiannulationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where valeurdelaiannulation is greater than DEFAULT_VALEURDELAIANNULATION
        defaultDelaiannulationShouldNotBeFound("valeurdelaiannulation.greaterThan=" + DEFAULT_VALEURDELAIANNULATION);

        // Get all the delaiannulationList where valeurdelaiannulation is greater than SMALLER_VALEURDELAIANNULATION
        defaultDelaiannulationShouldBeFound("valeurdelaiannulation.greaterThan=" + SMALLER_VALEURDELAIANNULATION);
    }


    @Test
    @Transactional
    public void getAllDelaiannulationsByStatutIsEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where statut equals to DEFAULT_STATUT
        defaultDelaiannulationShouldBeFound("statut.equals=" + DEFAULT_STATUT);

        // Get all the delaiannulationList where statut equals to UPDATED_STATUT
        defaultDelaiannulationShouldNotBeFound("statut.equals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByStatutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where statut not equals to DEFAULT_STATUT
        defaultDelaiannulationShouldNotBeFound("statut.notEquals=" + DEFAULT_STATUT);

        // Get all the delaiannulationList where statut not equals to UPDATED_STATUT
        defaultDelaiannulationShouldBeFound("statut.notEquals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByStatutIsInShouldWork() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where statut in DEFAULT_STATUT or UPDATED_STATUT
        defaultDelaiannulationShouldBeFound("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT);

        // Get all the delaiannulationList where statut equals to UPDATED_STATUT
        defaultDelaiannulationShouldNotBeFound("statut.in=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByStatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where statut is not null
        defaultDelaiannulationShouldBeFound("statut.specified=true");

        // Get all the delaiannulationList where statut is null
        defaultDelaiannulationShouldNotBeFound("statut.specified=false");
    }
                @Test
    @Transactional
    public void getAllDelaiannulationsByStatutContainsSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where statut contains DEFAULT_STATUT
        defaultDelaiannulationShouldBeFound("statut.contains=" + DEFAULT_STATUT);

        // Get all the delaiannulationList where statut contains UPDATED_STATUT
        defaultDelaiannulationShouldNotBeFound("statut.contains=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllDelaiannulationsByStatutNotContainsSomething() throws Exception {
        // Initialize the database
        delaiannulationRepository.saveAndFlush(delaiannulation);

        // Get all the delaiannulationList where statut does not contain DEFAULT_STATUT
        defaultDelaiannulationShouldNotBeFound("statut.doesNotContain=" + DEFAULT_STATUT);

        // Get all the delaiannulationList where statut does not contain UPDATED_STATUT
        defaultDelaiannulationShouldBeFound("statut.doesNotContain=" + UPDATED_STATUT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDelaiannulationShouldBeFound(String filter) throws Exception {
        restDelaiannulationMockMvc.perform(get("/api/delaiannulations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delaiannulation.getId().intValue())))
            .andExpect(jsonPath("$.[*].iddelaiannulation").value(hasItem(DEFAULT_IDDELAIANNULATION)))
            .andExpect(jsonPath("$.[*].codedelaiannulation").value(hasItem(DEFAULT_CODEDELAIANNULATION)))
            .andExpect(jsonPath("$.[*].designationdelaiannulation").value(hasItem(DEFAULT_DESIGNATIONDELAIANNULATION)))
            .andExpect(jsonPath("$.[*].valeurdelaiannulation").value(hasItem(DEFAULT_VALEURDELAIANNULATION)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));

        // Check, that the count call also returns 1
        restDelaiannulationMockMvc.perform(get("/api/delaiannulations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDelaiannulationShouldNotBeFound(String filter) throws Exception {
        restDelaiannulationMockMvc.perform(get("/api/delaiannulations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDelaiannulationMockMvc.perform(get("/api/delaiannulations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDelaiannulation() throws Exception {
        // Get the delaiannulation
        restDelaiannulationMockMvc.perform(get("/api/delaiannulations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDelaiannulation() throws Exception {
        // Initialize the database
        delaiannulationService.save(delaiannulation);

        int databaseSizeBeforeUpdate = delaiannulationRepository.findAll().size();

        // Update the delaiannulation
        Delaiannulation updatedDelaiannulation = delaiannulationRepository.findById(delaiannulation.getId()).get();
        // Disconnect from session so that the updates on updatedDelaiannulation are not directly saved in db
        em.detach(updatedDelaiannulation);
        updatedDelaiannulation
            .iddelaiannulation(UPDATED_IDDELAIANNULATION)
            .codedelaiannulation(UPDATED_CODEDELAIANNULATION)
            .designationdelaiannulation(UPDATED_DESIGNATIONDELAIANNULATION)
            .valeurdelaiannulation(UPDATED_VALEURDELAIANNULATION)
            .statut(UPDATED_STATUT);

        restDelaiannulationMockMvc.perform(put("/api/delaiannulations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDelaiannulation)))
            .andExpect(status().isOk());

        // Validate the Delaiannulation in the database
        List<Delaiannulation> delaiannulationList = delaiannulationRepository.findAll();
        assertThat(delaiannulationList).hasSize(databaseSizeBeforeUpdate);
        Delaiannulation testDelaiannulation = delaiannulationList.get(delaiannulationList.size() - 1);
        assertThat(testDelaiannulation.getIddelaiannulation()).isEqualTo(UPDATED_IDDELAIANNULATION);
        assertThat(testDelaiannulation.getCodedelaiannulation()).isEqualTo(UPDATED_CODEDELAIANNULATION);
        assertThat(testDelaiannulation.getDesignationdelaiannulation()).isEqualTo(UPDATED_DESIGNATIONDELAIANNULATION);
        assertThat(testDelaiannulation.getValeurdelaiannulation()).isEqualTo(UPDATED_VALEURDELAIANNULATION);
        assertThat(testDelaiannulation.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingDelaiannulation() throws Exception {
        int databaseSizeBeforeUpdate = delaiannulationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDelaiannulationMockMvc.perform(put("/api/delaiannulations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(delaiannulation)))
            .andExpect(status().isBadRequest());

        // Validate the Delaiannulation in the database
        List<Delaiannulation> delaiannulationList = delaiannulationRepository.findAll();
        assertThat(delaiannulationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDelaiannulation() throws Exception {
        // Initialize the database
        delaiannulationService.save(delaiannulation);

        int databaseSizeBeforeDelete = delaiannulationRepository.findAll().size();

        // Delete the delaiannulation
        restDelaiannulationMockMvc.perform(delete("/api/delaiannulations/{id}", delaiannulation.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Delaiannulation> delaiannulationList = delaiannulationRepository.findAll();
        assertThat(delaiannulationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
