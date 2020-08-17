package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Smtp;
import com.mycompany.myapp.repository.SmtpRepository;
import com.mycompany.myapp.service.SmtpService;
import com.mycompany.myapp.service.dto.SmtpCriteria;
import com.mycompany.myapp.service.SmtpQueryService;

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
 * Integration tests for the {@link SmtpResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SmtpResourceIT {

    private static final Integer DEFAULT_IDSMTP = 1;
    private static final Integer UPDATED_IDSMTP = 2;
    private static final Integer SMALLER_IDSMTP = 1 - 1;

    private static final String DEFAULT_CODESMTP = "AAAAAAAAAA";
    private static final String UPDATED_CODESMTP = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSEIPSMTP = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSEIPSMTP = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_AUTHENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private SmtpRepository smtpRepository;

    @Autowired
    private SmtpService smtpService;

    @Autowired
    private SmtpQueryService smtpQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSmtpMockMvc;

    private Smtp smtp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Smtp createEntity(EntityManager em) {
        Smtp smtp = new Smtp()
            .idsmtp(DEFAULT_IDSMTP)
            .codesmtp(DEFAULT_CODESMTP)
            .adresseipsmtp(DEFAULT_ADRESSEIPSMTP)
            .authentification(DEFAULT_AUTHENTIFICATION)
            .statut(DEFAULT_STATUT)
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD);
        return smtp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Smtp createUpdatedEntity(EntityManager em) {
        Smtp smtp = new Smtp()
            .idsmtp(UPDATED_IDSMTP)
            .codesmtp(UPDATED_CODESMTP)
            .adresseipsmtp(UPDATED_ADRESSEIPSMTP)
            .authentification(UPDATED_AUTHENTIFICATION)
            .statut(UPDATED_STATUT)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD);
        return smtp;
    }

    @BeforeEach
    public void initTest() {
        smtp = createEntity(em);
    }

    @Test
    @Transactional
    public void createSmtp() throws Exception {
        int databaseSizeBeforeCreate = smtpRepository.findAll().size();
        // Create the Smtp
        restSmtpMockMvc.perform(post("/api/smtps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smtp)))
            .andExpect(status().isCreated());

        // Validate the Smtp in the database
        List<Smtp> smtpList = smtpRepository.findAll();
        assertThat(smtpList).hasSize(databaseSizeBeforeCreate + 1);
        Smtp testSmtp = smtpList.get(smtpList.size() - 1);
        assertThat(testSmtp.getIdsmtp()).isEqualTo(DEFAULT_IDSMTP);
        assertThat(testSmtp.getCodesmtp()).isEqualTo(DEFAULT_CODESMTP);
        assertThat(testSmtp.getAdresseipsmtp()).isEqualTo(DEFAULT_ADRESSEIPSMTP);
        assertThat(testSmtp.getAuthentification()).isEqualTo(DEFAULT_AUTHENTIFICATION);
        assertThat(testSmtp.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testSmtp.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testSmtp.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createSmtpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = smtpRepository.findAll().size();

        // Create the Smtp with an existing ID
        smtp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmtpMockMvc.perform(post("/api/smtps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smtp)))
            .andExpect(status().isBadRequest());

        // Validate the Smtp in the database
        List<Smtp> smtpList = smtpRepository.findAll();
        assertThat(smtpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdsmtpIsRequired() throws Exception {
        int databaseSizeBeforeTest = smtpRepository.findAll().size();
        // set the field null
        smtp.setIdsmtp(null);

        // Create the Smtp, which fails.


        restSmtpMockMvc.perform(post("/api/smtps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smtp)))
            .andExpect(status().isBadRequest());

        List<Smtp> smtpList = smtpRepository.findAll();
        assertThat(smtpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmtps() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList
        restSmtpMockMvc.perform(get("/api/smtps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smtp.getId().intValue())))
            .andExpect(jsonPath("$.[*].idsmtp").value(hasItem(DEFAULT_IDSMTP)))
            .andExpect(jsonPath("$.[*].codesmtp").value(hasItem(DEFAULT_CODESMTP)))
            .andExpect(jsonPath("$.[*].adresseipsmtp").value(hasItem(DEFAULT_ADRESSEIPSMTP)))
            .andExpect(jsonPath("$.[*].authentification").value(hasItem(DEFAULT_AUTHENTIFICATION)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }
    
    @Test
    @Transactional
    public void getSmtp() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get the smtp
        restSmtpMockMvc.perform(get("/api/smtps/{id}", smtp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(smtp.getId().intValue()))
            .andExpect(jsonPath("$.idsmtp").value(DEFAULT_IDSMTP))
            .andExpect(jsonPath("$.codesmtp").value(DEFAULT_CODESMTP))
            .andExpect(jsonPath("$.adresseipsmtp").value(DEFAULT_ADRESSEIPSMTP))
            .andExpect(jsonPath("$.authentification").value(DEFAULT_AUTHENTIFICATION))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }


    @Test
    @Transactional
    public void getSmtpsByIdFiltering() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        Long id = smtp.getId();

        defaultSmtpShouldBeFound("id.equals=" + id);
        defaultSmtpShouldNotBeFound("id.notEquals=" + id);

        defaultSmtpShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSmtpShouldNotBeFound("id.greaterThan=" + id);

        defaultSmtpShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSmtpShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSmtpsByIdsmtpIsEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where idsmtp equals to DEFAULT_IDSMTP
        defaultSmtpShouldBeFound("idsmtp.equals=" + DEFAULT_IDSMTP);

        // Get all the smtpList where idsmtp equals to UPDATED_IDSMTP
        defaultSmtpShouldNotBeFound("idsmtp.equals=" + UPDATED_IDSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByIdsmtpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where idsmtp not equals to DEFAULT_IDSMTP
        defaultSmtpShouldNotBeFound("idsmtp.notEquals=" + DEFAULT_IDSMTP);

        // Get all the smtpList where idsmtp not equals to UPDATED_IDSMTP
        defaultSmtpShouldBeFound("idsmtp.notEquals=" + UPDATED_IDSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByIdsmtpIsInShouldWork() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where idsmtp in DEFAULT_IDSMTP or UPDATED_IDSMTP
        defaultSmtpShouldBeFound("idsmtp.in=" + DEFAULT_IDSMTP + "," + UPDATED_IDSMTP);

        // Get all the smtpList where idsmtp equals to UPDATED_IDSMTP
        defaultSmtpShouldNotBeFound("idsmtp.in=" + UPDATED_IDSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByIdsmtpIsNullOrNotNull() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where idsmtp is not null
        defaultSmtpShouldBeFound("idsmtp.specified=true");

        // Get all the smtpList where idsmtp is null
        defaultSmtpShouldNotBeFound("idsmtp.specified=false");
    }

    @Test
    @Transactional
    public void getAllSmtpsByIdsmtpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where idsmtp is greater than or equal to DEFAULT_IDSMTP
        defaultSmtpShouldBeFound("idsmtp.greaterThanOrEqual=" + DEFAULT_IDSMTP);

        // Get all the smtpList where idsmtp is greater than or equal to UPDATED_IDSMTP
        defaultSmtpShouldNotBeFound("idsmtp.greaterThanOrEqual=" + UPDATED_IDSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByIdsmtpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where idsmtp is less than or equal to DEFAULT_IDSMTP
        defaultSmtpShouldBeFound("idsmtp.lessThanOrEqual=" + DEFAULT_IDSMTP);

        // Get all the smtpList where idsmtp is less than or equal to SMALLER_IDSMTP
        defaultSmtpShouldNotBeFound("idsmtp.lessThanOrEqual=" + SMALLER_IDSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByIdsmtpIsLessThanSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where idsmtp is less than DEFAULT_IDSMTP
        defaultSmtpShouldNotBeFound("idsmtp.lessThan=" + DEFAULT_IDSMTP);

        // Get all the smtpList where idsmtp is less than UPDATED_IDSMTP
        defaultSmtpShouldBeFound("idsmtp.lessThan=" + UPDATED_IDSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByIdsmtpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where idsmtp is greater than DEFAULT_IDSMTP
        defaultSmtpShouldNotBeFound("idsmtp.greaterThan=" + DEFAULT_IDSMTP);

        // Get all the smtpList where idsmtp is greater than SMALLER_IDSMTP
        defaultSmtpShouldBeFound("idsmtp.greaterThan=" + SMALLER_IDSMTP);
    }


    @Test
    @Transactional
    public void getAllSmtpsByCodesmtpIsEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where codesmtp equals to DEFAULT_CODESMTP
        defaultSmtpShouldBeFound("codesmtp.equals=" + DEFAULT_CODESMTP);

        // Get all the smtpList where codesmtp equals to UPDATED_CODESMTP
        defaultSmtpShouldNotBeFound("codesmtp.equals=" + UPDATED_CODESMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByCodesmtpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where codesmtp not equals to DEFAULT_CODESMTP
        defaultSmtpShouldNotBeFound("codesmtp.notEquals=" + DEFAULT_CODESMTP);

        // Get all the smtpList where codesmtp not equals to UPDATED_CODESMTP
        defaultSmtpShouldBeFound("codesmtp.notEquals=" + UPDATED_CODESMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByCodesmtpIsInShouldWork() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where codesmtp in DEFAULT_CODESMTP or UPDATED_CODESMTP
        defaultSmtpShouldBeFound("codesmtp.in=" + DEFAULT_CODESMTP + "," + UPDATED_CODESMTP);

        // Get all the smtpList where codesmtp equals to UPDATED_CODESMTP
        defaultSmtpShouldNotBeFound("codesmtp.in=" + UPDATED_CODESMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByCodesmtpIsNullOrNotNull() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where codesmtp is not null
        defaultSmtpShouldBeFound("codesmtp.specified=true");

        // Get all the smtpList where codesmtp is null
        defaultSmtpShouldNotBeFound("codesmtp.specified=false");
    }
                @Test
    @Transactional
    public void getAllSmtpsByCodesmtpContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where codesmtp contains DEFAULT_CODESMTP
        defaultSmtpShouldBeFound("codesmtp.contains=" + DEFAULT_CODESMTP);

        // Get all the smtpList where codesmtp contains UPDATED_CODESMTP
        defaultSmtpShouldNotBeFound("codesmtp.contains=" + UPDATED_CODESMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByCodesmtpNotContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where codesmtp does not contain DEFAULT_CODESMTP
        defaultSmtpShouldNotBeFound("codesmtp.doesNotContain=" + DEFAULT_CODESMTP);

        // Get all the smtpList where codesmtp does not contain UPDATED_CODESMTP
        defaultSmtpShouldBeFound("codesmtp.doesNotContain=" + UPDATED_CODESMTP);
    }


    @Test
    @Transactional
    public void getAllSmtpsByAdresseipsmtpIsEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where adresseipsmtp equals to DEFAULT_ADRESSEIPSMTP
        defaultSmtpShouldBeFound("adresseipsmtp.equals=" + DEFAULT_ADRESSEIPSMTP);

        // Get all the smtpList where adresseipsmtp equals to UPDATED_ADRESSEIPSMTP
        defaultSmtpShouldNotBeFound("adresseipsmtp.equals=" + UPDATED_ADRESSEIPSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByAdresseipsmtpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where adresseipsmtp not equals to DEFAULT_ADRESSEIPSMTP
        defaultSmtpShouldNotBeFound("adresseipsmtp.notEquals=" + DEFAULT_ADRESSEIPSMTP);

        // Get all the smtpList where adresseipsmtp not equals to UPDATED_ADRESSEIPSMTP
        defaultSmtpShouldBeFound("adresseipsmtp.notEquals=" + UPDATED_ADRESSEIPSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByAdresseipsmtpIsInShouldWork() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where adresseipsmtp in DEFAULT_ADRESSEIPSMTP or UPDATED_ADRESSEIPSMTP
        defaultSmtpShouldBeFound("adresseipsmtp.in=" + DEFAULT_ADRESSEIPSMTP + "," + UPDATED_ADRESSEIPSMTP);

        // Get all the smtpList where adresseipsmtp equals to UPDATED_ADRESSEIPSMTP
        defaultSmtpShouldNotBeFound("adresseipsmtp.in=" + UPDATED_ADRESSEIPSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByAdresseipsmtpIsNullOrNotNull() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where adresseipsmtp is not null
        defaultSmtpShouldBeFound("adresseipsmtp.specified=true");

        // Get all the smtpList where adresseipsmtp is null
        defaultSmtpShouldNotBeFound("adresseipsmtp.specified=false");
    }
                @Test
    @Transactional
    public void getAllSmtpsByAdresseipsmtpContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where adresseipsmtp contains DEFAULT_ADRESSEIPSMTP
        defaultSmtpShouldBeFound("adresseipsmtp.contains=" + DEFAULT_ADRESSEIPSMTP);

        // Get all the smtpList where adresseipsmtp contains UPDATED_ADRESSEIPSMTP
        defaultSmtpShouldNotBeFound("adresseipsmtp.contains=" + UPDATED_ADRESSEIPSMTP);
    }

    @Test
    @Transactional
    public void getAllSmtpsByAdresseipsmtpNotContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where adresseipsmtp does not contain DEFAULT_ADRESSEIPSMTP
        defaultSmtpShouldNotBeFound("adresseipsmtp.doesNotContain=" + DEFAULT_ADRESSEIPSMTP);

        // Get all the smtpList where adresseipsmtp does not contain UPDATED_ADRESSEIPSMTP
        defaultSmtpShouldBeFound("adresseipsmtp.doesNotContain=" + UPDATED_ADRESSEIPSMTP);
    }


    @Test
    @Transactional
    public void getAllSmtpsByAuthentificationIsEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where authentification equals to DEFAULT_AUTHENTIFICATION
        defaultSmtpShouldBeFound("authentification.equals=" + DEFAULT_AUTHENTIFICATION);

        // Get all the smtpList where authentification equals to UPDATED_AUTHENTIFICATION
        defaultSmtpShouldNotBeFound("authentification.equals=" + UPDATED_AUTHENTIFICATION);
    }

    @Test
    @Transactional
    public void getAllSmtpsByAuthentificationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where authentification not equals to DEFAULT_AUTHENTIFICATION
        defaultSmtpShouldNotBeFound("authentification.notEquals=" + DEFAULT_AUTHENTIFICATION);

        // Get all the smtpList where authentification not equals to UPDATED_AUTHENTIFICATION
        defaultSmtpShouldBeFound("authentification.notEquals=" + UPDATED_AUTHENTIFICATION);
    }

    @Test
    @Transactional
    public void getAllSmtpsByAuthentificationIsInShouldWork() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where authentification in DEFAULT_AUTHENTIFICATION or UPDATED_AUTHENTIFICATION
        defaultSmtpShouldBeFound("authentification.in=" + DEFAULT_AUTHENTIFICATION + "," + UPDATED_AUTHENTIFICATION);

        // Get all the smtpList where authentification equals to UPDATED_AUTHENTIFICATION
        defaultSmtpShouldNotBeFound("authentification.in=" + UPDATED_AUTHENTIFICATION);
    }

    @Test
    @Transactional
    public void getAllSmtpsByAuthentificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where authentification is not null
        defaultSmtpShouldBeFound("authentification.specified=true");

        // Get all the smtpList where authentification is null
        defaultSmtpShouldNotBeFound("authentification.specified=false");
    }
                @Test
    @Transactional
    public void getAllSmtpsByAuthentificationContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where authentification contains DEFAULT_AUTHENTIFICATION
        defaultSmtpShouldBeFound("authentification.contains=" + DEFAULT_AUTHENTIFICATION);

        // Get all the smtpList where authentification contains UPDATED_AUTHENTIFICATION
        defaultSmtpShouldNotBeFound("authentification.contains=" + UPDATED_AUTHENTIFICATION);
    }

    @Test
    @Transactional
    public void getAllSmtpsByAuthentificationNotContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where authentification does not contain DEFAULT_AUTHENTIFICATION
        defaultSmtpShouldNotBeFound("authentification.doesNotContain=" + DEFAULT_AUTHENTIFICATION);

        // Get all the smtpList where authentification does not contain UPDATED_AUTHENTIFICATION
        defaultSmtpShouldBeFound("authentification.doesNotContain=" + UPDATED_AUTHENTIFICATION);
    }


    @Test
    @Transactional
    public void getAllSmtpsByStatutIsEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where statut equals to DEFAULT_STATUT
        defaultSmtpShouldBeFound("statut.equals=" + DEFAULT_STATUT);

        // Get all the smtpList where statut equals to UPDATED_STATUT
        defaultSmtpShouldNotBeFound("statut.equals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllSmtpsByStatutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where statut not equals to DEFAULT_STATUT
        defaultSmtpShouldNotBeFound("statut.notEquals=" + DEFAULT_STATUT);

        // Get all the smtpList where statut not equals to UPDATED_STATUT
        defaultSmtpShouldBeFound("statut.notEquals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllSmtpsByStatutIsInShouldWork() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where statut in DEFAULT_STATUT or UPDATED_STATUT
        defaultSmtpShouldBeFound("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT);

        // Get all the smtpList where statut equals to UPDATED_STATUT
        defaultSmtpShouldNotBeFound("statut.in=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllSmtpsByStatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where statut is not null
        defaultSmtpShouldBeFound("statut.specified=true");

        // Get all the smtpList where statut is null
        defaultSmtpShouldNotBeFound("statut.specified=false");
    }
                @Test
    @Transactional
    public void getAllSmtpsByStatutContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where statut contains DEFAULT_STATUT
        defaultSmtpShouldBeFound("statut.contains=" + DEFAULT_STATUT);

        // Get all the smtpList where statut contains UPDATED_STATUT
        defaultSmtpShouldNotBeFound("statut.contains=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllSmtpsByStatutNotContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where statut does not contain DEFAULT_STATUT
        defaultSmtpShouldNotBeFound("statut.doesNotContain=" + DEFAULT_STATUT);

        // Get all the smtpList where statut does not contain UPDATED_STATUT
        defaultSmtpShouldBeFound("statut.doesNotContain=" + UPDATED_STATUT);
    }


    @Test
    @Transactional
    public void getAllSmtpsByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where login equals to DEFAULT_LOGIN
        defaultSmtpShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the smtpList where login equals to UPDATED_LOGIN
        defaultSmtpShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSmtpsByLoginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where login not equals to DEFAULT_LOGIN
        defaultSmtpShouldNotBeFound("login.notEquals=" + DEFAULT_LOGIN);

        // Get all the smtpList where login not equals to UPDATED_LOGIN
        defaultSmtpShouldBeFound("login.notEquals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSmtpsByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultSmtpShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the smtpList where login equals to UPDATED_LOGIN
        defaultSmtpShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSmtpsByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where login is not null
        defaultSmtpShouldBeFound("login.specified=true");

        // Get all the smtpList where login is null
        defaultSmtpShouldNotBeFound("login.specified=false");
    }
                @Test
    @Transactional
    public void getAllSmtpsByLoginContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where login contains DEFAULT_LOGIN
        defaultSmtpShouldBeFound("login.contains=" + DEFAULT_LOGIN);

        // Get all the smtpList where login contains UPDATED_LOGIN
        defaultSmtpShouldNotBeFound("login.contains=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSmtpsByLoginNotContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where login does not contain DEFAULT_LOGIN
        defaultSmtpShouldNotBeFound("login.doesNotContain=" + DEFAULT_LOGIN);

        // Get all the smtpList where login does not contain UPDATED_LOGIN
        defaultSmtpShouldBeFound("login.doesNotContain=" + UPDATED_LOGIN);
    }


    @Test
    @Transactional
    public void getAllSmtpsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where password equals to DEFAULT_PASSWORD
        defaultSmtpShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the smtpList where password equals to UPDATED_PASSWORD
        defaultSmtpShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSmtpsByPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where password not equals to DEFAULT_PASSWORD
        defaultSmtpShouldNotBeFound("password.notEquals=" + DEFAULT_PASSWORD);

        // Get all the smtpList where password not equals to UPDATED_PASSWORD
        defaultSmtpShouldBeFound("password.notEquals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSmtpsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultSmtpShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the smtpList where password equals to UPDATED_PASSWORD
        defaultSmtpShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSmtpsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where password is not null
        defaultSmtpShouldBeFound("password.specified=true");

        // Get all the smtpList where password is null
        defaultSmtpShouldNotBeFound("password.specified=false");
    }
                @Test
    @Transactional
    public void getAllSmtpsByPasswordContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where password contains DEFAULT_PASSWORD
        defaultSmtpShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the smtpList where password contains UPDATED_PASSWORD
        defaultSmtpShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllSmtpsByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        smtpRepository.saveAndFlush(smtp);

        // Get all the smtpList where password does not contain DEFAULT_PASSWORD
        defaultSmtpShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the smtpList where password does not contain UPDATED_PASSWORD
        defaultSmtpShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSmtpShouldBeFound(String filter) throws Exception {
        restSmtpMockMvc.perform(get("/api/smtps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smtp.getId().intValue())))
            .andExpect(jsonPath("$.[*].idsmtp").value(hasItem(DEFAULT_IDSMTP)))
            .andExpect(jsonPath("$.[*].codesmtp").value(hasItem(DEFAULT_CODESMTP)))
            .andExpect(jsonPath("$.[*].adresseipsmtp").value(hasItem(DEFAULT_ADRESSEIPSMTP)))
            .andExpect(jsonPath("$.[*].authentification").value(hasItem(DEFAULT_AUTHENTIFICATION)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));

        // Check, that the count call also returns 1
        restSmtpMockMvc.perform(get("/api/smtps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSmtpShouldNotBeFound(String filter) throws Exception {
        restSmtpMockMvc.perform(get("/api/smtps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSmtpMockMvc.perform(get("/api/smtps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSmtp() throws Exception {
        // Get the smtp
        restSmtpMockMvc.perform(get("/api/smtps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmtp() throws Exception {
        // Initialize the database
        smtpService.save(smtp);

        int databaseSizeBeforeUpdate = smtpRepository.findAll().size();

        // Update the smtp
        Smtp updatedSmtp = smtpRepository.findById(smtp.getId()).get();
        // Disconnect from session so that the updates on updatedSmtp are not directly saved in db
        em.detach(updatedSmtp);
        updatedSmtp
            .idsmtp(UPDATED_IDSMTP)
            .codesmtp(UPDATED_CODESMTP)
            .adresseipsmtp(UPDATED_ADRESSEIPSMTP)
            .authentification(UPDATED_AUTHENTIFICATION)
            .statut(UPDATED_STATUT)
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD);

        restSmtpMockMvc.perform(put("/api/smtps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSmtp)))
            .andExpect(status().isOk());

        // Validate the Smtp in the database
        List<Smtp> smtpList = smtpRepository.findAll();
        assertThat(smtpList).hasSize(databaseSizeBeforeUpdate);
        Smtp testSmtp = smtpList.get(smtpList.size() - 1);
        assertThat(testSmtp.getIdsmtp()).isEqualTo(UPDATED_IDSMTP);
        assertThat(testSmtp.getCodesmtp()).isEqualTo(UPDATED_CODESMTP);
        assertThat(testSmtp.getAdresseipsmtp()).isEqualTo(UPDATED_ADRESSEIPSMTP);
        assertThat(testSmtp.getAuthentification()).isEqualTo(UPDATED_AUTHENTIFICATION);
        assertThat(testSmtp.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testSmtp.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testSmtp.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingSmtp() throws Exception {
        int databaseSizeBeforeUpdate = smtpRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSmtpMockMvc.perform(put("/api/smtps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smtp)))
            .andExpect(status().isBadRequest());

        // Validate the Smtp in the database
        List<Smtp> smtpList = smtpRepository.findAll();
        assertThat(smtpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSmtp() throws Exception {
        // Initialize the database
        smtpService.save(smtp);

        int databaseSizeBeforeDelete = smtpRepository.findAll().size();

        // Delete the smtp
        restSmtpMockMvc.perform(delete("/api/smtps/{id}", smtp.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Smtp> smtpList = smtpRepository.findAll();
        assertThat(smtpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
