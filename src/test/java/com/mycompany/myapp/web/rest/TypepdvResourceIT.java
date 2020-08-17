package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Typepdv;
import com.mycompany.myapp.repository.TypepdvRepository;
import com.mycompany.myapp.service.TypepdvService;
import com.mycompany.myapp.service.dto.TypepdvCriteria;
import com.mycompany.myapp.service.TypepdvQueryService;

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
 * Integration tests for the {@link TypepdvResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TypepdvResourceIT {

    private static final Integer DEFAULT_IDTYPEPDV = 1;
    private static final Integer UPDATED_IDTYPEPDV = 2;
    private static final Integer SMALLER_IDTYPEPDV = 1 - 1;

    private static final String DEFAULT_REFTYPEPDV = "AAAAAAAAAA";
    private static final String UPDATED_REFTYPEPDV = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NBREMAXTERMINAUX = 1;
    private static final Integer UPDATED_NBREMAXTERMINAUX = 2;
    private static final Integer SMALLER_NBREMAXTERMINAUX = 1 - 1;

    private static final Integer DEFAULT_PLAFONDPOSTPAYE = 1;
    private static final Integer UPDATED_PLAFONDPOSTPAYE = 2;
    private static final Integer SMALLER_PLAFONDPOSTPAYE = 1 - 1;

    @Autowired
    private TypepdvRepository typepdvRepository;

    @Autowired
    private TypepdvService typepdvService;

    @Autowired
    private TypepdvQueryService typepdvQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypepdvMockMvc;

    private Typepdv typepdv;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typepdv createEntity(EntityManager em) {
        Typepdv typepdv = new Typepdv()
            .idtypepdv(DEFAULT_IDTYPEPDV)
            .reftypepdv(DEFAULT_REFTYPEPDV)
            .type(DEFAULT_TYPE)
            .nbremaxterminaux(DEFAULT_NBREMAXTERMINAUX)
            .plafondpostpaye(DEFAULT_PLAFONDPOSTPAYE);
        return typepdv;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Typepdv createUpdatedEntity(EntityManager em) {
        Typepdv typepdv = new Typepdv()
            .idtypepdv(UPDATED_IDTYPEPDV)
            .reftypepdv(UPDATED_REFTYPEPDV)
            .type(UPDATED_TYPE)
            .nbremaxterminaux(UPDATED_NBREMAXTERMINAUX)
            .plafondpostpaye(UPDATED_PLAFONDPOSTPAYE);
        return typepdv;
    }

    @BeforeEach
    public void initTest() {
        typepdv = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypepdv() throws Exception {
        int databaseSizeBeforeCreate = typepdvRepository.findAll().size();
        // Create the Typepdv
        restTypepdvMockMvc.perform(post("/api/typepdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typepdv)))
            .andExpect(status().isCreated());

        // Validate the Typepdv in the database
        List<Typepdv> typepdvList = typepdvRepository.findAll();
        assertThat(typepdvList).hasSize(databaseSizeBeforeCreate + 1);
        Typepdv testTypepdv = typepdvList.get(typepdvList.size() - 1);
        assertThat(testTypepdv.getIdtypepdv()).isEqualTo(DEFAULT_IDTYPEPDV);
        assertThat(testTypepdv.getReftypepdv()).isEqualTo(DEFAULT_REFTYPEPDV);
        assertThat(testTypepdv.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTypepdv.getNbremaxterminaux()).isEqualTo(DEFAULT_NBREMAXTERMINAUX);
        assertThat(testTypepdv.getPlafondpostpaye()).isEqualTo(DEFAULT_PLAFONDPOSTPAYE);
    }

    @Test
    @Transactional
    public void createTypepdvWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typepdvRepository.findAll().size();

        // Create the Typepdv with an existing ID
        typepdv.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypepdvMockMvc.perform(post("/api/typepdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typepdv)))
            .andExpect(status().isBadRequest());

        // Validate the Typepdv in the database
        List<Typepdv> typepdvList = typepdvRepository.findAll();
        assertThat(typepdvList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdtypepdvIsRequired() throws Exception {
        int databaseSizeBeforeTest = typepdvRepository.findAll().size();
        // set the field null
        typepdv.setIdtypepdv(null);

        // Create the Typepdv, which fails.


        restTypepdvMockMvc.perform(post("/api/typepdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typepdv)))
            .andExpect(status().isBadRequest());

        List<Typepdv> typepdvList = typepdvRepository.findAll();
        assertThat(typepdvList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypepdvs() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList
        restTypepdvMockMvc.perform(get("/api/typepdvs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typepdv.getId().intValue())))
            .andExpect(jsonPath("$.[*].idtypepdv").value(hasItem(DEFAULT_IDTYPEPDV)))
            .andExpect(jsonPath("$.[*].reftypepdv").value(hasItem(DEFAULT_REFTYPEPDV)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].nbremaxterminaux").value(hasItem(DEFAULT_NBREMAXTERMINAUX)))
            .andExpect(jsonPath("$.[*].plafondpostpaye").value(hasItem(DEFAULT_PLAFONDPOSTPAYE)));
    }
    
    @Test
    @Transactional
    public void getTypepdv() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get the typepdv
        restTypepdvMockMvc.perform(get("/api/typepdvs/{id}", typepdv.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typepdv.getId().intValue()))
            .andExpect(jsonPath("$.idtypepdv").value(DEFAULT_IDTYPEPDV))
            .andExpect(jsonPath("$.reftypepdv").value(DEFAULT_REFTYPEPDV))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.nbremaxterminaux").value(DEFAULT_NBREMAXTERMINAUX))
            .andExpect(jsonPath("$.plafondpostpaye").value(DEFAULT_PLAFONDPOSTPAYE));
    }


    @Test
    @Transactional
    public void getTypepdvsByIdFiltering() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        Long id = typepdv.getId();

        defaultTypepdvShouldBeFound("id.equals=" + id);
        defaultTypepdvShouldNotBeFound("id.notEquals=" + id);

        defaultTypepdvShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTypepdvShouldNotBeFound("id.greaterThan=" + id);

        defaultTypepdvShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTypepdvShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTypepdvsByIdtypepdvIsEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where idtypepdv equals to DEFAULT_IDTYPEPDV
        defaultTypepdvShouldBeFound("idtypepdv.equals=" + DEFAULT_IDTYPEPDV);

        // Get all the typepdvList where idtypepdv equals to UPDATED_IDTYPEPDV
        defaultTypepdvShouldNotBeFound("idtypepdv.equals=" + UPDATED_IDTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByIdtypepdvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where idtypepdv not equals to DEFAULT_IDTYPEPDV
        defaultTypepdvShouldNotBeFound("idtypepdv.notEquals=" + DEFAULT_IDTYPEPDV);

        // Get all the typepdvList where idtypepdv not equals to UPDATED_IDTYPEPDV
        defaultTypepdvShouldBeFound("idtypepdv.notEquals=" + UPDATED_IDTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByIdtypepdvIsInShouldWork() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where idtypepdv in DEFAULT_IDTYPEPDV or UPDATED_IDTYPEPDV
        defaultTypepdvShouldBeFound("idtypepdv.in=" + DEFAULT_IDTYPEPDV + "," + UPDATED_IDTYPEPDV);

        // Get all the typepdvList where idtypepdv equals to UPDATED_IDTYPEPDV
        defaultTypepdvShouldNotBeFound("idtypepdv.in=" + UPDATED_IDTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByIdtypepdvIsNullOrNotNull() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where idtypepdv is not null
        defaultTypepdvShouldBeFound("idtypepdv.specified=true");

        // Get all the typepdvList where idtypepdv is null
        defaultTypepdvShouldNotBeFound("idtypepdv.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypepdvsByIdtypepdvIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where idtypepdv is greater than or equal to DEFAULT_IDTYPEPDV
        defaultTypepdvShouldBeFound("idtypepdv.greaterThanOrEqual=" + DEFAULT_IDTYPEPDV);

        // Get all the typepdvList where idtypepdv is greater than or equal to UPDATED_IDTYPEPDV
        defaultTypepdvShouldNotBeFound("idtypepdv.greaterThanOrEqual=" + UPDATED_IDTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByIdtypepdvIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where idtypepdv is less than or equal to DEFAULT_IDTYPEPDV
        defaultTypepdvShouldBeFound("idtypepdv.lessThanOrEqual=" + DEFAULT_IDTYPEPDV);

        // Get all the typepdvList where idtypepdv is less than or equal to SMALLER_IDTYPEPDV
        defaultTypepdvShouldNotBeFound("idtypepdv.lessThanOrEqual=" + SMALLER_IDTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByIdtypepdvIsLessThanSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where idtypepdv is less than DEFAULT_IDTYPEPDV
        defaultTypepdvShouldNotBeFound("idtypepdv.lessThan=" + DEFAULT_IDTYPEPDV);

        // Get all the typepdvList where idtypepdv is less than UPDATED_IDTYPEPDV
        defaultTypepdvShouldBeFound("idtypepdv.lessThan=" + UPDATED_IDTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByIdtypepdvIsGreaterThanSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where idtypepdv is greater than DEFAULT_IDTYPEPDV
        defaultTypepdvShouldNotBeFound("idtypepdv.greaterThan=" + DEFAULT_IDTYPEPDV);

        // Get all the typepdvList where idtypepdv is greater than SMALLER_IDTYPEPDV
        defaultTypepdvShouldBeFound("idtypepdv.greaterThan=" + SMALLER_IDTYPEPDV);
    }


    @Test
    @Transactional
    public void getAllTypepdvsByReftypepdvIsEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where reftypepdv equals to DEFAULT_REFTYPEPDV
        defaultTypepdvShouldBeFound("reftypepdv.equals=" + DEFAULT_REFTYPEPDV);

        // Get all the typepdvList where reftypepdv equals to UPDATED_REFTYPEPDV
        defaultTypepdvShouldNotBeFound("reftypepdv.equals=" + UPDATED_REFTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByReftypepdvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where reftypepdv not equals to DEFAULT_REFTYPEPDV
        defaultTypepdvShouldNotBeFound("reftypepdv.notEquals=" + DEFAULT_REFTYPEPDV);

        // Get all the typepdvList where reftypepdv not equals to UPDATED_REFTYPEPDV
        defaultTypepdvShouldBeFound("reftypepdv.notEquals=" + UPDATED_REFTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByReftypepdvIsInShouldWork() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where reftypepdv in DEFAULT_REFTYPEPDV or UPDATED_REFTYPEPDV
        defaultTypepdvShouldBeFound("reftypepdv.in=" + DEFAULT_REFTYPEPDV + "," + UPDATED_REFTYPEPDV);

        // Get all the typepdvList where reftypepdv equals to UPDATED_REFTYPEPDV
        defaultTypepdvShouldNotBeFound("reftypepdv.in=" + UPDATED_REFTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByReftypepdvIsNullOrNotNull() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where reftypepdv is not null
        defaultTypepdvShouldBeFound("reftypepdv.specified=true");

        // Get all the typepdvList where reftypepdv is null
        defaultTypepdvShouldNotBeFound("reftypepdv.specified=false");
    }
                @Test
    @Transactional
    public void getAllTypepdvsByReftypepdvContainsSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where reftypepdv contains DEFAULT_REFTYPEPDV
        defaultTypepdvShouldBeFound("reftypepdv.contains=" + DEFAULT_REFTYPEPDV);

        // Get all the typepdvList where reftypepdv contains UPDATED_REFTYPEPDV
        defaultTypepdvShouldNotBeFound("reftypepdv.contains=" + UPDATED_REFTYPEPDV);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByReftypepdvNotContainsSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where reftypepdv does not contain DEFAULT_REFTYPEPDV
        defaultTypepdvShouldNotBeFound("reftypepdv.doesNotContain=" + DEFAULT_REFTYPEPDV);

        // Get all the typepdvList where reftypepdv does not contain UPDATED_REFTYPEPDV
        defaultTypepdvShouldBeFound("reftypepdv.doesNotContain=" + UPDATED_REFTYPEPDV);
    }


    @Test
    @Transactional
    public void getAllTypepdvsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where type equals to DEFAULT_TYPE
        defaultTypepdvShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the typepdvList where type equals to UPDATED_TYPE
        defaultTypepdvShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where type not equals to DEFAULT_TYPE
        defaultTypepdvShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the typepdvList where type not equals to UPDATED_TYPE
        defaultTypepdvShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTypepdvShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the typepdvList where type equals to UPDATED_TYPE
        defaultTypepdvShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where type is not null
        defaultTypepdvShouldBeFound("type.specified=true");

        // Get all the typepdvList where type is null
        defaultTypepdvShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllTypepdvsByTypeContainsSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where type contains DEFAULT_TYPE
        defaultTypepdvShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the typepdvList where type contains UPDATED_TYPE
        defaultTypepdvShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where type does not contain DEFAULT_TYPE
        defaultTypepdvShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the typepdvList where type does not contain UPDATED_TYPE
        defaultTypepdvShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllTypepdvsByNbremaxterminauxIsEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where nbremaxterminaux equals to DEFAULT_NBREMAXTERMINAUX
        defaultTypepdvShouldBeFound("nbremaxterminaux.equals=" + DEFAULT_NBREMAXTERMINAUX);

        // Get all the typepdvList where nbremaxterminaux equals to UPDATED_NBREMAXTERMINAUX
        defaultTypepdvShouldNotBeFound("nbremaxterminaux.equals=" + UPDATED_NBREMAXTERMINAUX);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByNbremaxterminauxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where nbremaxterminaux not equals to DEFAULT_NBREMAXTERMINAUX
        defaultTypepdvShouldNotBeFound("nbremaxterminaux.notEquals=" + DEFAULT_NBREMAXTERMINAUX);

        // Get all the typepdvList where nbremaxterminaux not equals to UPDATED_NBREMAXTERMINAUX
        defaultTypepdvShouldBeFound("nbremaxterminaux.notEquals=" + UPDATED_NBREMAXTERMINAUX);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByNbremaxterminauxIsInShouldWork() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where nbremaxterminaux in DEFAULT_NBREMAXTERMINAUX or UPDATED_NBREMAXTERMINAUX
        defaultTypepdvShouldBeFound("nbremaxterminaux.in=" + DEFAULT_NBREMAXTERMINAUX + "," + UPDATED_NBREMAXTERMINAUX);

        // Get all the typepdvList where nbremaxterminaux equals to UPDATED_NBREMAXTERMINAUX
        defaultTypepdvShouldNotBeFound("nbremaxterminaux.in=" + UPDATED_NBREMAXTERMINAUX);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByNbremaxterminauxIsNullOrNotNull() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where nbremaxterminaux is not null
        defaultTypepdvShouldBeFound("nbremaxterminaux.specified=true");

        // Get all the typepdvList where nbremaxterminaux is null
        defaultTypepdvShouldNotBeFound("nbremaxterminaux.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypepdvsByNbremaxterminauxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where nbremaxterminaux is greater than or equal to DEFAULT_NBREMAXTERMINAUX
        defaultTypepdvShouldBeFound("nbremaxterminaux.greaterThanOrEqual=" + DEFAULT_NBREMAXTERMINAUX);

        // Get all the typepdvList where nbremaxterminaux is greater than or equal to UPDATED_NBREMAXTERMINAUX
        defaultTypepdvShouldNotBeFound("nbremaxterminaux.greaterThanOrEqual=" + UPDATED_NBREMAXTERMINAUX);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByNbremaxterminauxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where nbremaxterminaux is less than or equal to DEFAULT_NBREMAXTERMINAUX
        defaultTypepdvShouldBeFound("nbremaxterminaux.lessThanOrEqual=" + DEFAULT_NBREMAXTERMINAUX);

        // Get all the typepdvList where nbremaxterminaux is less than or equal to SMALLER_NBREMAXTERMINAUX
        defaultTypepdvShouldNotBeFound("nbremaxterminaux.lessThanOrEqual=" + SMALLER_NBREMAXTERMINAUX);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByNbremaxterminauxIsLessThanSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where nbremaxterminaux is less than DEFAULT_NBREMAXTERMINAUX
        defaultTypepdvShouldNotBeFound("nbremaxterminaux.lessThan=" + DEFAULT_NBREMAXTERMINAUX);

        // Get all the typepdvList where nbremaxterminaux is less than UPDATED_NBREMAXTERMINAUX
        defaultTypepdvShouldBeFound("nbremaxterminaux.lessThan=" + UPDATED_NBREMAXTERMINAUX);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByNbremaxterminauxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where nbremaxterminaux is greater than DEFAULT_NBREMAXTERMINAUX
        defaultTypepdvShouldNotBeFound("nbremaxterminaux.greaterThan=" + DEFAULT_NBREMAXTERMINAUX);

        // Get all the typepdvList where nbremaxterminaux is greater than SMALLER_NBREMAXTERMINAUX
        defaultTypepdvShouldBeFound("nbremaxterminaux.greaterThan=" + SMALLER_NBREMAXTERMINAUX);
    }


    @Test
    @Transactional
    public void getAllTypepdvsByPlafondpostpayeIsEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where plafondpostpaye equals to DEFAULT_PLAFONDPOSTPAYE
        defaultTypepdvShouldBeFound("plafondpostpaye.equals=" + DEFAULT_PLAFONDPOSTPAYE);

        // Get all the typepdvList where plafondpostpaye equals to UPDATED_PLAFONDPOSTPAYE
        defaultTypepdvShouldNotBeFound("plafondpostpaye.equals=" + UPDATED_PLAFONDPOSTPAYE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByPlafondpostpayeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where plafondpostpaye not equals to DEFAULT_PLAFONDPOSTPAYE
        defaultTypepdvShouldNotBeFound("plafondpostpaye.notEquals=" + DEFAULT_PLAFONDPOSTPAYE);

        // Get all the typepdvList where plafondpostpaye not equals to UPDATED_PLAFONDPOSTPAYE
        defaultTypepdvShouldBeFound("plafondpostpaye.notEquals=" + UPDATED_PLAFONDPOSTPAYE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByPlafondpostpayeIsInShouldWork() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where plafondpostpaye in DEFAULT_PLAFONDPOSTPAYE or UPDATED_PLAFONDPOSTPAYE
        defaultTypepdvShouldBeFound("plafondpostpaye.in=" + DEFAULT_PLAFONDPOSTPAYE + "," + UPDATED_PLAFONDPOSTPAYE);

        // Get all the typepdvList where plafondpostpaye equals to UPDATED_PLAFONDPOSTPAYE
        defaultTypepdvShouldNotBeFound("plafondpostpaye.in=" + UPDATED_PLAFONDPOSTPAYE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByPlafondpostpayeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where plafondpostpaye is not null
        defaultTypepdvShouldBeFound("plafondpostpaye.specified=true");

        // Get all the typepdvList where plafondpostpaye is null
        defaultTypepdvShouldNotBeFound("plafondpostpaye.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypepdvsByPlafondpostpayeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where plafondpostpaye is greater than or equal to DEFAULT_PLAFONDPOSTPAYE
        defaultTypepdvShouldBeFound("plafondpostpaye.greaterThanOrEqual=" + DEFAULT_PLAFONDPOSTPAYE);

        // Get all the typepdvList where plafondpostpaye is greater than or equal to UPDATED_PLAFONDPOSTPAYE
        defaultTypepdvShouldNotBeFound("plafondpostpaye.greaterThanOrEqual=" + UPDATED_PLAFONDPOSTPAYE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByPlafondpostpayeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where plafondpostpaye is less than or equal to DEFAULT_PLAFONDPOSTPAYE
        defaultTypepdvShouldBeFound("plafondpostpaye.lessThanOrEqual=" + DEFAULT_PLAFONDPOSTPAYE);

        // Get all the typepdvList where plafondpostpaye is less than or equal to SMALLER_PLAFONDPOSTPAYE
        defaultTypepdvShouldNotBeFound("plafondpostpaye.lessThanOrEqual=" + SMALLER_PLAFONDPOSTPAYE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByPlafondpostpayeIsLessThanSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where plafondpostpaye is less than DEFAULT_PLAFONDPOSTPAYE
        defaultTypepdvShouldNotBeFound("plafondpostpaye.lessThan=" + DEFAULT_PLAFONDPOSTPAYE);

        // Get all the typepdvList where plafondpostpaye is less than UPDATED_PLAFONDPOSTPAYE
        defaultTypepdvShouldBeFound("plafondpostpaye.lessThan=" + UPDATED_PLAFONDPOSTPAYE);
    }

    @Test
    @Transactional
    public void getAllTypepdvsByPlafondpostpayeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        typepdvRepository.saveAndFlush(typepdv);

        // Get all the typepdvList where plafondpostpaye is greater than DEFAULT_PLAFONDPOSTPAYE
        defaultTypepdvShouldNotBeFound("plafondpostpaye.greaterThan=" + DEFAULT_PLAFONDPOSTPAYE);

        // Get all the typepdvList where plafondpostpaye is greater than SMALLER_PLAFONDPOSTPAYE
        defaultTypepdvShouldBeFound("plafondpostpaye.greaterThan=" + SMALLER_PLAFONDPOSTPAYE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTypepdvShouldBeFound(String filter) throws Exception {
        restTypepdvMockMvc.perform(get("/api/typepdvs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typepdv.getId().intValue())))
            .andExpect(jsonPath("$.[*].idtypepdv").value(hasItem(DEFAULT_IDTYPEPDV)))
            .andExpect(jsonPath("$.[*].reftypepdv").value(hasItem(DEFAULT_REFTYPEPDV)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].nbremaxterminaux").value(hasItem(DEFAULT_NBREMAXTERMINAUX)))
            .andExpect(jsonPath("$.[*].plafondpostpaye").value(hasItem(DEFAULT_PLAFONDPOSTPAYE)));

        // Check, that the count call also returns 1
        restTypepdvMockMvc.perform(get("/api/typepdvs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTypepdvShouldNotBeFound(String filter) throws Exception {
        restTypepdvMockMvc.perform(get("/api/typepdvs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypepdvMockMvc.perform(get("/api/typepdvs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTypepdv() throws Exception {
        // Get the typepdv
        restTypepdvMockMvc.perform(get("/api/typepdvs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypepdv() throws Exception {
        // Initialize the database
        typepdvService.save(typepdv);

        int databaseSizeBeforeUpdate = typepdvRepository.findAll().size();

        // Update the typepdv
        Typepdv updatedTypepdv = typepdvRepository.findById(typepdv.getId()).get();
        // Disconnect from session so that the updates on updatedTypepdv are not directly saved in db
        em.detach(updatedTypepdv);
        updatedTypepdv
            .idtypepdv(UPDATED_IDTYPEPDV)
            .reftypepdv(UPDATED_REFTYPEPDV)
            .type(UPDATED_TYPE)
            .nbremaxterminaux(UPDATED_NBREMAXTERMINAUX)
            .plafondpostpaye(UPDATED_PLAFONDPOSTPAYE);

        restTypepdvMockMvc.perform(put("/api/typepdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypepdv)))
            .andExpect(status().isOk());

        // Validate the Typepdv in the database
        List<Typepdv> typepdvList = typepdvRepository.findAll();
        assertThat(typepdvList).hasSize(databaseSizeBeforeUpdate);
        Typepdv testTypepdv = typepdvList.get(typepdvList.size() - 1);
        assertThat(testTypepdv.getIdtypepdv()).isEqualTo(UPDATED_IDTYPEPDV);
        assertThat(testTypepdv.getReftypepdv()).isEqualTo(UPDATED_REFTYPEPDV);
        assertThat(testTypepdv.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTypepdv.getNbremaxterminaux()).isEqualTo(UPDATED_NBREMAXTERMINAUX);
        assertThat(testTypepdv.getPlafondpostpaye()).isEqualTo(UPDATED_PLAFONDPOSTPAYE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypepdv() throws Exception {
        int databaseSizeBeforeUpdate = typepdvRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypepdvMockMvc.perform(put("/api/typepdvs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typepdv)))
            .andExpect(status().isBadRequest());

        // Validate the Typepdv in the database
        List<Typepdv> typepdvList = typepdvRepository.findAll();
        assertThat(typepdvList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypepdv() throws Exception {
        // Initialize the database
        typepdvService.save(typepdv);

        int databaseSizeBeforeDelete = typepdvRepository.findAll().size();

        // Delete the typepdv
        restTypepdvMockMvc.perform(delete("/api/typepdvs/{id}", typepdv.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Typepdv> typepdvList = typepdvRepository.findAll();
        assertThat(typepdvList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
