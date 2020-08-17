package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Hypodrome;
import com.mycompany.myapp.repository.HypodromeRepository;
import com.mycompany.myapp.service.HypodromeService;
import com.mycompany.myapp.service.dto.HypodromeCriteria;
import com.mycompany.myapp.service.HypodromeQueryService;

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
 * Integration tests for the {@link HypodromeResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class HypodromeResourceIT {

    private static final Integer DEFAULT_IDHYPODROME = 1;
    private static final Integer UPDATED_IDHYPODROME = 2;
    private static final Integer SMALLER_IDHYPODROME = 1 - 1;

    private static final Integer DEFAULT_CODEHYPODROME = 1;
    private static final Integer UPDATED_CODEHYPODROME = 2;
    private static final Integer SMALLER_CODEHYPODROME = 1 - 1;

    private static final String DEFAULT_ABREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    @Autowired
    private HypodromeRepository hypodromeRepository;

    @Autowired
    private HypodromeService hypodromeService;

    @Autowired
    private HypodromeQueryService hypodromeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHypodromeMockMvc;

    private Hypodrome hypodrome;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hypodrome createEntity(EntityManager em) {
        Hypodrome hypodrome = new Hypodrome()
            .idhypodrome(DEFAULT_IDHYPODROME)
            .codehypodrome(DEFAULT_CODEHYPODROME)
            .abreviation(DEFAULT_ABREVIATION)
            .pays(DEFAULT_PAYS);
        return hypodrome;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hypodrome createUpdatedEntity(EntityManager em) {
        Hypodrome hypodrome = new Hypodrome()
            .idhypodrome(UPDATED_IDHYPODROME)
            .codehypodrome(UPDATED_CODEHYPODROME)
            .abreviation(UPDATED_ABREVIATION)
            .pays(UPDATED_PAYS);
        return hypodrome;
    }

    @BeforeEach
    public void initTest() {
        hypodrome = createEntity(em);
    }

    @Test
    @Transactional
    public void createHypodrome() throws Exception {
        int databaseSizeBeforeCreate = hypodromeRepository.findAll().size();
        // Create the Hypodrome
        restHypodromeMockMvc.perform(post("/api/hypodromes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hypodrome)))
            .andExpect(status().isCreated());

        // Validate the Hypodrome in the database
        List<Hypodrome> hypodromeList = hypodromeRepository.findAll();
        assertThat(hypodromeList).hasSize(databaseSizeBeforeCreate + 1);
        Hypodrome testHypodrome = hypodromeList.get(hypodromeList.size() - 1);
        assertThat(testHypodrome.getIdhypodrome()).isEqualTo(DEFAULT_IDHYPODROME);
        assertThat(testHypodrome.getCodehypodrome()).isEqualTo(DEFAULT_CODEHYPODROME);
        assertThat(testHypodrome.getAbreviation()).isEqualTo(DEFAULT_ABREVIATION);
        assertThat(testHypodrome.getPays()).isEqualTo(DEFAULT_PAYS);
    }

    @Test
    @Transactional
    public void createHypodromeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hypodromeRepository.findAll().size();

        // Create the Hypodrome with an existing ID
        hypodrome.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHypodromeMockMvc.perform(post("/api/hypodromes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hypodrome)))
            .andExpect(status().isBadRequest());

        // Validate the Hypodrome in the database
        List<Hypodrome> hypodromeList = hypodromeRepository.findAll();
        assertThat(hypodromeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdhypodromeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hypodromeRepository.findAll().size();
        // set the field null
        hypodrome.setIdhypodrome(null);

        // Create the Hypodrome, which fails.


        restHypodromeMockMvc.perform(post("/api/hypodromes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hypodrome)))
            .andExpect(status().isBadRequest());

        List<Hypodrome> hypodromeList = hypodromeRepository.findAll();
        assertThat(hypodromeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHypodromes() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList
        restHypodromeMockMvc.perform(get("/api/hypodromes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hypodrome.getId().intValue())))
            .andExpect(jsonPath("$.[*].idhypodrome").value(hasItem(DEFAULT_IDHYPODROME)))
            .andExpect(jsonPath("$.[*].codehypodrome").value(hasItem(DEFAULT_CODEHYPODROME)))
            .andExpect(jsonPath("$.[*].abreviation").value(hasItem(DEFAULT_ABREVIATION)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)));
    }
    
    @Test
    @Transactional
    public void getHypodrome() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get the hypodrome
        restHypodromeMockMvc.perform(get("/api/hypodromes/{id}", hypodrome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hypodrome.getId().intValue()))
            .andExpect(jsonPath("$.idhypodrome").value(DEFAULT_IDHYPODROME))
            .andExpect(jsonPath("$.codehypodrome").value(DEFAULT_CODEHYPODROME))
            .andExpect(jsonPath("$.abreviation").value(DEFAULT_ABREVIATION))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS));
    }


    @Test
    @Transactional
    public void getHypodromesByIdFiltering() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        Long id = hypodrome.getId();

        defaultHypodromeShouldBeFound("id.equals=" + id);
        defaultHypodromeShouldNotBeFound("id.notEquals=" + id);

        defaultHypodromeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHypodromeShouldNotBeFound("id.greaterThan=" + id);

        defaultHypodromeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHypodromeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHypodromesByIdhypodromeIsEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where idhypodrome equals to DEFAULT_IDHYPODROME
        defaultHypodromeShouldBeFound("idhypodrome.equals=" + DEFAULT_IDHYPODROME);

        // Get all the hypodromeList where idhypodrome equals to UPDATED_IDHYPODROME
        defaultHypodromeShouldNotBeFound("idhypodrome.equals=" + UPDATED_IDHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByIdhypodromeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where idhypodrome not equals to DEFAULT_IDHYPODROME
        defaultHypodromeShouldNotBeFound("idhypodrome.notEquals=" + DEFAULT_IDHYPODROME);

        // Get all the hypodromeList where idhypodrome not equals to UPDATED_IDHYPODROME
        defaultHypodromeShouldBeFound("idhypodrome.notEquals=" + UPDATED_IDHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByIdhypodromeIsInShouldWork() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where idhypodrome in DEFAULT_IDHYPODROME or UPDATED_IDHYPODROME
        defaultHypodromeShouldBeFound("idhypodrome.in=" + DEFAULT_IDHYPODROME + "," + UPDATED_IDHYPODROME);

        // Get all the hypodromeList where idhypodrome equals to UPDATED_IDHYPODROME
        defaultHypodromeShouldNotBeFound("idhypodrome.in=" + UPDATED_IDHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByIdhypodromeIsNullOrNotNull() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where idhypodrome is not null
        defaultHypodromeShouldBeFound("idhypodrome.specified=true");

        // Get all the hypodromeList where idhypodrome is null
        defaultHypodromeShouldNotBeFound("idhypodrome.specified=false");
    }

    @Test
    @Transactional
    public void getAllHypodromesByIdhypodromeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where idhypodrome is greater than or equal to DEFAULT_IDHYPODROME
        defaultHypodromeShouldBeFound("idhypodrome.greaterThanOrEqual=" + DEFAULT_IDHYPODROME);

        // Get all the hypodromeList where idhypodrome is greater than or equal to UPDATED_IDHYPODROME
        defaultHypodromeShouldNotBeFound("idhypodrome.greaterThanOrEqual=" + UPDATED_IDHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByIdhypodromeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where idhypodrome is less than or equal to DEFAULT_IDHYPODROME
        defaultHypodromeShouldBeFound("idhypodrome.lessThanOrEqual=" + DEFAULT_IDHYPODROME);

        // Get all the hypodromeList where idhypodrome is less than or equal to SMALLER_IDHYPODROME
        defaultHypodromeShouldNotBeFound("idhypodrome.lessThanOrEqual=" + SMALLER_IDHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByIdhypodromeIsLessThanSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where idhypodrome is less than DEFAULT_IDHYPODROME
        defaultHypodromeShouldNotBeFound("idhypodrome.lessThan=" + DEFAULT_IDHYPODROME);

        // Get all the hypodromeList where idhypodrome is less than UPDATED_IDHYPODROME
        defaultHypodromeShouldBeFound("idhypodrome.lessThan=" + UPDATED_IDHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByIdhypodromeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where idhypodrome is greater than DEFAULT_IDHYPODROME
        defaultHypodromeShouldNotBeFound("idhypodrome.greaterThan=" + DEFAULT_IDHYPODROME);

        // Get all the hypodromeList where idhypodrome is greater than SMALLER_IDHYPODROME
        defaultHypodromeShouldBeFound("idhypodrome.greaterThan=" + SMALLER_IDHYPODROME);
    }


    @Test
    @Transactional
    public void getAllHypodromesByCodehypodromeIsEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where codehypodrome equals to DEFAULT_CODEHYPODROME
        defaultHypodromeShouldBeFound("codehypodrome.equals=" + DEFAULT_CODEHYPODROME);

        // Get all the hypodromeList where codehypodrome equals to UPDATED_CODEHYPODROME
        defaultHypodromeShouldNotBeFound("codehypodrome.equals=" + UPDATED_CODEHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByCodehypodromeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where codehypodrome not equals to DEFAULT_CODEHYPODROME
        defaultHypodromeShouldNotBeFound("codehypodrome.notEquals=" + DEFAULT_CODEHYPODROME);

        // Get all the hypodromeList where codehypodrome not equals to UPDATED_CODEHYPODROME
        defaultHypodromeShouldBeFound("codehypodrome.notEquals=" + UPDATED_CODEHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByCodehypodromeIsInShouldWork() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where codehypodrome in DEFAULT_CODEHYPODROME or UPDATED_CODEHYPODROME
        defaultHypodromeShouldBeFound("codehypodrome.in=" + DEFAULT_CODEHYPODROME + "," + UPDATED_CODEHYPODROME);

        // Get all the hypodromeList where codehypodrome equals to UPDATED_CODEHYPODROME
        defaultHypodromeShouldNotBeFound("codehypodrome.in=" + UPDATED_CODEHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByCodehypodromeIsNullOrNotNull() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where codehypodrome is not null
        defaultHypodromeShouldBeFound("codehypodrome.specified=true");

        // Get all the hypodromeList where codehypodrome is null
        defaultHypodromeShouldNotBeFound("codehypodrome.specified=false");
    }

    @Test
    @Transactional
    public void getAllHypodromesByCodehypodromeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where codehypodrome is greater than or equal to DEFAULT_CODEHYPODROME
        defaultHypodromeShouldBeFound("codehypodrome.greaterThanOrEqual=" + DEFAULT_CODEHYPODROME);

        // Get all the hypodromeList where codehypodrome is greater than or equal to UPDATED_CODEHYPODROME
        defaultHypodromeShouldNotBeFound("codehypodrome.greaterThanOrEqual=" + UPDATED_CODEHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByCodehypodromeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where codehypodrome is less than or equal to DEFAULT_CODEHYPODROME
        defaultHypodromeShouldBeFound("codehypodrome.lessThanOrEqual=" + DEFAULT_CODEHYPODROME);

        // Get all the hypodromeList where codehypodrome is less than or equal to SMALLER_CODEHYPODROME
        defaultHypodromeShouldNotBeFound("codehypodrome.lessThanOrEqual=" + SMALLER_CODEHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByCodehypodromeIsLessThanSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where codehypodrome is less than DEFAULT_CODEHYPODROME
        defaultHypodromeShouldNotBeFound("codehypodrome.lessThan=" + DEFAULT_CODEHYPODROME);

        // Get all the hypodromeList where codehypodrome is less than UPDATED_CODEHYPODROME
        defaultHypodromeShouldBeFound("codehypodrome.lessThan=" + UPDATED_CODEHYPODROME);
    }

    @Test
    @Transactional
    public void getAllHypodromesByCodehypodromeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where codehypodrome is greater than DEFAULT_CODEHYPODROME
        defaultHypodromeShouldNotBeFound("codehypodrome.greaterThan=" + DEFAULT_CODEHYPODROME);

        // Get all the hypodromeList where codehypodrome is greater than SMALLER_CODEHYPODROME
        defaultHypodromeShouldBeFound("codehypodrome.greaterThan=" + SMALLER_CODEHYPODROME);
    }


    @Test
    @Transactional
    public void getAllHypodromesByAbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where abreviation equals to DEFAULT_ABREVIATION
        defaultHypodromeShouldBeFound("abreviation.equals=" + DEFAULT_ABREVIATION);

        // Get all the hypodromeList where abreviation equals to UPDATED_ABREVIATION
        defaultHypodromeShouldNotBeFound("abreviation.equals=" + UPDATED_ABREVIATION);
    }

    @Test
    @Transactional
    public void getAllHypodromesByAbreviationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where abreviation not equals to DEFAULT_ABREVIATION
        defaultHypodromeShouldNotBeFound("abreviation.notEquals=" + DEFAULT_ABREVIATION);

        // Get all the hypodromeList where abreviation not equals to UPDATED_ABREVIATION
        defaultHypodromeShouldBeFound("abreviation.notEquals=" + UPDATED_ABREVIATION);
    }

    @Test
    @Transactional
    public void getAllHypodromesByAbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where abreviation in DEFAULT_ABREVIATION or UPDATED_ABREVIATION
        defaultHypodromeShouldBeFound("abreviation.in=" + DEFAULT_ABREVIATION + "," + UPDATED_ABREVIATION);

        // Get all the hypodromeList where abreviation equals to UPDATED_ABREVIATION
        defaultHypodromeShouldNotBeFound("abreviation.in=" + UPDATED_ABREVIATION);
    }

    @Test
    @Transactional
    public void getAllHypodromesByAbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where abreviation is not null
        defaultHypodromeShouldBeFound("abreviation.specified=true");

        // Get all the hypodromeList where abreviation is null
        defaultHypodromeShouldNotBeFound("abreviation.specified=false");
    }
                @Test
    @Transactional
    public void getAllHypodromesByAbreviationContainsSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where abreviation contains DEFAULT_ABREVIATION
        defaultHypodromeShouldBeFound("abreviation.contains=" + DEFAULT_ABREVIATION);

        // Get all the hypodromeList where abreviation contains UPDATED_ABREVIATION
        defaultHypodromeShouldNotBeFound("abreviation.contains=" + UPDATED_ABREVIATION);
    }

    @Test
    @Transactional
    public void getAllHypodromesByAbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where abreviation does not contain DEFAULT_ABREVIATION
        defaultHypodromeShouldNotBeFound("abreviation.doesNotContain=" + DEFAULT_ABREVIATION);

        // Get all the hypodromeList where abreviation does not contain UPDATED_ABREVIATION
        defaultHypodromeShouldBeFound("abreviation.doesNotContain=" + UPDATED_ABREVIATION);
    }


    @Test
    @Transactional
    public void getAllHypodromesByPaysIsEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where pays equals to DEFAULT_PAYS
        defaultHypodromeShouldBeFound("pays.equals=" + DEFAULT_PAYS);

        // Get all the hypodromeList where pays equals to UPDATED_PAYS
        defaultHypodromeShouldNotBeFound("pays.equals=" + UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void getAllHypodromesByPaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where pays not equals to DEFAULT_PAYS
        defaultHypodromeShouldNotBeFound("pays.notEquals=" + DEFAULT_PAYS);

        // Get all the hypodromeList where pays not equals to UPDATED_PAYS
        defaultHypodromeShouldBeFound("pays.notEquals=" + UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void getAllHypodromesByPaysIsInShouldWork() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where pays in DEFAULT_PAYS or UPDATED_PAYS
        defaultHypodromeShouldBeFound("pays.in=" + DEFAULT_PAYS + "," + UPDATED_PAYS);

        // Get all the hypodromeList where pays equals to UPDATED_PAYS
        defaultHypodromeShouldNotBeFound("pays.in=" + UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void getAllHypodromesByPaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where pays is not null
        defaultHypodromeShouldBeFound("pays.specified=true");

        // Get all the hypodromeList where pays is null
        defaultHypodromeShouldNotBeFound("pays.specified=false");
    }
                @Test
    @Transactional
    public void getAllHypodromesByPaysContainsSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where pays contains DEFAULT_PAYS
        defaultHypodromeShouldBeFound("pays.contains=" + DEFAULT_PAYS);

        // Get all the hypodromeList where pays contains UPDATED_PAYS
        defaultHypodromeShouldNotBeFound("pays.contains=" + UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void getAllHypodromesByPaysNotContainsSomething() throws Exception {
        // Initialize the database
        hypodromeRepository.saveAndFlush(hypodrome);

        // Get all the hypodromeList where pays does not contain DEFAULT_PAYS
        defaultHypodromeShouldNotBeFound("pays.doesNotContain=" + DEFAULT_PAYS);

        // Get all the hypodromeList where pays does not contain UPDATED_PAYS
        defaultHypodromeShouldBeFound("pays.doesNotContain=" + UPDATED_PAYS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHypodromeShouldBeFound(String filter) throws Exception {
        restHypodromeMockMvc.perform(get("/api/hypodromes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hypodrome.getId().intValue())))
            .andExpect(jsonPath("$.[*].idhypodrome").value(hasItem(DEFAULT_IDHYPODROME)))
            .andExpect(jsonPath("$.[*].codehypodrome").value(hasItem(DEFAULT_CODEHYPODROME)))
            .andExpect(jsonPath("$.[*].abreviation").value(hasItem(DEFAULT_ABREVIATION)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)));

        // Check, that the count call also returns 1
        restHypodromeMockMvc.perform(get("/api/hypodromes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHypodromeShouldNotBeFound(String filter) throws Exception {
        restHypodromeMockMvc.perform(get("/api/hypodromes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHypodromeMockMvc.perform(get("/api/hypodromes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingHypodrome() throws Exception {
        // Get the hypodrome
        restHypodromeMockMvc.perform(get("/api/hypodromes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHypodrome() throws Exception {
        // Initialize the database
        hypodromeService.save(hypodrome);

        int databaseSizeBeforeUpdate = hypodromeRepository.findAll().size();

        // Update the hypodrome
        Hypodrome updatedHypodrome = hypodromeRepository.findById(hypodrome.getId()).get();
        // Disconnect from session so that the updates on updatedHypodrome are not directly saved in db
        em.detach(updatedHypodrome);
        updatedHypodrome
            .idhypodrome(UPDATED_IDHYPODROME)
            .codehypodrome(UPDATED_CODEHYPODROME)
            .abreviation(UPDATED_ABREVIATION)
            .pays(UPDATED_PAYS);

        restHypodromeMockMvc.perform(put("/api/hypodromes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHypodrome)))
            .andExpect(status().isOk());

        // Validate the Hypodrome in the database
        List<Hypodrome> hypodromeList = hypodromeRepository.findAll();
        assertThat(hypodromeList).hasSize(databaseSizeBeforeUpdate);
        Hypodrome testHypodrome = hypodromeList.get(hypodromeList.size() - 1);
        assertThat(testHypodrome.getIdhypodrome()).isEqualTo(UPDATED_IDHYPODROME);
        assertThat(testHypodrome.getCodehypodrome()).isEqualTo(UPDATED_CODEHYPODROME);
        assertThat(testHypodrome.getAbreviation()).isEqualTo(UPDATED_ABREVIATION);
        assertThat(testHypodrome.getPays()).isEqualTo(UPDATED_PAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingHypodrome() throws Exception {
        int databaseSizeBeforeUpdate = hypodromeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHypodromeMockMvc.perform(put("/api/hypodromes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hypodrome)))
            .andExpect(status().isBadRequest());

        // Validate the Hypodrome in the database
        List<Hypodrome> hypodromeList = hypodromeRepository.findAll();
        assertThat(hypodromeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHypodrome() throws Exception {
        // Initialize the database
        hypodromeService.save(hypodrome);

        int databaseSizeBeforeDelete = hypodromeRepository.findAll().size();

        // Delete the hypodrome
        restHypodromeMockMvc.perform(delete("/api/hypodromes/{id}", hypodrome.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hypodrome> hypodromeList = hypodromeRepository.findAll();
        assertThat(hypodromeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
