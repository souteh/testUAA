package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Agence;
import com.mycompany.myapp.repository.AgenceRepository;
import com.mycompany.myapp.service.AgenceService;
import com.mycompany.myapp.service.dto.AgenceCriteria;
import com.mycompany.myapp.service.AgenceQueryService;

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
 * Integration tests for the {@link AgenceResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AgenceResourceIT {

    private static final Integer DEFAULT_IDAGENCE = 1;
    private static final Integer UPDATED_IDAGENCE = 2;
    private static final Integer SMALLER_IDAGENCE = 1 - 1;

    private static final String DEFAULT_CODEAGENCE = "AAAAAAAAAA";
    private static final String UPDATED_CODEAGENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;
    private static final Integer SMALLER_NUMERO = 1 - 1;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private AgenceService agenceService;

    @Autowired
    private AgenceQueryService agenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgenceMockMvc;

    private Agence agence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createEntity(EntityManager em) {
        Agence agence = new Agence()
            .idagence(DEFAULT_IDAGENCE)
            .codeagence(DEFAULT_CODEAGENCE)
            .numero(DEFAULT_NUMERO)
            .nom(DEFAULT_NOM)
            .adresse(DEFAULT_ADRESSE);
        return agence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agence createUpdatedEntity(EntityManager em) {
        Agence agence = new Agence()
            .idagence(UPDATED_IDAGENCE)
            .codeagence(UPDATED_CODEAGENCE)
            .numero(UPDATED_NUMERO)
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE);
        return agence;
    }

    @BeforeEach
    public void initTest() {
        agence = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgence() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();
        // Create the Agence
        restAgenceMockMvc.perform(post("/api/agences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isCreated());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate + 1);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getIdagence()).isEqualTo(DEFAULT_IDAGENCE);
        assertThat(testAgence.getCodeagence()).isEqualTo(DEFAULT_CODEAGENCE);
        assertThat(testAgence.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAgence.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAgence.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createAgenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agenceRepository.findAll().size();

        // Create the Agence with an existing ID
        agence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenceMockMvc.perform(post("/api/agences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdagenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenceRepository.findAll().size();
        // set the field null
        agence.setIdagence(null);

        // Create the Agence, which fails.


        restAgenceMockMvc.perform(post("/api/agences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isBadRequest());

        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgences() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList
        restAgenceMockMvc.perform(get("/api/agences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agence.getId().intValue())))
            .andExpect(jsonPath("$.[*].idagence").value(hasItem(DEFAULT_IDAGENCE)))
            .andExpect(jsonPath("$.[*].codeagence").value(hasItem(DEFAULT_CODEAGENCE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)));
    }
    
    @Test
    @Transactional
    public void getAgence() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", agence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agence.getId().intValue()))
            .andExpect(jsonPath("$.idagence").value(DEFAULT_IDAGENCE))
            .andExpect(jsonPath("$.codeagence").value(DEFAULT_CODEAGENCE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE));
    }


    @Test
    @Transactional
    public void getAgencesByIdFiltering() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        Long id = agence.getId();

        defaultAgenceShouldBeFound("id.equals=" + id);
        defaultAgenceShouldNotBeFound("id.notEquals=" + id);

        defaultAgenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAgenceShouldNotBeFound("id.greaterThan=" + id);

        defaultAgenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAgenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAgencesByIdagenceIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where idagence equals to DEFAULT_IDAGENCE
        defaultAgenceShouldBeFound("idagence.equals=" + DEFAULT_IDAGENCE);

        // Get all the agenceList where idagence equals to UPDATED_IDAGENCE
        defaultAgenceShouldNotBeFound("idagence.equals=" + UPDATED_IDAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByIdagenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where idagence not equals to DEFAULT_IDAGENCE
        defaultAgenceShouldNotBeFound("idagence.notEquals=" + DEFAULT_IDAGENCE);

        // Get all the agenceList where idagence not equals to UPDATED_IDAGENCE
        defaultAgenceShouldBeFound("idagence.notEquals=" + UPDATED_IDAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByIdagenceIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where idagence in DEFAULT_IDAGENCE or UPDATED_IDAGENCE
        defaultAgenceShouldBeFound("idagence.in=" + DEFAULT_IDAGENCE + "," + UPDATED_IDAGENCE);

        // Get all the agenceList where idagence equals to UPDATED_IDAGENCE
        defaultAgenceShouldNotBeFound("idagence.in=" + UPDATED_IDAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByIdagenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where idagence is not null
        defaultAgenceShouldBeFound("idagence.specified=true");

        // Get all the agenceList where idagence is null
        defaultAgenceShouldNotBeFound("idagence.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencesByIdagenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where idagence is greater than or equal to DEFAULT_IDAGENCE
        defaultAgenceShouldBeFound("idagence.greaterThanOrEqual=" + DEFAULT_IDAGENCE);

        // Get all the agenceList where idagence is greater than or equal to UPDATED_IDAGENCE
        defaultAgenceShouldNotBeFound("idagence.greaterThanOrEqual=" + UPDATED_IDAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByIdagenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where idagence is less than or equal to DEFAULT_IDAGENCE
        defaultAgenceShouldBeFound("idagence.lessThanOrEqual=" + DEFAULT_IDAGENCE);

        // Get all the agenceList where idagence is less than or equal to SMALLER_IDAGENCE
        defaultAgenceShouldNotBeFound("idagence.lessThanOrEqual=" + SMALLER_IDAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByIdagenceIsLessThanSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where idagence is less than DEFAULT_IDAGENCE
        defaultAgenceShouldNotBeFound("idagence.lessThan=" + DEFAULT_IDAGENCE);

        // Get all the agenceList where idagence is less than UPDATED_IDAGENCE
        defaultAgenceShouldBeFound("idagence.lessThan=" + UPDATED_IDAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByIdagenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where idagence is greater than DEFAULT_IDAGENCE
        defaultAgenceShouldNotBeFound("idagence.greaterThan=" + DEFAULT_IDAGENCE);

        // Get all the agenceList where idagence is greater than SMALLER_IDAGENCE
        defaultAgenceShouldBeFound("idagence.greaterThan=" + SMALLER_IDAGENCE);
    }


    @Test
    @Transactional
    public void getAllAgencesByCodeagenceIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where codeagence equals to DEFAULT_CODEAGENCE
        defaultAgenceShouldBeFound("codeagence.equals=" + DEFAULT_CODEAGENCE);

        // Get all the agenceList where codeagence equals to UPDATED_CODEAGENCE
        defaultAgenceShouldNotBeFound("codeagence.equals=" + UPDATED_CODEAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByCodeagenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where codeagence not equals to DEFAULT_CODEAGENCE
        defaultAgenceShouldNotBeFound("codeagence.notEquals=" + DEFAULT_CODEAGENCE);

        // Get all the agenceList where codeagence not equals to UPDATED_CODEAGENCE
        defaultAgenceShouldBeFound("codeagence.notEquals=" + UPDATED_CODEAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByCodeagenceIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where codeagence in DEFAULT_CODEAGENCE or UPDATED_CODEAGENCE
        defaultAgenceShouldBeFound("codeagence.in=" + DEFAULT_CODEAGENCE + "," + UPDATED_CODEAGENCE);

        // Get all the agenceList where codeagence equals to UPDATED_CODEAGENCE
        defaultAgenceShouldNotBeFound("codeagence.in=" + UPDATED_CODEAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByCodeagenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where codeagence is not null
        defaultAgenceShouldBeFound("codeagence.specified=true");

        // Get all the agenceList where codeagence is null
        defaultAgenceShouldNotBeFound("codeagence.specified=false");
    }
                @Test
    @Transactional
    public void getAllAgencesByCodeagenceContainsSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where codeagence contains DEFAULT_CODEAGENCE
        defaultAgenceShouldBeFound("codeagence.contains=" + DEFAULT_CODEAGENCE);

        // Get all the agenceList where codeagence contains UPDATED_CODEAGENCE
        defaultAgenceShouldNotBeFound("codeagence.contains=" + UPDATED_CODEAGENCE);
    }

    @Test
    @Transactional
    public void getAllAgencesByCodeagenceNotContainsSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where codeagence does not contain DEFAULT_CODEAGENCE
        defaultAgenceShouldNotBeFound("codeagence.doesNotContain=" + DEFAULT_CODEAGENCE);

        // Get all the agenceList where codeagence does not contain UPDATED_CODEAGENCE
        defaultAgenceShouldBeFound("codeagence.doesNotContain=" + UPDATED_CODEAGENCE);
    }


    @Test
    @Transactional
    public void getAllAgencesByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where numero equals to DEFAULT_NUMERO
        defaultAgenceShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the agenceList where numero equals to UPDATED_NUMERO
        defaultAgenceShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllAgencesByNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where numero not equals to DEFAULT_NUMERO
        defaultAgenceShouldNotBeFound("numero.notEquals=" + DEFAULT_NUMERO);

        // Get all the agenceList where numero not equals to UPDATED_NUMERO
        defaultAgenceShouldBeFound("numero.notEquals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllAgencesByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultAgenceShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the agenceList where numero equals to UPDATED_NUMERO
        defaultAgenceShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllAgencesByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where numero is not null
        defaultAgenceShouldBeFound("numero.specified=true");

        // Get all the agenceList where numero is null
        defaultAgenceShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgencesByNumeroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where numero is greater than or equal to DEFAULT_NUMERO
        defaultAgenceShouldBeFound("numero.greaterThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the agenceList where numero is greater than or equal to UPDATED_NUMERO
        defaultAgenceShouldNotBeFound("numero.greaterThanOrEqual=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllAgencesByNumeroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where numero is less than or equal to DEFAULT_NUMERO
        defaultAgenceShouldBeFound("numero.lessThanOrEqual=" + DEFAULT_NUMERO);

        // Get all the agenceList where numero is less than or equal to SMALLER_NUMERO
        defaultAgenceShouldNotBeFound("numero.lessThanOrEqual=" + SMALLER_NUMERO);
    }

    @Test
    @Transactional
    public void getAllAgencesByNumeroIsLessThanSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where numero is less than DEFAULT_NUMERO
        defaultAgenceShouldNotBeFound("numero.lessThan=" + DEFAULT_NUMERO);

        // Get all the agenceList where numero is less than UPDATED_NUMERO
        defaultAgenceShouldBeFound("numero.lessThan=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllAgencesByNumeroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where numero is greater than DEFAULT_NUMERO
        defaultAgenceShouldNotBeFound("numero.greaterThan=" + DEFAULT_NUMERO);

        // Get all the agenceList where numero is greater than SMALLER_NUMERO
        defaultAgenceShouldBeFound("numero.greaterThan=" + SMALLER_NUMERO);
    }


    @Test
    @Transactional
    public void getAllAgencesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where nom equals to DEFAULT_NOM
        defaultAgenceShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the agenceList where nom equals to UPDATED_NOM
        defaultAgenceShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllAgencesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where nom not equals to DEFAULT_NOM
        defaultAgenceShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the agenceList where nom not equals to UPDATED_NOM
        defaultAgenceShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllAgencesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultAgenceShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the agenceList where nom equals to UPDATED_NOM
        defaultAgenceShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllAgencesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where nom is not null
        defaultAgenceShouldBeFound("nom.specified=true");

        // Get all the agenceList where nom is null
        defaultAgenceShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllAgencesByNomContainsSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where nom contains DEFAULT_NOM
        defaultAgenceShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the agenceList where nom contains UPDATED_NOM
        defaultAgenceShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllAgencesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where nom does not contain DEFAULT_NOM
        defaultAgenceShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the agenceList where nom does not contain UPDATED_NOM
        defaultAgenceShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllAgencesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where adresse equals to DEFAULT_ADRESSE
        defaultAgenceShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the agenceList where adresse equals to UPDATED_ADRESSE
        defaultAgenceShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllAgencesByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where adresse not equals to DEFAULT_ADRESSE
        defaultAgenceShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the agenceList where adresse not equals to UPDATED_ADRESSE
        defaultAgenceShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllAgencesByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultAgenceShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the agenceList where adresse equals to UPDATED_ADRESSE
        defaultAgenceShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllAgencesByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where adresse is not null
        defaultAgenceShouldBeFound("adresse.specified=true");

        // Get all the agenceList where adresse is null
        defaultAgenceShouldNotBeFound("adresse.specified=false");
    }
                @Test
    @Transactional
    public void getAllAgencesByAdresseContainsSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where adresse contains DEFAULT_ADRESSE
        defaultAgenceShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the agenceList where adresse contains UPDATED_ADRESSE
        defaultAgenceShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllAgencesByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        agenceRepository.saveAndFlush(agence);

        // Get all the agenceList where adresse does not contain DEFAULT_ADRESSE
        defaultAgenceShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the agenceList where adresse does not contain UPDATED_ADRESSE
        defaultAgenceShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAgenceShouldBeFound(String filter) throws Exception {
        restAgenceMockMvc.perform(get("/api/agences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agence.getId().intValue())))
            .andExpect(jsonPath("$.[*].idagence").value(hasItem(DEFAULT_IDAGENCE)))
            .andExpect(jsonPath("$.[*].codeagence").value(hasItem(DEFAULT_CODEAGENCE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)));

        // Check, that the count call also returns 1
        restAgenceMockMvc.perform(get("/api/agences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAgenceShouldNotBeFound(String filter) throws Exception {
        restAgenceMockMvc.perform(get("/api/agences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgenceMockMvc.perform(get("/api/agences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAgence() throws Exception {
        // Get the agence
        restAgenceMockMvc.perform(get("/api/agences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgence() throws Exception {
        // Initialize the database
        agenceService.save(agence);

        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // Update the agence
        Agence updatedAgence = agenceRepository.findById(agence.getId()).get();
        // Disconnect from session so that the updates on updatedAgence are not directly saved in db
        em.detach(updatedAgence);
        updatedAgence
            .idagence(UPDATED_IDAGENCE)
            .codeagence(UPDATED_CODEAGENCE)
            .numero(UPDATED_NUMERO)
            .nom(UPDATED_NOM)
            .adresse(UPDATED_ADRESSE);

        restAgenceMockMvc.perform(put("/api/agences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgence)))
            .andExpect(status().isOk());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
        Agence testAgence = agenceList.get(agenceList.size() - 1);
        assertThat(testAgence.getIdagence()).isEqualTo(UPDATED_IDAGENCE);
        assertThat(testAgence.getCodeagence()).isEqualTo(UPDATED_CODEAGENCE);
        assertThat(testAgence.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAgence.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAgence.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingAgence() throws Exception {
        int databaseSizeBeforeUpdate = agenceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenceMockMvc.perform(put("/api/agences").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(agence)))
            .andExpect(status().isBadRequest());

        // Validate the Agence in the database
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgence() throws Exception {
        // Initialize the database
        agenceService.save(agence);

        int databaseSizeBeforeDelete = agenceRepository.findAll().size();

        // Delete the agence
        restAgenceMockMvc.perform(delete("/api/agences/{id}", agence.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agence> agenceList = agenceRepository.findAll();
        assertThat(agenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
