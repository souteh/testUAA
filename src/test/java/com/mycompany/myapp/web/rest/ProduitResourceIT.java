package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.repository.ProduitRepository;
import com.mycompany.myapp.service.ProduitService;
import com.mycompany.myapp.service.dto.ProduitCriteria;
import com.mycompany.myapp.service.ProduitQueryService;

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
 * Integration tests for the {@link ProduitResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProduitResourceIT {

    private static final Integer DEFAULT_IDPRODUIT = 1;
    private static final Integer UPDATED_IDPRODUIT = 2;
    private static final Integer SMALLER_IDPRODUIT = 1 - 1;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODEPRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_CODEPRODUIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ENJEUMIN = 1;
    private static final Integer UPDATED_ENJEUMIN = 2;
    private static final Integer SMALLER_ENJEUMIN = 1 - 1;

    private static final String DEFAULT_ORDRE = "AAAAAAAAAA";
    private static final String UPDATED_ORDRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NBRECHEVAUXBASE = 1;
    private static final Integer UPDATED_NBRECHEVAUXBASE = 2;
    private static final Integer SMALLER_NBRECHEVAUXBASE = 1 - 1;

    private static final Integer DEFAULT_NBREMINPARTANT = 1;
    private static final Integer UPDATED_NBREMINPARTANT = 2;
    private static final Integer SMALLER_NBREMINPARTANT = 1 - 1;

    private static final Integer DEFAULT_CHAMPX = 1;
    private static final Integer UPDATED_CHAMPX = 2;
    private static final Integer SMALLER_CHAMPX = 1 - 1;

    private static final Integer DEFAULT_EXPRESS = 1;
    private static final Integer UPDATED_EXPRESS = 2;
    private static final Integer SMALLER_EXPRESS = 1 - 1;

    private static final String DEFAULT_APPARTENANCE = "AAAAAAAAAA";
    private static final String UPDATED_APPARTENANCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ENJEUMAX = 1;
    private static final Integer UPDATED_ENJEUMAX = 2;
    private static final Integer SMALLER_ENJEUMAX = 1 - 1;

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private ProduitQueryService produitQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduitMockMvc;

    private Produit produit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createEntity(EntityManager em) {
        Produit produit = new Produit()
            .idproduit(DEFAULT_IDPRODUIT)
            .designation(DEFAULT_DESIGNATION)
            .libelle(DEFAULT_LIBELLE)
            .codeproduit(DEFAULT_CODEPRODUIT)
            .enjeumin(DEFAULT_ENJEUMIN)
            .ordre(DEFAULT_ORDRE)
            .nbrechevauxbase(DEFAULT_NBRECHEVAUXBASE)
            .nbreminpartant(DEFAULT_NBREMINPARTANT)
            .champx(DEFAULT_CHAMPX)
            .express(DEFAULT_EXPRESS)
            .appartenance(DEFAULT_APPARTENANCE)
            .enjeumax(DEFAULT_ENJEUMAX)
            .statut(DEFAULT_STATUT);
        return produit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createUpdatedEntity(EntityManager em) {
        Produit produit = new Produit()
            .idproduit(UPDATED_IDPRODUIT)
            .designation(UPDATED_DESIGNATION)
            .libelle(UPDATED_LIBELLE)
            .codeproduit(UPDATED_CODEPRODUIT)
            .enjeumin(UPDATED_ENJEUMIN)
            .ordre(UPDATED_ORDRE)
            .nbrechevauxbase(UPDATED_NBRECHEVAUXBASE)
            .nbreminpartant(UPDATED_NBREMINPARTANT)
            .champx(UPDATED_CHAMPX)
            .express(UPDATED_EXPRESS)
            .appartenance(UPDATED_APPARTENANCE)
            .enjeumax(UPDATED_ENJEUMAX)
            .statut(UPDATED_STATUT);
        return produit;
    }

    @BeforeEach
    public void initTest() {
        produit = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduit() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();
        // Create the Produit
        restProduitMockMvc.perform(post("/api/produits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate + 1);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getIdproduit()).isEqualTo(DEFAULT_IDPRODUIT);
        assertThat(testProduit.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testProduit.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testProduit.getCodeproduit()).isEqualTo(DEFAULT_CODEPRODUIT);
        assertThat(testProduit.getEnjeumin()).isEqualTo(DEFAULT_ENJEUMIN);
        assertThat(testProduit.getOrdre()).isEqualTo(DEFAULT_ORDRE);
        assertThat(testProduit.getNbrechevauxbase()).isEqualTo(DEFAULT_NBRECHEVAUXBASE);
        assertThat(testProduit.getNbreminpartant()).isEqualTo(DEFAULT_NBREMINPARTANT);
        assertThat(testProduit.getChampx()).isEqualTo(DEFAULT_CHAMPX);
        assertThat(testProduit.getExpress()).isEqualTo(DEFAULT_EXPRESS);
        assertThat(testProduit.getAppartenance()).isEqualTo(DEFAULT_APPARTENANCE);
        assertThat(testProduit.getEnjeumax()).isEqualTo(DEFAULT_ENJEUMAX);
        assertThat(testProduit.getStatut()).isEqualTo(DEFAULT_STATUT);
    }

    @Test
    @Transactional
    public void createProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit with an existing ID
        produit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc.perform(post("/api/produits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdproduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setIdproduit(null);

        // Create the Produit, which fails.


        restProduitMockMvc.perform(post("/api/produits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProduits() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].idproduit").value(hasItem(DEFAULT_IDPRODUIT)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].codeproduit").value(hasItem(DEFAULT_CODEPRODUIT)))
            .andExpect(jsonPath("$.[*].enjeumin").value(hasItem(DEFAULT_ENJEUMIN)))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE)))
            .andExpect(jsonPath("$.[*].nbrechevauxbase").value(hasItem(DEFAULT_NBRECHEVAUXBASE)))
            .andExpect(jsonPath("$.[*].nbreminpartant").value(hasItem(DEFAULT_NBREMINPARTANT)))
            .andExpect(jsonPath("$.[*].champx").value(hasItem(DEFAULT_CHAMPX)))
            .andExpect(jsonPath("$.[*].express").value(hasItem(DEFAULT_EXPRESS)))
            .andExpect(jsonPath("$.[*].appartenance").value(hasItem(DEFAULT_APPARTENANCE)))
            .andExpect(jsonPath("$.[*].enjeumax").value(hasItem(DEFAULT_ENJEUMAX)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));
    }
    
    @Test
    @Transactional
    public void getProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produit.getId().intValue()))
            .andExpect(jsonPath("$.idproduit").value(DEFAULT_IDPRODUIT))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.codeproduit").value(DEFAULT_CODEPRODUIT))
            .andExpect(jsonPath("$.enjeumin").value(DEFAULT_ENJEUMIN))
            .andExpect(jsonPath("$.ordre").value(DEFAULT_ORDRE))
            .andExpect(jsonPath("$.nbrechevauxbase").value(DEFAULT_NBRECHEVAUXBASE))
            .andExpect(jsonPath("$.nbreminpartant").value(DEFAULT_NBREMINPARTANT))
            .andExpect(jsonPath("$.champx").value(DEFAULT_CHAMPX))
            .andExpect(jsonPath("$.express").value(DEFAULT_EXPRESS))
            .andExpect(jsonPath("$.appartenance").value(DEFAULT_APPARTENANCE))
            .andExpect(jsonPath("$.enjeumax").value(DEFAULT_ENJEUMAX))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT));
    }


    @Test
    @Transactional
    public void getProduitsByIdFiltering() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        Long id = produit.getId();

        defaultProduitShouldBeFound("id.equals=" + id);
        defaultProduitShouldNotBeFound("id.notEquals=" + id);

        defaultProduitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProduitShouldNotBeFound("id.greaterThan=" + id);

        defaultProduitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProduitShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProduitsByIdproduitIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idproduit equals to DEFAULT_IDPRODUIT
        defaultProduitShouldBeFound("idproduit.equals=" + DEFAULT_IDPRODUIT);

        // Get all the produitList where idproduit equals to UPDATED_IDPRODUIT
        defaultProduitShouldNotBeFound("idproduit.equals=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByIdproduitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idproduit not equals to DEFAULT_IDPRODUIT
        defaultProduitShouldNotBeFound("idproduit.notEquals=" + DEFAULT_IDPRODUIT);

        // Get all the produitList where idproduit not equals to UPDATED_IDPRODUIT
        defaultProduitShouldBeFound("idproduit.notEquals=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByIdproduitIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idproduit in DEFAULT_IDPRODUIT or UPDATED_IDPRODUIT
        defaultProduitShouldBeFound("idproduit.in=" + DEFAULT_IDPRODUIT + "," + UPDATED_IDPRODUIT);

        // Get all the produitList where idproduit equals to UPDATED_IDPRODUIT
        defaultProduitShouldNotBeFound("idproduit.in=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByIdproduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idproduit is not null
        defaultProduitShouldBeFound("idproduit.specified=true");

        // Get all the produitList where idproduit is null
        defaultProduitShouldNotBeFound("idproduit.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByIdproduitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idproduit is greater than or equal to DEFAULT_IDPRODUIT
        defaultProduitShouldBeFound("idproduit.greaterThanOrEqual=" + DEFAULT_IDPRODUIT);

        // Get all the produitList where idproduit is greater than or equal to UPDATED_IDPRODUIT
        defaultProduitShouldNotBeFound("idproduit.greaterThanOrEqual=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByIdproduitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idproduit is less than or equal to DEFAULT_IDPRODUIT
        defaultProduitShouldBeFound("idproduit.lessThanOrEqual=" + DEFAULT_IDPRODUIT);

        // Get all the produitList where idproduit is less than or equal to SMALLER_IDPRODUIT
        defaultProduitShouldNotBeFound("idproduit.lessThanOrEqual=" + SMALLER_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByIdproduitIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idproduit is less than DEFAULT_IDPRODUIT
        defaultProduitShouldNotBeFound("idproduit.lessThan=" + DEFAULT_IDPRODUIT);

        // Get all the produitList where idproduit is less than UPDATED_IDPRODUIT
        defaultProduitShouldBeFound("idproduit.lessThan=" + UPDATED_IDPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByIdproduitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idproduit is greater than DEFAULT_IDPRODUIT
        defaultProduitShouldNotBeFound("idproduit.greaterThan=" + DEFAULT_IDPRODUIT);

        // Get all the produitList where idproduit is greater than SMALLER_IDPRODUIT
        defaultProduitShouldBeFound("idproduit.greaterThan=" + SMALLER_IDPRODUIT);
    }


    @Test
    @Transactional
    public void getAllProduitsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where designation equals to DEFAULT_DESIGNATION
        defaultProduitShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the produitList where designation equals to UPDATED_DESIGNATION
        defaultProduitShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where designation not equals to DEFAULT_DESIGNATION
        defaultProduitShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the produitList where designation not equals to UPDATED_DESIGNATION
        defaultProduitShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultProduitShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the produitList where designation equals to UPDATED_DESIGNATION
        defaultProduitShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where designation is not null
        defaultProduitShouldBeFound("designation.specified=true");

        // Get all the produitList where designation is null
        defaultProduitShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where designation contains DEFAULT_DESIGNATION
        defaultProduitShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the produitList where designation contains UPDATED_DESIGNATION
        defaultProduitShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllProduitsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where designation does not contain DEFAULT_DESIGNATION
        defaultProduitShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the produitList where designation does not contain UPDATED_DESIGNATION
        defaultProduitShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }


    @Test
    @Transactional
    public void getAllProduitsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle equals to DEFAULT_LIBELLE
        defaultProduitShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the produitList where libelle equals to UPDATED_LIBELLE
        defaultProduitShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllProduitsByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle not equals to DEFAULT_LIBELLE
        defaultProduitShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the produitList where libelle not equals to UPDATED_LIBELLE
        defaultProduitShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllProduitsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultProduitShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the produitList where libelle equals to UPDATED_LIBELLE
        defaultProduitShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllProduitsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle is not null
        defaultProduitShouldBeFound("libelle.specified=true");

        // Get all the produitList where libelle is null
        defaultProduitShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle contains DEFAULT_LIBELLE
        defaultProduitShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the produitList where libelle contains UPDATED_LIBELLE
        defaultProduitShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllProduitsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle does not contain DEFAULT_LIBELLE
        defaultProduitShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the produitList where libelle does not contain UPDATED_LIBELLE
        defaultProduitShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }


    @Test
    @Transactional
    public void getAllProduitsByCodeproduitIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeproduit equals to DEFAULT_CODEPRODUIT
        defaultProduitShouldBeFound("codeproduit.equals=" + DEFAULT_CODEPRODUIT);

        // Get all the produitList where codeproduit equals to UPDATED_CODEPRODUIT
        defaultProduitShouldNotBeFound("codeproduit.equals=" + UPDATED_CODEPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByCodeproduitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeproduit not equals to DEFAULT_CODEPRODUIT
        defaultProduitShouldNotBeFound("codeproduit.notEquals=" + DEFAULT_CODEPRODUIT);

        // Get all the produitList where codeproduit not equals to UPDATED_CODEPRODUIT
        defaultProduitShouldBeFound("codeproduit.notEquals=" + UPDATED_CODEPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByCodeproduitIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeproduit in DEFAULT_CODEPRODUIT or UPDATED_CODEPRODUIT
        defaultProduitShouldBeFound("codeproduit.in=" + DEFAULT_CODEPRODUIT + "," + UPDATED_CODEPRODUIT);

        // Get all the produitList where codeproduit equals to UPDATED_CODEPRODUIT
        defaultProduitShouldNotBeFound("codeproduit.in=" + UPDATED_CODEPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByCodeproduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeproduit is not null
        defaultProduitShouldBeFound("codeproduit.specified=true");

        // Get all the produitList where codeproduit is null
        defaultProduitShouldNotBeFound("codeproduit.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByCodeproduitContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeproduit contains DEFAULT_CODEPRODUIT
        defaultProduitShouldBeFound("codeproduit.contains=" + DEFAULT_CODEPRODUIT);

        // Get all the produitList where codeproduit contains UPDATED_CODEPRODUIT
        defaultProduitShouldNotBeFound("codeproduit.contains=" + UPDATED_CODEPRODUIT);
    }

    @Test
    @Transactional
    public void getAllProduitsByCodeproduitNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where codeproduit does not contain DEFAULT_CODEPRODUIT
        defaultProduitShouldNotBeFound("codeproduit.doesNotContain=" + DEFAULT_CODEPRODUIT);

        // Get all the produitList where codeproduit does not contain UPDATED_CODEPRODUIT
        defaultProduitShouldBeFound("codeproduit.doesNotContain=" + UPDATED_CODEPRODUIT);
    }


    @Test
    @Transactional
    public void getAllProduitsByEnjeuminIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumin equals to DEFAULT_ENJEUMIN
        defaultProduitShouldBeFound("enjeumin.equals=" + DEFAULT_ENJEUMIN);

        // Get all the produitList where enjeumin equals to UPDATED_ENJEUMIN
        defaultProduitShouldNotBeFound("enjeumin.equals=" + UPDATED_ENJEUMIN);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeuminIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumin not equals to DEFAULT_ENJEUMIN
        defaultProduitShouldNotBeFound("enjeumin.notEquals=" + DEFAULT_ENJEUMIN);

        // Get all the produitList where enjeumin not equals to UPDATED_ENJEUMIN
        defaultProduitShouldBeFound("enjeumin.notEquals=" + UPDATED_ENJEUMIN);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeuminIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumin in DEFAULT_ENJEUMIN or UPDATED_ENJEUMIN
        defaultProduitShouldBeFound("enjeumin.in=" + DEFAULT_ENJEUMIN + "," + UPDATED_ENJEUMIN);

        // Get all the produitList where enjeumin equals to UPDATED_ENJEUMIN
        defaultProduitShouldNotBeFound("enjeumin.in=" + UPDATED_ENJEUMIN);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeuminIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumin is not null
        defaultProduitShouldBeFound("enjeumin.specified=true");

        // Get all the produitList where enjeumin is null
        defaultProduitShouldNotBeFound("enjeumin.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeuminIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumin is greater than or equal to DEFAULT_ENJEUMIN
        defaultProduitShouldBeFound("enjeumin.greaterThanOrEqual=" + DEFAULT_ENJEUMIN);

        // Get all the produitList where enjeumin is greater than or equal to UPDATED_ENJEUMIN
        defaultProduitShouldNotBeFound("enjeumin.greaterThanOrEqual=" + UPDATED_ENJEUMIN);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeuminIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumin is less than or equal to DEFAULT_ENJEUMIN
        defaultProduitShouldBeFound("enjeumin.lessThanOrEqual=" + DEFAULT_ENJEUMIN);

        // Get all the produitList where enjeumin is less than or equal to SMALLER_ENJEUMIN
        defaultProduitShouldNotBeFound("enjeumin.lessThanOrEqual=" + SMALLER_ENJEUMIN);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeuminIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumin is less than DEFAULT_ENJEUMIN
        defaultProduitShouldNotBeFound("enjeumin.lessThan=" + DEFAULT_ENJEUMIN);

        // Get all the produitList where enjeumin is less than UPDATED_ENJEUMIN
        defaultProduitShouldBeFound("enjeumin.lessThan=" + UPDATED_ENJEUMIN);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeuminIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumin is greater than DEFAULT_ENJEUMIN
        defaultProduitShouldNotBeFound("enjeumin.greaterThan=" + DEFAULT_ENJEUMIN);

        // Get all the produitList where enjeumin is greater than SMALLER_ENJEUMIN
        defaultProduitShouldBeFound("enjeumin.greaterThan=" + SMALLER_ENJEUMIN);
    }


    @Test
    @Transactional
    public void getAllProduitsByOrdreIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where ordre equals to DEFAULT_ORDRE
        defaultProduitShouldBeFound("ordre.equals=" + DEFAULT_ORDRE);

        // Get all the produitList where ordre equals to UPDATED_ORDRE
        defaultProduitShouldNotBeFound("ordre.equals=" + UPDATED_ORDRE);
    }

    @Test
    @Transactional
    public void getAllProduitsByOrdreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where ordre not equals to DEFAULT_ORDRE
        defaultProduitShouldNotBeFound("ordre.notEquals=" + DEFAULT_ORDRE);

        // Get all the produitList where ordre not equals to UPDATED_ORDRE
        defaultProduitShouldBeFound("ordre.notEquals=" + UPDATED_ORDRE);
    }

    @Test
    @Transactional
    public void getAllProduitsByOrdreIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where ordre in DEFAULT_ORDRE or UPDATED_ORDRE
        defaultProduitShouldBeFound("ordre.in=" + DEFAULT_ORDRE + "," + UPDATED_ORDRE);

        // Get all the produitList where ordre equals to UPDATED_ORDRE
        defaultProduitShouldNotBeFound("ordre.in=" + UPDATED_ORDRE);
    }

    @Test
    @Transactional
    public void getAllProduitsByOrdreIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where ordre is not null
        defaultProduitShouldBeFound("ordre.specified=true");

        // Get all the produitList where ordre is null
        defaultProduitShouldNotBeFound("ordre.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByOrdreContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where ordre contains DEFAULT_ORDRE
        defaultProduitShouldBeFound("ordre.contains=" + DEFAULT_ORDRE);

        // Get all the produitList where ordre contains UPDATED_ORDRE
        defaultProduitShouldNotBeFound("ordre.contains=" + UPDATED_ORDRE);
    }

    @Test
    @Transactional
    public void getAllProduitsByOrdreNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where ordre does not contain DEFAULT_ORDRE
        defaultProduitShouldNotBeFound("ordre.doesNotContain=" + DEFAULT_ORDRE);

        // Get all the produitList where ordre does not contain UPDATED_ORDRE
        defaultProduitShouldBeFound("ordre.doesNotContain=" + UPDATED_ORDRE);
    }


    @Test
    @Transactional
    public void getAllProduitsByNbrechevauxbaseIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbrechevauxbase equals to DEFAULT_NBRECHEVAUXBASE
        defaultProduitShouldBeFound("nbrechevauxbase.equals=" + DEFAULT_NBRECHEVAUXBASE);

        // Get all the produitList where nbrechevauxbase equals to UPDATED_NBRECHEVAUXBASE
        defaultProduitShouldNotBeFound("nbrechevauxbase.equals=" + UPDATED_NBRECHEVAUXBASE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbrechevauxbaseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbrechevauxbase not equals to DEFAULT_NBRECHEVAUXBASE
        defaultProduitShouldNotBeFound("nbrechevauxbase.notEquals=" + DEFAULT_NBRECHEVAUXBASE);

        // Get all the produitList where nbrechevauxbase not equals to UPDATED_NBRECHEVAUXBASE
        defaultProduitShouldBeFound("nbrechevauxbase.notEquals=" + UPDATED_NBRECHEVAUXBASE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbrechevauxbaseIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbrechevauxbase in DEFAULT_NBRECHEVAUXBASE or UPDATED_NBRECHEVAUXBASE
        defaultProduitShouldBeFound("nbrechevauxbase.in=" + DEFAULT_NBRECHEVAUXBASE + "," + UPDATED_NBRECHEVAUXBASE);

        // Get all the produitList where nbrechevauxbase equals to UPDATED_NBRECHEVAUXBASE
        defaultProduitShouldNotBeFound("nbrechevauxbase.in=" + UPDATED_NBRECHEVAUXBASE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbrechevauxbaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbrechevauxbase is not null
        defaultProduitShouldBeFound("nbrechevauxbase.specified=true");

        // Get all the produitList where nbrechevauxbase is null
        defaultProduitShouldNotBeFound("nbrechevauxbase.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByNbrechevauxbaseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbrechevauxbase is greater than or equal to DEFAULT_NBRECHEVAUXBASE
        defaultProduitShouldBeFound("nbrechevauxbase.greaterThanOrEqual=" + DEFAULT_NBRECHEVAUXBASE);

        // Get all the produitList where nbrechevauxbase is greater than or equal to UPDATED_NBRECHEVAUXBASE
        defaultProduitShouldNotBeFound("nbrechevauxbase.greaterThanOrEqual=" + UPDATED_NBRECHEVAUXBASE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbrechevauxbaseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbrechevauxbase is less than or equal to DEFAULT_NBRECHEVAUXBASE
        defaultProduitShouldBeFound("nbrechevauxbase.lessThanOrEqual=" + DEFAULT_NBRECHEVAUXBASE);

        // Get all the produitList where nbrechevauxbase is less than or equal to SMALLER_NBRECHEVAUXBASE
        defaultProduitShouldNotBeFound("nbrechevauxbase.lessThanOrEqual=" + SMALLER_NBRECHEVAUXBASE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbrechevauxbaseIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbrechevauxbase is less than DEFAULT_NBRECHEVAUXBASE
        defaultProduitShouldNotBeFound("nbrechevauxbase.lessThan=" + DEFAULT_NBRECHEVAUXBASE);

        // Get all the produitList where nbrechevauxbase is less than UPDATED_NBRECHEVAUXBASE
        defaultProduitShouldBeFound("nbrechevauxbase.lessThan=" + UPDATED_NBRECHEVAUXBASE);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbrechevauxbaseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbrechevauxbase is greater than DEFAULT_NBRECHEVAUXBASE
        defaultProduitShouldNotBeFound("nbrechevauxbase.greaterThan=" + DEFAULT_NBRECHEVAUXBASE);

        // Get all the produitList where nbrechevauxbase is greater than SMALLER_NBRECHEVAUXBASE
        defaultProduitShouldBeFound("nbrechevauxbase.greaterThan=" + SMALLER_NBRECHEVAUXBASE);
    }


    @Test
    @Transactional
    public void getAllProduitsByNbreminpartantIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbreminpartant equals to DEFAULT_NBREMINPARTANT
        defaultProduitShouldBeFound("nbreminpartant.equals=" + DEFAULT_NBREMINPARTANT);

        // Get all the produitList where nbreminpartant equals to UPDATED_NBREMINPARTANT
        defaultProduitShouldNotBeFound("nbreminpartant.equals=" + UPDATED_NBREMINPARTANT);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbreminpartantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbreminpartant not equals to DEFAULT_NBREMINPARTANT
        defaultProduitShouldNotBeFound("nbreminpartant.notEquals=" + DEFAULT_NBREMINPARTANT);

        // Get all the produitList where nbreminpartant not equals to UPDATED_NBREMINPARTANT
        defaultProduitShouldBeFound("nbreminpartant.notEquals=" + UPDATED_NBREMINPARTANT);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbreminpartantIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbreminpartant in DEFAULT_NBREMINPARTANT or UPDATED_NBREMINPARTANT
        defaultProduitShouldBeFound("nbreminpartant.in=" + DEFAULT_NBREMINPARTANT + "," + UPDATED_NBREMINPARTANT);

        // Get all the produitList where nbreminpartant equals to UPDATED_NBREMINPARTANT
        defaultProduitShouldNotBeFound("nbreminpartant.in=" + UPDATED_NBREMINPARTANT);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbreminpartantIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbreminpartant is not null
        defaultProduitShouldBeFound("nbreminpartant.specified=true");

        // Get all the produitList where nbreminpartant is null
        defaultProduitShouldNotBeFound("nbreminpartant.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByNbreminpartantIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbreminpartant is greater than or equal to DEFAULT_NBREMINPARTANT
        defaultProduitShouldBeFound("nbreminpartant.greaterThanOrEqual=" + DEFAULT_NBREMINPARTANT);

        // Get all the produitList where nbreminpartant is greater than or equal to UPDATED_NBREMINPARTANT
        defaultProduitShouldNotBeFound("nbreminpartant.greaterThanOrEqual=" + UPDATED_NBREMINPARTANT);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbreminpartantIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbreminpartant is less than or equal to DEFAULT_NBREMINPARTANT
        defaultProduitShouldBeFound("nbreminpartant.lessThanOrEqual=" + DEFAULT_NBREMINPARTANT);

        // Get all the produitList where nbreminpartant is less than or equal to SMALLER_NBREMINPARTANT
        defaultProduitShouldNotBeFound("nbreminpartant.lessThanOrEqual=" + SMALLER_NBREMINPARTANT);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbreminpartantIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbreminpartant is less than DEFAULT_NBREMINPARTANT
        defaultProduitShouldNotBeFound("nbreminpartant.lessThan=" + DEFAULT_NBREMINPARTANT);

        // Get all the produitList where nbreminpartant is less than UPDATED_NBREMINPARTANT
        defaultProduitShouldBeFound("nbreminpartant.lessThan=" + UPDATED_NBREMINPARTANT);
    }

    @Test
    @Transactional
    public void getAllProduitsByNbreminpartantIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nbreminpartant is greater than DEFAULT_NBREMINPARTANT
        defaultProduitShouldNotBeFound("nbreminpartant.greaterThan=" + DEFAULT_NBREMINPARTANT);

        // Get all the produitList where nbreminpartant is greater than SMALLER_NBREMINPARTANT
        defaultProduitShouldBeFound("nbreminpartant.greaterThan=" + SMALLER_NBREMINPARTANT);
    }


    @Test
    @Transactional
    public void getAllProduitsByChampxIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where champx equals to DEFAULT_CHAMPX
        defaultProduitShouldBeFound("champx.equals=" + DEFAULT_CHAMPX);

        // Get all the produitList where champx equals to UPDATED_CHAMPX
        defaultProduitShouldNotBeFound("champx.equals=" + UPDATED_CHAMPX);
    }

    @Test
    @Transactional
    public void getAllProduitsByChampxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where champx not equals to DEFAULT_CHAMPX
        defaultProduitShouldNotBeFound("champx.notEquals=" + DEFAULT_CHAMPX);

        // Get all the produitList where champx not equals to UPDATED_CHAMPX
        defaultProduitShouldBeFound("champx.notEquals=" + UPDATED_CHAMPX);
    }

    @Test
    @Transactional
    public void getAllProduitsByChampxIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where champx in DEFAULT_CHAMPX or UPDATED_CHAMPX
        defaultProduitShouldBeFound("champx.in=" + DEFAULT_CHAMPX + "," + UPDATED_CHAMPX);

        // Get all the produitList where champx equals to UPDATED_CHAMPX
        defaultProduitShouldNotBeFound("champx.in=" + UPDATED_CHAMPX);
    }

    @Test
    @Transactional
    public void getAllProduitsByChampxIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where champx is not null
        defaultProduitShouldBeFound("champx.specified=true");

        // Get all the produitList where champx is null
        defaultProduitShouldNotBeFound("champx.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByChampxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where champx is greater than or equal to DEFAULT_CHAMPX
        defaultProduitShouldBeFound("champx.greaterThanOrEqual=" + DEFAULT_CHAMPX);

        // Get all the produitList where champx is greater than or equal to UPDATED_CHAMPX
        defaultProduitShouldNotBeFound("champx.greaterThanOrEqual=" + UPDATED_CHAMPX);
    }

    @Test
    @Transactional
    public void getAllProduitsByChampxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where champx is less than or equal to DEFAULT_CHAMPX
        defaultProduitShouldBeFound("champx.lessThanOrEqual=" + DEFAULT_CHAMPX);

        // Get all the produitList where champx is less than or equal to SMALLER_CHAMPX
        defaultProduitShouldNotBeFound("champx.lessThanOrEqual=" + SMALLER_CHAMPX);
    }

    @Test
    @Transactional
    public void getAllProduitsByChampxIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where champx is less than DEFAULT_CHAMPX
        defaultProduitShouldNotBeFound("champx.lessThan=" + DEFAULT_CHAMPX);

        // Get all the produitList where champx is less than UPDATED_CHAMPX
        defaultProduitShouldBeFound("champx.lessThan=" + UPDATED_CHAMPX);
    }

    @Test
    @Transactional
    public void getAllProduitsByChampxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where champx is greater than DEFAULT_CHAMPX
        defaultProduitShouldNotBeFound("champx.greaterThan=" + DEFAULT_CHAMPX);

        // Get all the produitList where champx is greater than SMALLER_CHAMPX
        defaultProduitShouldBeFound("champx.greaterThan=" + SMALLER_CHAMPX);
    }


    @Test
    @Transactional
    public void getAllProduitsByExpressIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where express equals to DEFAULT_EXPRESS
        defaultProduitShouldBeFound("express.equals=" + DEFAULT_EXPRESS);

        // Get all the produitList where express equals to UPDATED_EXPRESS
        defaultProduitShouldNotBeFound("express.equals=" + UPDATED_EXPRESS);
    }

    @Test
    @Transactional
    public void getAllProduitsByExpressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where express not equals to DEFAULT_EXPRESS
        defaultProduitShouldNotBeFound("express.notEquals=" + DEFAULT_EXPRESS);

        // Get all the produitList where express not equals to UPDATED_EXPRESS
        defaultProduitShouldBeFound("express.notEquals=" + UPDATED_EXPRESS);
    }

    @Test
    @Transactional
    public void getAllProduitsByExpressIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where express in DEFAULT_EXPRESS or UPDATED_EXPRESS
        defaultProduitShouldBeFound("express.in=" + DEFAULT_EXPRESS + "," + UPDATED_EXPRESS);

        // Get all the produitList where express equals to UPDATED_EXPRESS
        defaultProduitShouldNotBeFound("express.in=" + UPDATED_EXPRESS);
    }

    @Test
    @Transactional
    public void getAllProduitsByExpressIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where express is not null
        defaultProduitShouldBeFound("express.specified=true");

        // Get all the produitList where express is null
        defaultProduitShouldNotBeFound("express.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByExpressIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where express is greater than or equal to DEFAULT_EXPRESS
        defaultProduitShouldBeFound("express.greaterThanOrEqual=" + DEFAULT_EXPRESS);

        // Get all the produitList where express is greater than or equal to UPDATED_EXPRESS
        defaultProduitShouldNotBeFound("express.greaterThanOrEqual=" + UPDATED_EXPRESS);
    }

    @Test
    @Transactional
    public void getAllProduitsByExpressIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where express is less than or equal to DEFAULT_EXPRESS
        defaultProduitShouldBeFound("express.lessThanOrEqual=" + DEFAULT_EXPRESS);

        // Get all the produitList where express is less than or equal to SMALLER_EXPRESS
        defaultProduitShouldNotBeFound("express.lessThanOrEqual=" + SMALLER_EXPRESS);
    }

    @Test
    @Transactional
    public void getAllProduitsByExpressIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where express is less than DEFAULT_EXPRESS
        defaultProduitShouldNotBeFound("express.lessThan=" + DEFAULT_EXPRESS);

        // Get all the produitList where express is less than UPDATED_EXPRESS
        defaultProduitShouldBeFound("express.lessThan=" + UPDATED_EXPRESS);
    }

    @Test
    @Transactional
    public void getAllProduitsByExpressIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where express is greater than DEFAULT_EXPRESS
        defaultProduitShouldNotBeFound("express.greaterThan=" + DEFAULT_EXPRESS);

        // Get all the produitList where express is greater than SMALLER_EXPRESS
        defaultProduitShouldBeFound("express.greaterThan=" + SMALLER_EXPRESS);
    }


    @Test
    @Transactional
    public void getAllProduitsByAppartenanceIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where appartenance equals to DEFAULT_APPARTENANCE
        defaultProduitShouldBeFound("appartenance.equals=" + DEFAULT_APPARTENANCE);

        // Get all the produitList where appartenance equals to UPDATED_APPARTENANCE
        defaultProduitShouldNotBeFound("appartenance.equals=" + UPDATED_APPARTENANCE);
    }

    @Test
    @Transactional
    public void getAllProduitsByAppartenanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where appartenance not equals to DEFAULT_APPARTENANCE
        defaultProduitShouldNotBeFound("appartenance.notEquals=" + DEFAULT_APPARTENANCE);

        // Get all the produitList where appartenance not equals to UPDATED_APPARTENANCE
        defaultProduitShouldBeFound("appartenance.notEquals=" + UPDATED_APPARTENANCE);
    }

    @Test
    @Transactional
    public void getAllProduitsByAppartenanceIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where appartenance in DEFAULT_APPARTENANCE or UPDATED_APPARTENANCE
        defaultProduitShouldBeFound("appartenance.in=" + DEFAULT_APPARTENANCE + "," + UPDATED_APPARTENANCE);

        // Get all the produitList where appartenance equals to UPDATED_APPARTENANCE
        defaultProduitShouldNotBeFound("appartenance.in=" + UPDATED_APPARTENANCE);
    }

    @Test
    @Transactional
    public void getAllProduitsByAppartenanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where appartenance is not null
        defaultProduitShouldBeFound("appartenance.specified=true");

        // Get all the produitList where appartenance is null
        defaultProduitShouldNotBeFound("appartenance.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByAppartenanceContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where appartenance contains DEFAULT_APPARTENANCE
        defaultProduitShouldBeFound("appartenance.contains=" + DEFAULT_APPARTENANCE);

        // Get all the produitList where appartenance contains UPDATED_APPARTENANCE
        defaultProduitShouldNotBeFound("appartenance.contains=" + UPDATED_APPARTENANCE);
    }

    @Test
    @Transactional
    public void getAllProduitsByAppartenanceNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where appartenance does not contain DEFAULT_APPARTENANCE
        defaultProduitShouldNotBeFound("appartenance.doesNotContain=" + DEFAULT_APPARTENANCE);

        // Get all the produitList where appartenance does not contain UPDATED_APPARTENANCE
        defaultProduitShouldBeFound("appartenance.doesNotContain=" + UPDATED_APPARTENANCE);
    }


    @Test
    @Transactional
    public void getAllProduitsByEnjeumaxIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumax equals to DEFAULT_ENJEUMAX
        defaultProduitShouldBeFound("enjeumax.equals=" + DEFAULT_ENJEUMAX);

        // Get all the produitList where enjeumax equals to UPDATED_ENJEUMAX
        defaultProduitShouldNotBeFound("enjeumax.equals=" + UPDATED_ENJEUMAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeumaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumax not equals to DEFAULT_ENJEUMAX
        defaultProduitShouldNotBeFound("enjeumax.notEquals=" + DEFAULT_ENJEUMAX);

        // Get all the produitList where enjeumax not equals to UPDATED_ENJEUMAX
        defaultProduitShouldBeFound("enjeumax.notEquals=" + UPDATED_ENJEUMAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeumaxIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumax in DEFAULT_ENJEUMAX or UPDATED_ENJEUMAX
        defaultProduitShouldBeFound("enjeumax.in=" + DEFAULT_ENJEUMAX + "," + UPDATED_ENJEUMAX);

        // Get all the produitList where enjeumax equals to UPDATED_ENJEUMAX
        defaultProduitShouldNotBeFound("enjeumax.in=" + UPDATED_ENJEUMAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeumaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumax is not null
        defaultProduitShouldBeFound("enjeumax.specified=true");

        // Get all the produitList where enjeumax is null
        defaultProduitShouldNotBeFound("enjeumax.specified=false");
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeumaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumax is greater than or equal to DEFAULT_ENJEUMAX
        defaultProduitShouldBeFound("enjeumax.greaterThanOrEqual=" + DEFAULT_ENJEUMAX);

        // Get all the produitList where enjeumax is greater than or equal to UPDATED_ENJEUMAX
        defaultProduitShouldNotBeFound("enjeumax.greaterThanOrEqual=" + UPDATED_ENJEUMAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeumaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumax is less than or equal to DEFAULT_ENJEUMAX
        defaultProduitShouldBeFound("enjeumax.lessThanOrEqual=" + DEFAULT_ENJEUMAX);

        // Get all the produitList where enjeumax is less than or equal to SMALLER_ENJEUMAX
        defaultProduitShouldNotBeFound("enjeumax.lessThanOrEqual=" + SMALLER_ENJEUMAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeumaxIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumax is less than DEFAULT_ENJEUMAX
        defaultProduitShouldNotBeFound("enjeumax.lessThan=" + DEFAULT_ENJEUMAX);

        // Get all the produitList where enjeumax is less than UPDATED_ENJEUMAX
        defaultProduitShouldBeFound("enjeumax.lessThan=" + UPDATED_ENJEUMAX);
    }

    @Test
    @Transactional
    public void getAllProduitsByEnjeumaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where enjeumax is greater than DEFAULT_ENJEUMAX
        defaultProduitShouldNotBeFound("enjeumax.greaterThan=" + DEFAULT_ENJEUMAX);

        // Get all the produitList where enjeumax is greater than SMALLER_ENJEUMAX
        defaultProduitShouldBeFound("enjeumax.greaterThan=" + SMALLER_ENJEUMAX);
    }


    @Test
    @Transactional
    public void getAllProduitsByStatutIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where statut equals to DEFAULT_STATUT
        defaultProduitShouldBeFound("statut.equals=" + DEFAULT_STATUT);

        // Get all the produitList where statut equals to UPDATED_STATUT
        defaultProduitShouldNotBeFound("statut.equals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllProduitsByStatutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where statut not equals to DEFAULT_STATUT
        defaultProduitShouldNotBeFound("statut.notEquals=" + DEFAULT_STATUT);

        // Get all the produitList where statut not equals to UPDATED_STATUT
        defaultProduitShouldBeFound("statut.notEquals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllProduitsByStatutIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where statut in DEFAULT_STATUT or UPDATED_STATUT
        defaultProduitShouldBeFound("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT);

        // Get all the produitList where statut equals to UPDATED_STATUT
        defaultProduitShouldNotBeFound("statut.in=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllProduitsByStatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where statut is not null
        defaultProduitShouldBeFound("statut.specified=true");

        // Get all the produitList where statut is null
        defaultProduitShouldNotBeFound("statut.specified=false");
    }
                @Test
    @Transactional
    public void getAllProduitsByStatutContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where statut contains DEFAULT_STATUT
        defaultProduitShouldBeFound("statut.contains=" + DEFAULT_STATUT);

        // Get all the produitList where statut contains UPDATED_STATUT
        defaultProduitShouldNotBeFound("statut.contains=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllProduitsByStatutNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where statut does not contain DEFAULT_STATUT
        defaultProduitShouldNotBeFound("statut.doesNotContain=" + DEFAULT_STATUT);

        // Get all the produitList where statut does not contain UPDATED_STATUT
        defaultProduitShouldBeFound("statut.doesNotContain=" + UPDATED_STATUT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProduitShouldBeFound(String filter) throws Exception {
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].idproduit").value(hasItem(DEFAULT_IDPRODUIT)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].codeproduit").value(hasItem(DEFAULT_CODEPRODUIT)))
            .andExpect(jsonPath("$.[*].enjeumin").value(hasItem(DEFAULT_ENJEUMIN)))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE)))
            .andExpect(jsonPath("$.[*].nbrechevauxbase").value(hasItem(DEFAULT_NBRECHEVAUXBASE)))
            .andExpect(jsonPath("$.[*].nbreminpartant").value(hasItem(DEFAULT_NBREMINPARTANT)))
            .andExpect(jsonPath("$.[*].champx").value(hasItem(DEFAULT_CHAMPX)))
            .andExpect(jsonPath("$.[*].express").value(hasItem(DEFAULT_EXPRESS)))
            .andExpect(jsonPath("$.[*].appartenance").value(hasItem(DEFAULT_APPARTENANCE)))
            .andExpect(jsonPath("$.[*].enjeumax").value(hasItem(DEFAULT_ENJEUMAX)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)));

        // Check, that the count call also returns 1
        restProduitMockMvc.perform(get("/api/produits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProduitShouldNotBeFound(String filter) throws Exception {
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProduitMockMvc.perform(get("/api/produits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduit() throws Exception {
        // Initialize the database
        produitService.save(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit
        Produit updatedProduit = produitRepository.findById(produit.getId()).get();
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .idproduit(UPDATED_IDPRODUIT)
            .designation(UPDATED_DESIGNATION)
            .libelle(UPDATED_LIBELLE)
            .codeproduit(UPDATED_CODEPRODUIT)
            .enjeumin(UPDATED_ENJEUMIN)
            .ordre(UPDATED_ORDRE)
            .nbrechevauxbase(UPDATED_NBRECHEVAUXBASE)
            .nbreminpartant(UPDATED_NBREMINPARTANT)
            .champx(UPDATED_CHAMPX)
            .express(UPDATED_EXPRESS)
            .appartenance(UPDATED_APPARTENANCE)
            .enjeumax(UPDATED_ENJEUMAX)
            .statut(UPDATED_STATUT);

        restProduitMockMvc.perform(put("/api/produits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduit)))
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getIdproduit()).isEqualTo(UPDATED_IDPRODUIT);
        assertThat(testProduit.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testProduit.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testProduit.getCodeproduit()).isEqualTo(UPDATED_CODEPRODUIT);
        assertThat(testProduit.getEnjeumin()).isEqualTo(UPDATED_ENJEUMIN);
        assertThat(testProduit.getOrdre()).isEqualTo(UPDATED_ORDRE);
        assertThat(testProduit.getNbrechevauxbase()).isEqualTo(UPDATED_NBRECHEVAUXBASE);
        assertThat(testProduit.getNbreminpartant()).isEqualTo(UPDATED_NBREMINPARTANT);
        assertThat(testProduit.getChampx()).isEqualTo(UPDATED_CHAMPX);
        assertThat(testProduit.getExpress()).isEqualTo(UPDATED_EXPRESS);
        assertThat(testProduit.getAppartenance()).isEqualTo(UPDATED_APPARTENANCE);
        assertThat(testProduit.getEnjeumax()).isEqualTo(UPDATED_ENJEUMAX);
        assertThat(testProduit.getStatut()).isEqualTo(UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void updateNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc.perform(put("/api/produits").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduit() throws Exception {
        // Initialize the database
        produitService.save(produit);

        int databaseSizeBeforeDelete = produitRepository.findAll().size();

        // Delete the produit
        restProduitMockMvc.perform(delete("/api/produits/{id}", produit.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
