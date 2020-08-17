package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.I18nTextTranslation;
import com.mycompany.myapp.repository.I18nTextTranslationRepository;
import com.mycompany.myapp.service.I18nTextTranslationService;
import com.mycompany.myapp.service.dto.I18nTextTranslationCriteria;
import com.mycompany.myapp.service.I18nTextTranslationQueryService;

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
 * Integration tests for the {@link I18nTextTranslationResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class I18nTextTranslationResourceIT {

    private static final String DEFAULT_LANG = "AAAAAAAAAA";
    private static final String UPDATED_LANG = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSLATION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_I_18_N_TEXT_ID = 1;
    private static final Integer UPDATED_I_18_N_TEXT_ID = 2;
    private static final Integer SMALLER_I_18_N_TEXT_ID = 1 - 1;

    @Autowired
    private I18nTextTranslationRepository i18nTextTranslationRepository;

    @Autowired
    private I18nTextTranslationService i18nTextTranslationService;

    @Autowired
    private I18nTextTranslationQueryService i18nTextTranslationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restI18nTextTranslationMockMvc;

    private I18nTextTranslation i18nTextTranslation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static I18nTextTranslation createEntity(EntityManager em) {
        I18nTextTranslation i18nTextTranslation = new I18nTextTranslation()
            .lang(DEFAULT_LANG)
            .translation(DEFAULT_TRANSLATION)
            .i18nTextId(DEFAULT_I_18_N_TEXT_ID);
        return i18nTextTranslation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static I18nTextTranslation createUpdatedEntity(EntityManager em) {
        I18nTextTranslation i18nTextTranslation = new I18nTextTranslation()
            .lang(UPDATED_LANG)
            .translation(UPDATED_TRANSLATION)
            .i18nTextId(UPDATED_I_18_N_TEXT_ID);
        return i18nTextTranslation;
    }

    @BeforeEach
    public void initTest() {
        i18nTextTranslation = createEntity(em);
    }

    @Test
    @Transactional
    public void createI18nTextTranslation() throws Exception {
        int databaseSizeBeforeCreate = i18nTextTranslationRepository.findAll().size();
        // Create the I18nTextTranslation
        restI18nTextTranslationMockMvc.perform(post("/api/i-18-n-text-translations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(i18nTextTranslation)))
            .andExpect(status().isCreated());

        // Validate the I18nTextTranslation in the database
        List<I18nTextTranslation> i18nTextTranslationList = i18nTextTranslationRepository.findAll();
        assertThat(i18nTextTranslationList).hasSize(databaseSizeBeforeCreate + 1);
        I18nTextTranslation testI18nTextTranslation = i18nTextTranslationList.get(i18nTextTranslationList.size() - 1);
        assertThat(testI18nTextTranslation.getLang()).isEqualTo(DEFAULT_LANG);
        assertThat(testI18nTextTranslation.getTranslation()).isEqualTo(DEFAULT_TRANSLATION);
        assertThat(testI18nTextTranslation.geti18nTextId()).isEqualTo(DEFAULT_I_18_N_TEXT_ID);
    }

    @Test
    @Transactional
    public void createI18nTextTranslationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = i18nTextTranslationRepository.findAll().size();

        // Create the I18nTextTranslation with an existing ID
        i18nTextTranslation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restI18nTextTranslationMockMvc.perform(post("/api/i-18-n-text-translations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(i18nTextTranslation)))
            .andExpect(status().isBadRequest());

        // Validate the I18nTextTranslation in the database
        List<I18nTextTranslation> i18nTextTranslationList = i18nTextTranslationRepository.findAll();
        assertThat(i18nTextTranslationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLangIsRequired() throws Exception {
        int databaseSizeBeforeTest = i18nTextTranslationRepository.findAll().size();
        // set the field null
        i18nTextTranslation.setLang(null);

        // Create the I18nTextTranslation, which fails.


        restI18nTextTranslationMockMvc.perform(post("/api/i-18-n-text-translations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(i18nTextTranslation)))
            .andExpect(status().isBadRequest());

        List<I18nTextTranslation> i18nTextTranslationList = i18nTextTranslationRepository.findAll();
        assertThat(i18nTextTranslationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTranslationIsRequired() throws Exception {
        int databaseSizeBeforeTest = i18nTextTranslationRepository.findAll().size();
        // set the field null
        i18nTextTranslation.setTranslation(null);

        // Create the I18nTextTranslation, which fails.


        restI18nTextTranslationMockMvc.perform(post("/api/i-18-n-text-translations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(i18nTextTranslation)))
            .andExpect(status().isBadRequest());

        List<I18nTextTranslation> i18nTextTranslationList = i18nTextTranslationRepository.findAll();
        assertThat(i18nTextTranslationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checki18nTextIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = i18nTextTranslationRepository.findAll().size();
        // set the field null
        i18nTextTranslation.seti18nTextId(null);

        // Create the I18nTextTranslation, which fails.


        restI18nTextTranslationMockMvc.perform(post("/api/i-18-n-text-translations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(i18nTextTranslation)))
            .andExpect(status().isBadRequest());

        List<I18nTextTranslation> i18nTextTranslationList = i18nTextTranslationRepository.findAll();
        assertThat(i18nTextTranslationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslations() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList
        restI18nTextTranslationMockMvc.perform(get("/api/i-18-n-text-translations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(i18nTextTranslation.getId().intValue())))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG)))
            .andExpect(jsonPath("$.[*].translation").value(hasItem(DEFAULT_TRANSLATION)))
            .andExpect(jsonPath("$.[*].i18nTextId").value(hasItem(DEFAULT_I_18_N_TEXT_ID)));
    }
    
    @Test
    @Transactional
    public void getI18nTextTranslation() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get the i18nTextTranslation
        restI18nTextTranslationMockMvc.perform(get("/api/i-18-n-text-translations/{id}", i18nTextTranslation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(i18nTextTranslation.getId().intValue()))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG))
            .andExpect(jsonPath("$.translation").value(DEFAULT_TRANSLATION))
            .andExpect(jsonPath("$.i18nTextId").value(DEFAULT_I_18_N_TEXT_ID));
    }


    @Test
    @Transactional
    public void getI18nTextTranslationsByIdFiltering() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        Long id = i18nTextTranslation.getId();

        defaultI18nTextTranslationShouldBeFound("id.equals=" + id);
        defaultI18nTextTranslationShouldNotBeFound("id.notEquals=" + id);

        defaultI18nTextTranslationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultI18nTextTranslationShouldNotBeFound("id.greaterThan=" + id);

        defaultI18nTextTranslationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultI18nTextTranslationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllI18nTextTranslationsByLangIsEqualToSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where lang equals to DEFAULT_LANG
        defaultI18nTextTranslationShouldBeFound("lang.equals=" + DEFAULT_LANG);

        // Get all the i18nTextTranslationList where lang equals to UPDATED_LANG
        defaultI18nTextTranslationShouldNotBeFound("lang.equals=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByLangIsNotEqualToSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where lang not equals to DEFAULT_LANG
        defaultI18nTextTranslationShouldNotBeFound("lang.notEquals=" + DEFAULT_LANG);

        // Get all the i18nTextTranslationList where lang not equals to UPDATED_LANG
        defaultI18nTextTranslationShouldBeFound("lang.notEquals=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByLangIsInShouldWork() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where lang in DEFAULT_LANG or UPDATED_LANG
        defaultI18nTextTranslationShouldBeFound("lang.in=" + DEFAULT_LANG + "," + UPDATED_LANG);

        // Get all the i18nTextTranslationList where lang equals to UPDATED_LANG
        defaultI18nTextTranslationShouldNotBeFound("lang.in=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByLangIsNullOrNotNull() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where lang is not null
        defaultI18nTextTranslationShouldBeFound("lang.specified=true");

        // Get all the i18nTextTranslationList where lang is null
        defaultI18nTextTranslationShouldNotBeFound("lang.specified=false");
    }
                @Test
    @Transactional
    public void getAllI18nTextTranslationsByLangContainsSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where lang contains DEFAULT_LANG
        defaultI18nTextTranslationShouldBeFound("lang.contains=" + DEFAULT_LANG);

        // Get all the i18nTextTranslationList where lang contains UPDATED_LANG
        defaultI18nTextTranslationShouldNotBeFound("lang.contains=" + UPDATED_LANG);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByLangNotContainsSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where lang does not contain DEFAULT_LANG
        defaultI18nTextTranslationShouldNotBeFound("lang.doesNotContain=" + DEFAULT_LANG);

        // Get all the i18nTextTranslationList where lang does not contain UPDATED_LANG
        defaultI18nTextTranslationShouldBeFound("lang.doesNotContain=" + UPDATED_LANG);
    }


    @Test
    @Transactional
    public void getAllI18nTextTranslationsByTranslationIsEqualToSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where translation equals to DEFAULT_TRANSLATION
        defaultI18nTextTranslationShouldBeFound("translation.equals=" + DEFAULT_TRANSLATION);

        // Get all the i18nTextTranslationList where translation equals to UPDATED_TRANSLATION
        defaultI18nTextTranslationShouldNotBeFound("translation.equals=" + UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByTranslationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where translation not equals to DEFAULT_TRANSLATION
        defaultI18nTextTranslationShouldNotBeFound("translation.notEquals=" + DEFAULT_TRANSLATION);

        // Get all the i18nTextTranslationList where translation not equals to UPDATED_TRANSLATION
        defaultI18nTextTranslationShouldBeFound("translation.notEquals=" + UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByTranslationIsInShouldWork() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where translation in DEFAULT_TRANSLATION or UPDATED_TRANSLATION
        defaultI18nTextTranslationShouldBeFound("translation.in=" + DEFAULT_TRANSLATION + "," + UPDATED_TRANSLATION);

        // Get all the i18nTextTranslationList where translation equals to UPDATED_TRANSLATION
        defaultI18nTextTranslationShouldNotBeFound("translation.in=" + UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByTranslationIsNullOrNotNull() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where translation is not null
        defaultI18nTextTranslationShouldBeFound("translation.specified=true");

        // Get all the i18nTextTranslationList where translation is null
        defaultI18nTextTranslationShouldNotBeFound("translation.specified=false");
    }
                @Test
    @Transactional
    public void getAllI18nTextTranslationsByTranslationContainsSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where translation contains DEFAULT_TRANSLATION
        defaultI18nTextTranslationShouldBeFound("translation.contains=" + DEFAULT_TRANSLATION);

        // Get all the i18nTextTranslationList where translation contains UPDATED_TRANSLATION
        defaultI18nTextTranslationShouldNotBeFound("translation.contains=" + UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByTranslationNotContainsSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where translation does not contain DEFAULT_TRANSLATION
        defaultI18nTextTranslationShouldNotBeFound("translation.doesNotContain=" + DEFAULT_TRANSLATION);

        // Get all the i18nTextTranslationList where translation does not contain UPDATED_TRANSLATION
        defaultI18nTextTranslationShouldBeFound("translation.doesNotContain=" + UPDATED_TRANSLATION);
    }


    @Test
    @Transactional
    public void getAllI18nTextTranslationsByi18nTextIdIsEqualToSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where i18nTextId equals to DEFAULT_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldBeFound("i18nTextId.equals=" + DEFAULT_I_18_N_TEXT_ID);

        // Get all the i18nTextTranslationList where i18nTextId equals to UPDATED_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldNotBeFound("i18nTextId.equals=" + UPDATED_I_18_N_TEXT_ID);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByi18nTextIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where i18nTextId not equals to DEFAULT_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldNotBeFound("i18nTextId.notEquals=" + DEFAULT_I_18_N_TEXT_ID);

        // Get all the i18nTextTranslationList where i18nTextId not equals to UPDATED_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldBeFound("i18nTextId.notEquals=" + UPDATED_I_18_N_TEXT_ID);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByi18nTextIdIsInShouldWork() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where i18nTextId in DEFAULT_I_18_N_TEXT_ID or UPDATED_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldBeFound("i18nTextId.in=" + DEFAULT_I_18_N_TEXT_ID + "," + UPDATED_I_18_N_TEXT_ID);

        // Get all the i18nTextTranslationList where i18nTextId equals to UPDATED_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldNotBeFound("i18nTextId.in=" + UPDATED_I_18_N_TEXT_ID);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByi18nTextIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where i18nTextId is not null
        defaultI18nTextTranslationShouldBeFound("i18nTextId.specified=true");

        // Get all the i18nTextTranslationList where i18nTextId is null
        defaultI18nTextTranslationShouldNotBeFound("i18nTextId.specified=false");
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByi18nTextIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where i18nTextId is greater than or equal to DEFAULT_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldBeFound("i18nTextId.greaterThanOrEqual=" + DEFAULT_I_18_N_TEXT_ID);

        // Get all the i18nTextTranslationList where i18nTextId is greater than or equal to UPDATED_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldNotBeFound("i18nTextId.greaterThanOrEqual=" + UPDATED_I_18_N_TEXT_ID);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByi18nTextIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where i18nTextId is less than or equal to DEFAULT_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldBeFound("i18nTextId.lessThanOrEqual=" + DEFAULT_I_18_N_TEXT_ID);

        // Get all the i18nTextTranslationList where i18nTextId is less than or equal to SMALLER_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldNotBeFound("i18nTextId.lessThanOrEqual=" + SMALLER_I_18_N_TEXT_ID);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByi18nTextIdIsLessThanSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where i18nTextId is less than DEFAULT_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldNotBeFound("i18nTextId.lessThan=" + DEFAULT_I_18_N_TEXT_ID);

        // Get all the i18nTextTranslationList where i18nTextId is less than UPDATED_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldBeFound("i18nTextId.lessThan=" + UPDATED_I_18_N_TEXT_ID);
    }

    @Test
    @Transactional
    public void getAllI18nTextTranslationsByi18nTextIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        i18nTextTranslationRepository.saveAndFlush(i18nTextTranslation);

        // Get all the i18nTextTranslationList where i18nTextId is greater than DEFAULT_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldNotBeFound("i18nTextId.greaterThan=" + DEFAULT_I_18_N_TEXT_ID);

        // Get all the i18nTextTranslationList where i18nTextId is greater than SMALLER_I_18_N_TEXT_ID
        defaultI18nTextTranslationShouldBeFound("i18nTextId.greaterThan=" + SMALLER_I_18_N_TEXT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultI18nTextTranslationShouldBeFound(String filter) throws Exception {
        restI18nTextTranslationMockMvc.perform(get("/api/i-18-n-text-translations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(i18nTextTranslation.getId().intValue())))
            .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG)))
            .andExpect(jsonPath("$.[*].translation").value(hasItem(DEFAULT_TRANSLATION)))
            .andExpect(jsonPath("$.[*].i18nTextId").value(hasItem(DEFAULT_I_18_N_TEXT_ID)));

        // Check, that the count call also returns 1
        restI18nTextTranslationMockMvc.perform(get("/api/i-18-n-text-translations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultI18nTextTranslationShouldNotBeFound(String filter) throws Exception {
        restI18nTextTranslationMockMvc.perform(get("/api/i-18-n-text-translations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restI18nTextTranslationMockMvc.perform(get("/api/i-18-n-text-translations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingI18nTextTranslation() throws Exception {
        // Get the i18nTextTranslation
        restI18nTextTranslationMockMvc.perform(get("/api/i-18-n-text-translations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateI18nTextTranslation() throws Exception {
        // Initialize the database
        i18nTextTranslationService.save(i18nTextTranslation);

        int databaseSizeBeforeUpdate = i18nTextTranslationRepository.findAll().size();

        // Update the i18nTextTranslation
        I18nTextTranslation updatedI18nTextTranslation = i18nTextTranslationRepository.findById(i18nTextTranslation.getId()).get();
        // Disconnect from session so that the updates on updatedI18nTextTranslation are not directly saved in db
        em.detach(updatedI18nTextTranslation);
        updatedI18nTextTranslation
            .lang(UPDATED_LANG)
            .translation(UPDATED_TRANSLATION)
            .i18nTextId(UPDATED_I_18_N_TEXT_ID);

        restI18nTextTranslationMockMvc.perform(put("/api/i-18-n-text-translations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedI18nTextTranslation)))
            .andExpect(status().isOk());

        // Validate the I18nTextTranslation in the database
        List<I18nTextTranslation> i18nTextTranslationList = i18nTextTranslationRepository.findAll();
        assertThat(i18nTextTranslationList).hasSize(databaseSizeBeforeUpdate);
        I18nTextTranslation testI18nTextTranslation = i18nTextTranslationList.get(i18nTextTranslationList.size() - 1);
        assertThat(testI18nTextTranslation.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testI18nTextTranslation.getTranslation()).isEqualTo(UPDATED_TRANSLATION);
        assertThat(testI18nTextTranslation.geti18nTextId()).isEqualTo(UPDATED_I_18_N_TEXT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingI18nTextTranslation() throws Exception {
        int databaseSizeBeforeUpdate = i18nTextTranslationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restI18nTextTranslationMockMvc.perform(put("/api/i-18-n-text-translations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(i18nTextTranslation)))
            .andExpect(status().isBadRequest());

        // Validate the I18nTextTranslation in the database
        List<I18nTextTranslation> i18nTextTranslationList = i18nTextTranslationRepository.findAll();
        assertThat(i18nTextTranslationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteI18nTextTranslation() throws Exception {
        // Initialize the database
        i18nTextTranslationService.save(i18nTextTranslation);

        int databaseSizeBeforeDelete = i18nTextTranslationRepository.findAll().size();

        // Delete the i18nTextTranslation
        restI18nTextTranslationMockMvc.perform(delete("/api/i-18-n-text-translations/{id}", i18nTextTranslation.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<I18nTextTranslation> i18nTextTranslationList = i18nTextTranslationRepository.findAll();
        assertThat(i18nTextTranslationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
