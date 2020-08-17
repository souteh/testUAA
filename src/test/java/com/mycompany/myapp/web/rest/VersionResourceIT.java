package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Version;
import com.mycompany.myapp.domain.Typeterminal;
import com.mycompany.myapp.repository.VersionRepository;
import com.mycompany.myapp.service.VersionService;
import com.mycompany.myapp.service.dto.VersionCriteria;
import com.mycompany.myapp.service.VersionQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VersionResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class VersionResourceIT {

    private static final Integer DEFAULT_IDVERSION = 1;
    private static final Integer UPDATED_IDVERSION = 2;
    private static final Integer SMALLER_IDVERSION = 1 - 1;

    private static final String DEFAULT_REFVERSION = "AAAAAAAAAA";
    private static final String UPDATED_REFVERSION = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_FICHIER = "BBBBBBBBBB";

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private VersionService versionService;

    @Autowired
    private VersionQueryService versionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVersionMockMvc;

    private Version version;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Version createEntity(EntityManager em) {
        Version version = new Version()
            .idversion(DEFAULT_IDVERSION)
            .refversion(DEFAULT_REFVERSION)
            .libelle(DEFAULT_LIBELLE)
            .date(DEFAULT_DATE)
            .fichier(DEFAULT_FICHIER);
        // Add required entity
        Typeterminal typeterminal;
        if (TestUtil.findAll(em, Typeterminal.class).isEmpty()) {
            typeterminal = TypeterminalResourceIT.createEntity(em);
            em.persist(typeterminal);
            em.flush();
        } else {
            typeterminal = TestUtil.findAll(em, Typeterminal.class).get(0);
        }
        version.setIdtypeterminal(typeterminal);
        return version;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Version createUpdatedEntity(EntityManager em) {
        Version version = new Version()
            .idversion(UPDATED_IDVERSION)
            .refversion(UPDATED_REFVERSION)
            .libelle(UPDATED_LIBELLE)
            .date(UPDATED_DATE)
            .fichier(UPDATED_FICHIER);
        // Add required entity
        Typeterminal typeterminal;
        if (TestUtil.findAll(em, Typeterminal.class).isEmpty()) {
            typeterminal = TypeterminalResourceIT.createUpdatedEntity(em);
            em.persist(typeterminal);
            em.flush();
        } else {
            typeterminal = TestUtil.findAll(em, Typeterminal.class).get(0);
        }
        version.setIdtypeterminal(typeterminal);
        return version;
    }

    @BeforeEach
    public void initTest() {
        version = createEntity(em);
    }

    @Test
    @Transactional
    public void createVersion() throws Exception {
        int databaseSizeBeforeCreate = versionRepository.findAll().size();
        // Create the Version
        restVersionMockMvc.perform(post("/api/versions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(version)))
            .andExpect(status().isCreated());

        // Validate the Version in the database
        List<Version> versionList = versionRepository.findAll();
        assertThat(versionList).hasSize(databaseSizeBeforeCreate + 1);
        Version testVersion = versionList.get(versionList.size() - 1);
        assertThat(testVersion.getIdversion()).isEqualTo(DEFAULT_IDVERSION);
        assertThat(testVersion.getRefversion()).isEqualTo(DEFAULT_REFVERSION);
        assertThat(testVersion.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testVersion.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testVersion.getFichier()).isEqualTo(DEFAULT_FICHIER);
    }

    @Test
    @Transactional
    public void createVersionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = versionRepository.findAll().size();

        // Create the Version with an existing ID
        version.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVersionMockMvc.perform(post("/api/versions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(version)))
            .andExpect(status().isBadRequest());

        // Validate the Version in the database
        List<Version> versionList = versionRepository.findAll();
        assertThat(versionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdversionIsRequired() throws Exception {
        int databaseSizeBeforeTest = versionRepository.findAll().size();
        // set the field null
        version.setIdversion(null);

        // Create the Version, which fails.


        restVersionMockMvc.perform(post("/api/versions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(version)))
            .andExpect(status().isBadRequest());

        List<Version> versionList = versionRepository.findAll();
        assertThat(versionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVersions() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList
        restVersionMockMvc.perform(get("/api/versions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(version.getId().intValue())))
            .andExpect(jsonPath("$.[*].idversion").value(hasItem(DEFAULT_IDVERSION)))
            .andExpect(jsonPath("$.[*].refversion").value(hasItem(DEFAULT_REFVERSION)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fichier").value(hasItem(DEFAULT_FICHIER)));
    }
    
    @Test
    @Transactional
    public void getVersion() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get the version
        restVersionMockMvc.perform(get("/api/versions/{id}", version.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(version.getId().intValue()))
            .andExpect(jsonPath("$.idversion").value(DEFAULT_IDVERSION))
            .andExpect(jsonPath("$.refversion").value(DEFAULT_REFVERSION))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.fichier").value(DEFAULT_FICHIER));
    }


    @Test
    @Transactional
    public void getVersionsByIdFiltering() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        Long id = version.getId();

        defaultVersionShouldBeFound("id.equals=" + id);
        defaultVersionShouldNotBeFound("id.notEquals=" + id);

        defaultVersionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVersionShouldNotBeFound("id.greaterThan=" + id);

        defaultVersionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVersionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVersionsByIdversionIsEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where idversion equals to DEFAULT_IDVERSION
        defaultVersionShouldBeFound("idversion.equals=" + DEFAULT_IDVERSION);

        // Get all the versionList where idversion equals to UPDATED_IDVERSION
        defaultVersionShouldNotBeFound("idversion.equals=" + UPDATED_IDVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByIdversionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where idversion not equals to DEFAULT_IDVERSION
        defaultVersionShouldNotBeFound("idversion.notEquals=" + DEFAULT_IDVERSION);

        // Get all the versionList where idversion not equals to UPDATED_IDVERSION
        defaultVersionShouldBeFound("idversion.notEquals=" + UPDATED_IDVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByIdversionIsInShouldWork() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where idversion in DEFAULT_IDVERSION or UPDATED_IDVERSION
        defaultVersionShouldBeFound("idversion.in=" + DEFAULT_IDVERSION + "," + UPDATED_IDVERSION);

        // Get all the versionList where idversion equals to UPDATED_IDVERSION
        defaultVersionShouldNotBeFound("idversion.in=" + UPDATED_IDVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByIdversionIsNullOrNotNull() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where idversion is not null
        defaultVersionShouldBeFound("idversion.specified=true");

        // Get all the versionList where idversion is null
        defaultVersionShouldNotBeFound("idversion.specified=false");
    }

    @Test
    @Transactional
    public void getAllVersionsByIdversionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where idversion is greater than or equal to DEFAULT_IDVERSION
        defaultVersionShouldBeFound("idversion.greaterThanOrEqual=" + DEFAULT_IDVERSION);

        // Get all the versionList where idversion is greater than or equal to UPDATED_IDVERSION
        defaultVersionShouldNotBeFound("idversion.greaterThanOrEqual=" + UPDATED_IDVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByIdversionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where idversion is less than or equal to DEFAULT_IDVERSION
        defaultVersionShouldBeFound("idversion.lessThanOrEqual=" + DEFAULT_IDVERSION);

        // Get all the versionList where idversion is less than or equal to SMALLER_IDVERSION
        defaultVersionShouldNotBeFound("idversion.lessThanOrEqual=" + SMALLER_IDVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByIdversionIsLessThanSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where idversion is less than DEFAULT_IDVERSION
        defaultVersionShouldNotBeFound("idversion.lessThan=" + DEFAULT_IDVERSION);

        // Get all the versionList where idversion is less than UPDATED_IDVERSION
        defaultVersionShouldBeFound("idversion.lessThan=" + UPDATED_IDVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByIdversionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where idversion is greater than DEFAULT_IDVERSION
        defaultVersionShouldNotBeFound("idversion.greaterThan=" + DEFAULT_IDVERSION);

        // Get all the versionList where idversion is greater than SMALLER_IDVERSION
        defaultVersionShouldBeFound("idversion.greaterThan=" + SMALLER_IDVERSION);
    }


    @Test
    @Transactional
    public void getAllVersionsByRefversionIsEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where refversion equals to DEFAULT_REFVERSION
        defaultVersionShouldBeFound("refversion.equals=" + DEFAULT_REFVERSION);

        // Get all the versionList where refversion equals to UPDATED_REFVERSION
        defaultVersionShouldNotBeFound("refversion.equals=" + UPDATED_REFVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByRefversionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where refversion not equals to DEFAULT_REFVERSION
        defaultVersionShouldNotBeFound("refversion.notEquals=" + DEFAULT_REFVERSION);

        // Get all the versionList where refversion not equals to UPDATED_REFVERSION
        defaultVersionShouldBeFound("refversion.notEquals=" + UPDATED_REFVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByRefversionIsInShouldWork() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where refversion in DEFAULT_REFVERSION or UPDATED_REFVERSION
        defaultVersionShouldBeFound("refversion.in=" + DEFAULT_REFVERSION + "," + UPDATED_REFVERSION);

        // Get all the versionList where refversion equals to UPDATED_REFVERSION
        defaultVersionShouldNotBeFound("refversion.in=" + UPDATED_REFVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByRefversionIsNullOrNotNull() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where refversion is not null
        defaultVersionShouldBeFound("refversion.specified=true");

        // Get all the versionList where refversion is null
        defaultVersionShouldNotBeFound("refversion.specified=false");
    }
                @Test
    @Transactional
    public void getAllVersionsByRefversionContainsSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where refversion contains DEFAULT_REFVERSION
        defaultVersionShouldBeFound("refversion.contains=" + DEFAULT_REFVERSION);

        // Get all the versionList where refversion contains UPDATED_REFVERSION
        defaultVersionShouldNotBeFound("refversion.contains=" + UPDATED_REFVERSION);
    }

    @Test
    @Transactional
    public void getAllVersionsByRefversionNotContainsSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where refversion does not contain DEFAULT_REFVERSION
        defaultVersionShouldNotBeFound("refversion.doesNotContain=" + DEFAULT_REFVERSION);

        // Get all the versionList where refversion does not contain UPDATED_REFVERSION
        defaultVersionShouldBeFound("refversion.doesNotContain=" + UPDATED_REFVERSION);
    }


    @Test
    @Transactional
    public void getAllVersionsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where libelle equals to DEFAULT_LIBELLE
        defaultVersionShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the versionList where libelle equals to UPDATED_LIBELLE
        defaultVersionShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllVersionsByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where libelle not equals to DEFAULT_LIBELLE
        defaultVersionShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the versionList where libelle not equals to UPDATED_LIBELLE
        defaultVersionShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllVersionsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultVersionShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the versionList where libelle equals to UPDATED_LIBELLE
        defaultVersionShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllVersionsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where libelle is not null
        defaultVersionShouldBeFound("libelle.specified=true");

        // Get all the versionList where libelle is null
        defaultVersionShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllVersionsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where libelle contains DEFAULT_LIBELLE
        defaultVersionShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the versionList where libelle contains UPDATED_LIBELLE
        defaultVersionShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllVersionsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where libelle does not contain DEFAULT_LIBELLE
        defaultVersionShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the versionList where libelle does not contain UPDATED_LIBELLE
        defaultVersionShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }


    @Test
    @Transactional
    public void getAllVersionsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where date equals to DEFAULT_DATE
        defaultVersionShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the versionList where date equals to UPDATED_DATE
        defaultVersionShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllVersionsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where date not equals to DEFAULT_DATE
        defaultVersionShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the versionList where date not equals to UPDATED_DATE
        defaultVersionShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllVersionsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where date in DEFAULT_DATE or UPDATED_DATE
        defaultVersionShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the versionList where date equals to UPDATED_DATE
        defaultVersionShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllVersionsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where date is not null
        defaultVersionShouldBeFound("date.specified=true");

        // Get all the versionList where date is null
        defaultVersionShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllVersionsByFichierIsEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where fichier equals to DEFAULT_FICHIER
        defaultVersionShouldBeFound("fichier.equals=" + DEFAULT_FICHIER);

        // Get all the versionList where fichier equals to UPDATED_FICHIER
        defaultVersionShouldNotBeFound("fichier.equals=" + UPDATED_FICHIER);
    }

    @Test
    @Transactional
    public void getAllVersionsByFichierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where fichier not equals to DEFAULT_FICHIER
        defaultVersionShouldNotBeFound("fichier.notEquals=" + DEFAULT_FICHIER);

        // Get all the versionList where fichier not equals to UPDATED_FICHIER
        defaultVersionShouldBeFound("fichier.notEquals=" + UPDATED_FICHIER);
    }

    @Test
    @Transactional
    public void getAllVersionsByFichierIsInShouldWork() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where fichier in DEFAULT_FICHIER or UPDATED_FICHIER
        defaultVersionShouldBeFound("fichier.in=" + DEFAULT_FICHIER + "," + UPDATED_FICHIER);

        // Get all the versionList where fichier equals to UPDATED_FICHIER
        defaultVersionShouldNotBeFound("fichier.in=" + UPDATED_FICHIER);
    }

    @Test
    @Transactional
    public void getAllVersionsByFichierIsNullOrNotNull() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where fichier is not null
        defaultVersionShouldBeFound("fichier.specified=true");

        // Get all the versionList where fichier is null
        defaultVersionShouldNotBeFound("fichier.specified=false");
    }
                @Test
    @Transactional
    public void getAllVersionsByFichierContainsSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where fichier contains DEFAULT_FICHIER
        defaultVersionShouldBeFound("fichier.contains=" + DEFAULT_FICHIER);

        // Get all the versionList where fichier contains UPDATED_FICHIER
        defaultVersionShouldNotBeFound("fichier.contains=" + UPDATED_FICHIER);
    }

    @Test
    @Transactional
    public void getAllVersionsByFichierNotContainsSomething() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versionList where fichier does not contain DEFAULT_FICHIER
        defaultVersionShouldNotBeFound("fichier.doesNotContain=" + DEFAULT_FICHIER);

        // Get all the versionList where fichier does not contain UPDATED_FICHIER
        defaultVersionShouldBeFound("fichier.doesNotContain=" + UPDATED_FICHIER);
    }


    @Test
    @Transactional
    public void getAllVersionsByIdtypeterminalIsEqualToSomething() throws Exception {
        // Get already existing entity
        Typeterminal idtypeterminal = version.getIdtypeterminal();
        versionRepository.saveAndFlush(version);
        Long idtypeterminalId = idtypeterminal.getId();

        // Get all the versionList where idtypeterminal equals to idtypeterminalId
        defaultVersionShouldBeFound("idtypeterminalId.equals=" + idtypeterminalId);

        // Get all the versionList where idtypeterminal equals to idtypeterminalId + 1
        defaultVersionShouldNotBeFound("idtypeterminalId.equals=" + (idtypeterminalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVersionShouldBeFound(String filter) throws Exception {
        restVersionMockMvc.perform(get("/api/versions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(version.getId().intValue())))
            .andExpect(jsonPath("$.[*].idversion").value(hasItem(DEFAULT_IDVERSION)))
            .andExpect(jsonPath("$.[*].refversion").value(hasItem(DEFAULT_REFVERSION)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fichier").value(hasItem(DEFAULT_FICHIER)));

        // Check, that the count call also returns 1
        restVersionMockMvc.perform(get("/api/versions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVersionShouldNotBeFound(String filter) throws Exception {
        restVersionMockMvc.perform(get("/api/versions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVersionMockMvc.perform(get("/api/versions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVersion() throws Exception {
        // Get the version
        restVersionMockMvc.perform(get("/api/versions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersion() throws Exception {
        // Initialize the database
        versionService.save(version);

        int databaseSizeBeforeUpdate = versionRepository.findAll().size();

        // Update the version
        Version updatedVersion = versionRepository.findById(version.getId()).get();
        // Disconnect from session so that the updates on updatedVersion are not directly saved in db
        em.detach(updatedVersion);
        updatedVersion
            .idversion(UPDATED_IDVERSION)
            .refversion(UPDATED_REFVERSION)
            .libelle(UPDATED_LIBELLE)
            .date(UPDATED_DATE)
            .fichier(UPDATED_FICHIER);

        restVersionMockMvc.perform(put("/api/versions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVersion)))
            .andExpect(status().isOk());

        // Validate the Version in the database
        List<Version> versionList = versionRepository.findAll();
        assertThat(versionList).hasSize(databaseSizeBeforeUpdate);
        Version testVersion = versionList.get(versionList.size() - 1);
        assertThat(testVersion.getIdversion()).isEqualTo(UPDATED_IDVERSION);
        assertThat(testVersion.getRefversion()).isEqualTo(UPDATED_REFVERSION);
        assertThat(testVersion.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testVersion.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testVersion.getFichier()).isEqualTo(UPDATED_FICHIER);
    }

    @Test
    @Transactional
    public void updateNonExistingVersion() throws Exception {
        int databaseSizeBeforeUpdate = versionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVersionMockMvc.perform(put("/api/versions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(version)))
            .andExpect(status().isBadRequest());

        // Validate the Version in the database
        List<Version> versionList = versionRepository.findAll();
        assertThat(versionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVersion() throws Exception {
        // Initialize the database
        versionService.save(version);

        int databaseSizeBeforeDelete = versionRepository.findAll().size();

        // Delete the version
        restVersionMockMvc.perform(delete("/api/versions/{id}", version.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Version> versionList = versionRepository.findAll();
        assertThat(versionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
