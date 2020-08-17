package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Alertemail;
import com.mycompany.myapp.repository.AlertemailRepository;
import com.mycompany.myapp.service.AlertemailService;
import com.mycompany.myapp.service.dto.AlertemailCriteria;
import com.mycompany.myapp.service.AlertemailQueryService;

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
 * Integration tests for the {@link AlertemailResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AlertemailResourceIT {

    private static final Integer DEFAULT_IDALERTEMAIL = 1;
    private static final Integer UPDATED_IDALERTEMAIL = 2;
    private static final Integer SMALLER_IDALERTEMAIL = 1 - 1;

    private static final String DEFAULT_CODEALERTEMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CODEALERTEMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEALERTEMAIL = "AAAAAAAAAA";
    private static final String UPDATED_TYPEALERTEMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OBJETALERTEMAIL = "AAAAAAAAAA";
    private static final String UPDATED_OBJETALERTEMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSEMAILDIFFUSION = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSEMAILDIFFUSION = "BBBBBBBBBB";

    @Autowired
    private AlertemailRepository alertemailRepository;

    @Autowired
    private AlertemailService alertemailService;

    @Autowired
    private AlertemailQueryService alertemailQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertemailMockMvc;

    private Alertemail alertemail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alertemail createEntity(EntityManager em) {
        Alertemail alertemail = new Alertemail()
            .idalertemail(DEFAULT_IDALERTEMAIL)
            .codealertemail(DEFAULT_CODEALERTEMAIL)
            .typealertemail(DEFAULT_TYPEALERTEMAIL)
            .objetalertemail(DEFAULT_OBJETALERTEMAIL)
            .adressemaildiffusion(DEFAULT_ADRESSEMAILDIFFUSION);
        return alertemail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alertemail createUpdatedEntity(EntityManager em) {
        Alertemail alertemail = new Alertemail()
            .idalertemail(UPDATED_IDALERTEMAIL)
            .codealertemail(UPDATED_CODEALERTEMAIL)
            .typealertemail(UPDATED_TYPEALERTEMAIL)
            .objetalertemail(UPDATED_OBJETALERTEMAIL)
            .adressemaildiffusion(UPDATED_ADRESSEMAILDIFFUSION);
        return alertemail;
    }

    @BeforeEach
    public void initTest() {
        alertemail = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlertemail() throws Exception {
        int databaseSizeBeforeCreate = alertemailRepository.findAll().size();
        // Create the Alertemail
        restAlertemailMockMvc.perform(post("/api/alertemails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertemail)))
            .andExpect(status().isCreated());

        // Validate the Alertemail in the database
        List<Alertemail> alertemailList = alertemailRepository.findAll();
        assertThat(alertemailList).hasSize(databaseSizeBeforeCreate + 1);
        Alertemail testAlertemail = alertemailList.get(alertemailList.size() - 1);
        assertThat(testAlertemail.getIdalertemail()).isEqualTo(DEFAULT_IDALERTEMAIL);
        assertThat(testAlertemail.getCodealertemail()).isEqualTo(DEFAULT_CODEALERTEMAIL);
        assertThat(testAlertemail.getTypealertemail()).isEqualTo(DEFAULT_TYPEALERTEMAIL);
        assertThat(testAlertemail.getObjetalertemail()).isEqualTo(DEFAULT_OBJETALERTEMAIL);
        assertThat(testAlertemail.getAdressemaildiffusion()).isEqualTo(DEFAULT_ADRESSEMAILDIFFUSION);
    }

    @Test
    @Transactional
    public void createAlertemailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alertemailRepository.findAll().size();

        // Create the Alertemail with an existing ID
        alertemail.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertemailMockMvc.perform(post("/api/alertemails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertemail)))
            .andExpect(status().isBadRequest());

        // Validate the Alertemail in the database
        List<Alertemail> alertemailList = alertemailRepository.findAll();
        assertThat(alertemailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdalertemailIsRequired() throws Exception {
        int databaseSizeBeforeTest = alertemailRepository.findAll().size();
        // set the field null
        alertemail.setIdalertemail(null);

        // Create the Alertemail, which fails.


        restAlertemailMockMvc.perform(post("/api/alertemails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertemail)))
            .andExpect(status().isBadRequest());

        List<Alertemail> alertemailList = alertemailRepository.findAll();
        assertThat(alertemailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlertemails() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList
        restAlertemailMockMvc.perform(get("/api/alertemails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertemail.getId().intValue())))
            .andExpect(jsonPath("$.[*].idalertemail").value(hasItem(DEFAULT_IDALERTEMAIL)))
            .andExpect(jsonPath("$.[*].codealertemail").value(hasItem(DEFAULT_CODEALERTEMAIL)))
            .andExpect(jsonPath("$.[*].typealertemail").value(hasItem(DEFAULT_TYPEALERTEMAIL)))
            .andExpect(jsonPath("$.[*].objetalertemail").value(hasItem(DEFAULT_OBJETALERTEMAIL)))
            .andExpect(jsonPath("$.[*].adressemaildiffusion").value(hasItem(DEFAULT_ADRESSEMAILDIFFUSION)));
    }
    
    @Test
    @Transactional
    public void getAlertemail() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get the alertemail
        restAlertemailMockMvc.perform(get("/api/alertemails/{id}", alertemail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alertemail.getId().intValue()))
            .andExpect(jsonPath("$.idalertemail").value(DEFAULT_IDALERTEMAIL))
            .andExpect(jsonPath("$.codealertemail").value(DEFAULT_CODEALERTEMAIL))
            .andExpect(jsonPath("$.typealertemail").value(DEFAULT_TYPEALERTEMAIL))
            .andExpect(jsonPath("$.objetalertemail").value(DEFAULT_OBJETALERTEMAIL))
            .andExpect(jsonPath("$.adressemaildiffusion").value(DEFAULT_ADRESSEMAILDIFFUSION));
    }


    @Test
    @Transactional
    public void getAlertemailsByIdFiltering() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        Long id = alertemail.getId();

        defaultAlertemailShouldBeFound("id.equals=" + id);
        defaultAlertemailShouldNotBeFound("id.notEquals=" + id);

        defaultAlertemailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlertemailShouldNotBeFound("id.greaterThan=" + id);

        defaultAlertemailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlertemailShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlertemailsByIdalertemailIsEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where idalertemail equals to DEFAULT_IDALERTEMAIL
        defaultAlertemailShouldBeFound("idalertemail.equals=" + DEFAULT_IDALERTEMAIL);

        // Get all the alertemailList where idalertemail equals to UPDATED_IDALERTEMAIL
        defaultAlertemailShouldNotBeFound("idalertemail.equals=" + UPDATED_IDALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByIdalertemailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where idalertemail not equals to DEFAULT_IDALERTEMAIL
        defaultAlertemailShouldNotBeFound("idalertemail.notEquals=" + DEFAULT_IDALERTEMAIL);

        // Get all the alertemailList where idalertemail not equals to UPDATED_IDALERTEMAIL
        defaultAlertemailShouldBeFound("idalertemail.notEquals=" + UPDATED_IDALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByIdalertemailIsInShouldWork() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where idalertemail in DEFAULT_IDALERTEMAIL or UPDATED_IDALERTEMAIL
        defaultAlertemailShouldBeFound("idalertemail.in=" + DEFAULT_IDALERTEMAIL + "," + UPDATED_IDALERTEMAIL);

        // Get all the alertemailList where idalertemail equals to UPDATED_IDALERTEMAIL
        defaultAlertemailShouldNotBeFound("idalertemail.in=" + UPDATED_IDALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByIdalertemailIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where idalertemail is not null
        defaultAlertemailShouldBeFound("idalertemail.specified=true");

        // Get all the alertemailList where idalertemail is null
        defaultAlertemailShouldNotBeFound("idalertemail.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlertemailsByIdalertemailIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where idalertemail is greater than or equal to DEFAULT_IDALERTEMAIL
        defaultAlertemailShouldBeFound("idalertemail.greaterThanOrEqual=" + DEFAULT_IDALERTEMAIL);

        // Get all the alertemailList where idalertemail is greater than or equal to UPDATED_IDALERTEMAIL
        defaultAlertemailShouldNotBeFound("idalertemail.greaterThanOrEqual=" + UPDATED_IDALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByIdalertemailIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where idalertemail is less than or equal to DEFAULT_IDALERTEMAIL
        defaultAlertemailShouldBeFound("idalertemail.lessThanOrEqual=" + DEFAULT_IDALERTEMAIL);

        // Get all the alertemailList where idalertemail is less than or equal to SMALLER_IDALERTEMAIL
        defaultAlertemailShouldNotBeFound("idalertemail.lessThanOrEqual=" + SMALLER_IDALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByIdalertemailIsLessThanSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where idalertemail is less than DEFAULT_IDALERTEMAIL
        defaultAlertemailShouldNotBeFound("idalertemail.lessThan=" + DEFAULT_IDALERTEMAIL);

        // Get all the alertemailList where idalertemail is less than UPDATED_IDALERTEMAIL
        defaultAlertemailShouldBeFound("idalertemail.lessThan=" + UPDATED_IDALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByIdalertemailIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where idalertemail is greater than DEFAULT_IDALERTEMAIL
        defaultAlertemailShouldNotBeFound("idalertemail.greaterThan=" + DEFAULT_IDALERTEMAIL);

        // Get all the alertemailList where idalertemail is greater than SMALLER_IDALERTEMAIL
        defaultAlertemailShouldBeFound("idalertemail.greaterThan=" + SMALLER_IDALERTEMAIL);
    }


    @Test
    @Transactional
    public void getAllAlertemailsByCodealertemailIsEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where codealertemail equals to DEFAULT_CODEALERTEMAIL
        defaultAlertemailShouldBeFound("codealertemail.equals=" + DEFAULT_CODEALERTEMAIL);

        // Get all the alertemailList where codealertemail equals to UPDATED_CODEALERTEMAIL
        defaultAlertemailShouldNotBeFound("codealertemail.equals=" + UPDATED_CODEALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByCodealertemailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where codealertemail not equals to DEFAULT_CODEALERTEMAIL
        defaultAlertemailShouldNotBeFound("codealertemail.notEquals=" + DEFAULT_CODEALERTEMAIL);

        // Get all the alertemailList where codealertemail not equals to UPDATED_CODEALERTEMAIL
        defaultAlertemailShouldBeFound("codealertemail.notEquals=" + UPDATED_CODEALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByCodealertemailIsInShouldWork() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where codealertemail in DEFAULT_CODEALERTEMAIL or UPDATED_CODEALERTEMAIL
        defaultAlertemailShouldBeFound("codealertemail.in=" + DEFAULT_CODEALERTEMAIL + "," + UPDATED_CODEALERTEMAIL);

        // Get all the alertemailList where codealertemail equals to UPDATED_CODEALERTEMAIL
        defaultAlertemailShouldNotBeFound("codealertemail.in=" + UPDATED_CODEALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByCodealertemailIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where codealertemail is not null
        defaultAlertemailShouldBeFound("codealertemail.specified=true");

        // Get all the alertemailList where codealertemail is null
        defaultAlertemailShouldNotBeFound("codealertemail.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertemailsByCodealertemailContainsSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where codealertemail contains DEFAULT_CODEALERTEMAIL
        defaultAlertemailShouldBeFound("codealertemail.contains=" + DEFAULT_CODEALERTEMAIL);

        // Get all the alertemailList where codealertemail contains UPDATED_CODEALERTEMAIL
        defaultAlertemailShouldNotBeFound("codealertemail.contains=" + UPDATED_CODEALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByCodealertemailNotContainsSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where codealertemail does not contain DEFAULT_CODEALERTEMAIL
        defaultAlertemailShouldNotBeFound("codealertemail.doesNotContain=" + DEFAULT_CODEALERTEMAIL);

        // Get all the alertemailList where codealertemail does not contain UPDATED_CODEALERTEMAIL
        defaultAlertemailShouldBeFound("codealertemail.doesNotContain=" + UPDATED_CODEALERTEMAIL);
    }


    @Test
    @Transactional
    public void getAllAlertemailsByTypealertemailIsEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where typealertemail equals to DEFAULT_TYPEALERTEMAIL
        defaultAlertemailShouldBeFound("typealertemail.equals=" + DEFAULT_TYPEALERTEMAIL);

        // Get all the alertemailList where typealertemail equals to UPDATED_TYPEALERTEMAIL
        defaultAlertemailShouldNotBeFound("typealertemail.equals=" + UPDATED_TYPEALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByTypealertemailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where typealertemail not equals to DEFAULT_TYPEALERTEMAIL
        defaultAlertemailShouldNotBeFound("typealertemail.notEquals=" + DEFAULT_TYPEALERTEMAIL);

        // Get all the alertemailList where typealertemail not equals to UPDATED_TYPEALERTEMAIL
        defaultAlertemailShouldBeFound("typealertemail.notEquals=" + UPDATED_TYPEALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByTypealertemailIsInShouldWork() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where typealertemail in DEFAULT_TYPEALERTEMAIL or UPDATED_TYPEALERTEMAIL
        defaultAlertemailShouldBeFound("typealertemail.in=" + DEFAULT_TYPEALERTEMAIL + "," + UPDATED_TYPEALERTEMAIL);

        // Get all the alertemailList where typealertemail equals to UPDATED_TYPEALERTEMAIL
        defaultAlertemailShouldNotBeFound("typealertemail.in=" + UPDATED_TYPEALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByTypealertemailIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where typealertemail is not null
        defaultAlertemailShouldBeFound("typealertemail.specified=true");

        // Get all the alertemailList where typealertemail is null
        defaultAlertemailShouldNotBeFound("typealertemail.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertemailsByTypealertemailContainsSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where typealertemail contains DEFAULT_TYPEALERTEMAIL
        defaultAlertemailShouldBeFound("typealertemail.contains=" + DEFAULT_TYPEALERTEMAIL);

        // Get all the alertemailList where typealertemail contains UPDATED_TYPEALERTEMAIL
        defaultAlertemailShouldNotBeFound("typealertemail.contains=" + UPDATED_TYPEALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByTypealertemailNotContainsSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where typealertemail does not contain DEFAULT_TYPEALERTEMAIL
        defaultAlertemailShouldNotBeFound("typealertemail.doesNotContain=" + DEFAULT_TYPEALERTEMAIL);

        // Get all the alertemailList where typealertemail does not contain UPDATED_TYPEALERTEMAIL
        defaultAlertemailShouldBeFound("typealertemail.doesNotContain=" + UPDATED_TYPEALERTEMAIL);
    }


    @Test
    @Transactional
    public void getAllAlertemailsByObjetalertemailIsEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where objetalertemail equals to DEFAULT_OBJETALERTEMAIL
        defaultAlertemailShouldBeFound("objetalertemail.equals=" + DEFAULT_OBJETALERTEMAIL);

        // Get all the alertemailList where objetalertemail equals to UPDATED_OBJETALERTEMAIL
        defaultAlertemailShouldNotBeFound("objetalertemail.equals=" + UPDATED_OBJETALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByObjetalertemailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where objetalertemail not equals to DEFAULT_OBJETALERTEMAIL
        defaultAlertemailShouldNotBeFound("objetalertemail.notEquals=" + DEFAULT_OBJETALERTEMAIL);

        // Get all the alertemailList where objetalertemail not equals to UPDATED_OBJETALERTEMAIL
        defaultAlertemailShouldBeFound("objetalertemail.notEquals=" + UPDATED_OBJETALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByObjetalertemailIsInShouldWork() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where objetalertemail in DEFAULT_OBJETALERTEMAIL or UPDATED_OBJETALERTEMAIL
        defaultAlertemailShouldBeFound("objetalertemail.in=" + DEFAULT_OBJETALERTEMAIL + "," + UPDATED_OBJETALERTEMAIL);

        // Get all the alertemailList where objetalertemail equals to UPDATED_OBJETALERTEMAIL
        defaultAlertemailShouldNotBeFound("objetalertemail.in=" + UPDATED_OBJETALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByObjetalertemailIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where objetalertemail is not null
        defaultAlertemailShouldBeFound("objetalertemail.specified=true");

        // Get all the alertemailList where objetalertemail is null
        defaultAlertemailShouldNotBeFound("objetalertemail.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertemailsByObjetalertemailContainsSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where objetalertemail contains DEFAULT_OBJETALERTEMAIL
        defaultAlertemailShouldBeFound("objetalertemail.contains=" + DEFAULT_OBJETALERTEMAIL);

        // Get all the alertemailList where objetalertemail contains UPDATED_OBJETALERTEMAIL
        defaultAlertemailShouldNotBeFound("objetalertemail.contains=" + UPDATED_OBJETALERTEMAIL);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByObjetalertemailNotContainsSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where objetalertemail does not contain DEFAULT_OBJETALERTEMAIL
        defaultAlertemailShouldNotBeFound("objetalertemail.doesNotContain=" + DEFAULT_OBJETALERTEMAIL);

        // Get all the alertemailList where objetalertemail does not contain UPDATED_OBJETALERTEMAIL
        defaultAlertemailShouldBeFound("objetalertemail.doesNotContain=" + UPDATED_OBJETALERTEMAIL);
    }


    @Test
    @Transactional
    public void getAllAlertemailsByAdressemaildiffusionIsEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where adressemaildiffusion equals to DEFAULT_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldBeFound("adressemaildiffusion.equals=" + DEFAULT_ADRESSEMAILDIFFUSION);

        // Get all the alertemailList where adressemaildiffusion equals to UPDATED_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldNotBeFound("adressemaildiffusion.equals=" + UPDATED_ADRESSEMAILDIFFUSION);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByAdressemaildiffusionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where adressemaildiffusion not equals to DEFAULT_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldNotBeFound("adressemaildiffusion.notEquals=" + DEFAULT_ADRESSEMAILDIFFUSION);

        // Get all the alertemailList where adressemaildiffusion not equals to UPDATED_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldBeFound("adressemaildiffusion.notEquals=" + UPDATED_ADRESSEMAILDIFFUSION);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByAdressemaildiffusionIsInShouldWork() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where adressemaildiffusion in DEFAULT_ADRESSEMAILDIFFUSION or UPDATED_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldBeFound("adressemaildiffusion.in=" + DEFAULT_ADRESSEMAILDIFFUSION + "," + UPDATED_ADRESSEMAILDIFFUSION);

        // Get all the alertemailList where adressemaildiffusion equals to UPDATED_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldNotBeFound("adressemaildiffusion.in=" + UPDATED_ADRESSEMAILDIFFUSION);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByAdressemaildiffusionIsNullOrNotNull() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where adressemaildiffusion is not null
        defaultAlertemailShouldBeFound("adressemaildiffusion.specified=true");

        // Get all the alertemailList where adressemaildiffusion is null
        defaultAlertemailShouldNotBeFound("adressemaildiffusion.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlertemailsByAdressemaildiffusionContainsSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where adressemaildiffusion contains DEFAULT_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldBeFound("adressemaildiffusion.contains=" + DEFAULT_ADRESSEMAILDIFFUSION);

        // Get all the alertemailList where adressemaildiffusion contains UPDATED_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldNotBeFound("adressemaildiffusion.contains=" + UPDATED_ADRESSEMAILDIFFUSION);
    }

    @Test
    @Transactional
    public void getAllAlertemailsByAdressemaildiffusionNotContainsSomething() throws Exception {
        // Initialize the database
        alertemailRepository.saveAndFlush(alertemail);

        // Get all the alertemailList where adressemaildiffusion does not contain DEFAULT_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldNotBeFound("adressemaildiffusion.doesNotContain=" + DEFAULT_ADRESSEMAILDIFFUSION);

        // Get all the alertemailList where adressemaildiffusion does not contain UPDATED_ADRESSEMAILDIFFUSION
        defaultAlertemailShouldBeFound("adressemaildiffusion.doesNotContain=" + UPDATED_ADRESSEMAILDIFFUSION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlertemailShouldBeFound(String filter) throws Exception {
        restAlertemailMockMvc.perform(get("/api/alertemails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alertemail.getId().intValue())))
            .andExpect(jsonPath("$.[*].idalertemail").value(hasItem(DEFAULT_IDALERTEMAIL)))
            .andExpect(jsonPath("$.[*].codealertemail").value(hasItem(DEFAULT_CODEALERTEMAIL)))
            .andExpect(jsonPath("$.[*].typealertemail").value(hasItem(DEFAULT_TYPEALERTEMAIL)))
            .andExpect(jsonPath("$.[*].objetalertemail").value(hasItem(DEFAULT_OBJETALERTEMAIL)))
            .andExpect(jsonPath("$.[*].adressemaildiffusion").value(hasItem(DEFAULT_ADRESSEMAILDIFFUSION)));

        // Check, that the count call also returns 1
        restAlertemailMockMvc.perform(get("/api/alertemails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlertemailShouldNotBeFound(String filter) throws Exception {
        restAlertemailMockMvc.perform(get("/api/alertemails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlertemailMockMvc.perform(get("/api/alertemails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlertemail() throws Exception {
        // Get the alertemail
        restAlertemailMockMvc.perform(get("/api/alertemails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlertemail() throws Exception {
        // Initialize the database
        alertemailService.save(alertemail);

        int databaseSizeBeforeUpdate = alertemailRepository.findAll().size();

        // Update the alertemail
        Alertemail updatedAlertemail = alertemailRepository.findById(alertemail.getId()).get();
        // Disconnect from session so that the updates on updatedAlertemail are not directly saved in db
        em.detach(updatedAlertemail);
        updatedAlertemail
            .idalertemail(UPDATED_IDALERTEMAIL)
            .codealertemail(UPDATED_CODEALERTEMAIL)
            .typealertemail(UPDATED_TYPEALERTEMAIL)
            .objetalertemail(UPDATED_OBJETALERTEMAIL)
            .adressemaildiffusion(UPDATED_ADRESSEMAILDIFFUSION);

        restAlertemailMockMvc.perform(put("/api/alertemails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlertemail)))
            .andExpect(status().isOk());

        // Validate the Alertemail in the database
        List<Alertemail> alertemailList = alertemailRepository.findAll();
        assertThat(alertemailList).hasSize(databaseSizeBeforeUpdate);
        Alertemail testAlertemail = alertemailList.get(alertemailList.size() - 1);
        assertThat(testAlertemail.getIdalertemail()).isEqualTo(UPDATED_IDALERTEMAIL);
        assertThat(testAlertemail.getCodealertemail()).isEqualTo(UPDATED_CODEALERTEMAIL);
        assertThat(testAlertemail.getTypealertemail()).isEqualTo(UPDATED_TYPEALERTEMAIL);
        assertThat(testAlertemail.getObjetalertemail()).isEqualTo(UPDATED_OBJETALERTEMAIL);
        assertThat(testAlertemail.getAdressemaildiffusion()).isEqualTo(UPDATED_ADRESSEMAILDIFFUSION);
    }

    @Test
    @Transactional
    public void updateNonExistingAlertemail() throws Exception {
        int databaseSizeBeforeUpdate = alertemailRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertemailMockMvc.perform(put("/api/alertemails").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alertemail)))
            .andExpect(status().isBadRequest());

        // Validate the Alertemail in the database
        List<Alertemail> alertemailList = alertemailRepository.findAll();
        assertThat(alertemailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlertemail() throws Exception {
        // Initialize the database
        alertemailService.save(alertemail);

        int databaseSizeBeforeDelete = alertemailRepository.findAll().size();

        // Delete the alertemail
        restAlertemailMockMvc.perform(delete("/api/alertemails/{id}", alertemail.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alertemail> alertemailList = alertemailRepository.findAll();
        assertThat(alertemailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
