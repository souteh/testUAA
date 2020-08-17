package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Ville;
import com.mycompany.myapp.repository.VilleRepository;
import com.mycompany.myapp.service.VilleService;
import com.mycompany.myapp.service.dto.VilleCriteria;
import com.mycompany.myapp.service.VilleQueryService;

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
 * Integration tests for the {@link VilleResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class VilleResourceIT {

    private static final Integer DEFAULT_IDVILLE = 1;
    private static final Integer UPDATED_IDVILLE = 2;
    private static final Integer SMALLER_IDVILLE = 1 - 1;

    private static final Integer DEFAULT_DESIGNATION = 1;
    private static final Integer UPDATED_DESIGNATION = 2;
    private static final Integer SMALLER_DESIGNATION = 1 - 1;

    private static final String DEFAULT_CODEVILLE = "AAAAAAAAAA";
    private static final String UPDATED_CODEVILLE = "BBBBBBBBBB";

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private VilleService villeService;

    @Autowired
    private VilleQueryService villeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVilleMockMvc;

    private Ville ville;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ville createEntity(EntityManager em) {
        Ville ville = new Ville()
            .idville(DEFAULT_IDVILLE)
            .designation(DEFAULT_DESIGNATION)
            .codeville(DEFAULT_CODEVILLE);
        return ville;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ville createUpdatedEntity(EntityManager em) {
        Ville ville = new Ville()
            .idville(UPDATED_IDVILLE)
            .designation(UPDATED_DESIGNATION)
            .codeville(UPDATED_CODEVILLE);
        return ville;
    }

    @BeforeEach
    public void initTest() {
        ville = createEntity(em);
    }

    @Test
    @Transactional
    public void createVille() throws Exception {
        int databaseSizeBeforeCreate = villeRepository.findAll().size();
        // Create the Ville
        restVilleMockMvc.perform(post("/api/villes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ville)))
            .andExpect(status().isCreated());

        // Validate the Ville in the database
        List<Ville> villeList = villeRepository.findAll();
        assertThat(villeList).hasSize(databaseSizeBeforeCreate + 1);
        Ville testVille = villeList.get(villeList.size() - 1);
        assertThat(testVille.getIdville()).isEqualTo(DEFAULT_IDVILLE);
        assertThat(testVille.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testVille.getCodeville()).isEqualTo(DEFAULT_CODEVILLE);
    }

    @Test
    @Transactional
    public void createVilleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = villeRepository.findAll().size();

        // Create the Ville with an existing ID
        ville.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVilleMockMvc.perform(post("/api/villes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ville)))
            .andExpect(status().isBadRequest());

        // Validate the Ville in the database
        List<Ville> villeList = villeRepository.findAll();
        assertThat(villeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdvilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = villeRepository.findAll().size();
        // set the field null
        ville.setIdville(null);

        // Create the Ville, which fails.


        restVilleMockMvc.perform(post("/api/villes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ville)))
            .andExpect(status().isBadRequest());

        List<Ville> villeList = villeRepository.findAll();
        assertThat(villeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVilles() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList
        restVilleMockMvc.perform(get("/api/villes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ville.getId().intValue())))
            .andExpect(jsonPath("$.[*].idville").value(hasItem(DEFAULT_IDVILLE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].codeville").value(hasItem(DEFAULT_CODEVILLE)));
    }
    
    @Test
    @Transactional
    public void getVille() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get the ville
        restVilleMockMvc.perform(get("/api/villes/{id}", ville.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ville.getId().intValue()))
            .andExpect(jsonPath("$.idville").value(DEFAULT_IDVILLE))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.codeville").value(DEFAULT_CODEVILLE));
    }


    @Test
    @Transactional
    public void getVillesByIdFiltering() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        Long id = ville.getId();

        defaultVilleShouldBeFound("id.equals=" + id);
        defaultVilleShouldNotBeFound("id.notEquals=" + id);

        defaultVilleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVilleShouldNotBeFound("id.greaterThan=" + id);

        defaultVilleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVilleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVillesByIdvilleIsEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where idville equals to DEFAULT_IDVILLE
        defaultVilleShouldBeFound("idville.equals=" + DEFAULT_IDVILLE);

        // Get all the villeList where idville equals to UPDATED_IDVILLE
        defaultVilleShouldNotBeFound("idville.equals=" + UPDATED_IDVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByIdvilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where idville not equals to DEFAULT_IDVILLE
        defaultVilleShouldNotBeFound("idville.notEquals=" + DEFAULT_IDVILLE);

        // Get all the villeList where idville not equals to UPDATED_IDVILLE
        defaultVilleShouldBeFound("idville.notEquals=" + UPDATED_IDVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByIdvilleIsInShouldWork() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where idville in DEFAULT_IDVILLE or UPDATED_IDVILLE
        defaultVilleShouldBeFound("idville.in=" + DEFAULT_IDVILLE + "," + UPDATED_IDVILLE);

        // Get all the villeList where idville equals to UPDATED_IDVILLE
        defaultVilleShouldNotBeFound("idville.in=" + UPDATED_IDVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByIdvilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where idville is not null
        defaultVilleShouldBeFound("idville.specified=true");

        // Get all the villeList where idville is null
        defaultVilleShouldNotBeFound("idville.specified=false");
    }

    @Test
    @Transactional
    public void getAllVillesByIdvilleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where idville is greater than or equal to DEFAULT_IDVILLE
        defaultVilleShouldBeFound("idville.greaterThanOrEqual=" + DEFAULT_IDVILLE);

        // Get all the villeList where idville is greater than or equal to UPDATED_IDVILLE
        defaultVilleShouldNotBeFound("idville.greaterThanOrEqual=" + UPDATED_IDVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByIdvilleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where idville is less than or equal to DEFAULT_IDVILLE
        defaultVilleShouldBeFound("idville.lessThanOrEqual=" + DEFAULT_IDVILLE);

        // Get all the villeList where idville is less than or equal to SMALLER_IDVILLE
        defaultVilleShouldNotBeFound("idville.lessThanOrEqual=" + SMALLER_IDVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByIdvilleIsLessThanSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where idville is less than DEFAULT_IDVILLE
        defaultVilleShouldNotBeFound("idville.lessThan=" + DEFAULT_IDVILLE);

        // Get all the villeList where idville is less than UPDATED_IDVILLE
        defaultVilleShouldBeFound("idville.lessThan=" + UPDATED_IDVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByIdvilleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where idville is greater than DEFAULT_IDVILLE
        defaultVilleShouldNotBeFound("idville.greaterThan=" + DEFAULT_IDVILLE);

        // Get all the villeList where idville is greater than SMALLER_IDVILLE
        defaultVilleShouldBeFound("idville.greaterThan=" + SMALLER_IDVILLE);
    }


    @Test
    @Transactional
    public void getAllVillesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where designation equals to DEFAULT_DESIGNATION
        defaultVilleShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the villeList where designation equals to UPDATED_DESIGNATION
        defaultVilleShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllVillesByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where designation not equals to DEFAULT_DESIGNATION
        defaultVilleShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the villeList where designation not equals to UPDATED_DESIGNATION
        defaultVilleShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllVillesByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultVilleShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the villeList where designation equals to UPDATED_DESIGNATION
        defaultVilleShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllVillesByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where designation is not null
        defaultVilleShouldBeFound("designation.specified=true");

        // Get all the villeList where designation is null
        defaultVilleShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    public void getAllVillesByDesignationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where designation is greater than or equal to DEFAULT_DESIGNATION
        defaultVilleShouldBeFound("designation.greaterThanOrEqual=" + DEFAULT_DESIGNATION);

        // Get all the villeList where designation is greater than or equal to UPDATED_DESIGNATION
        defaultVilleShouldNotBeFound("designation.greaterThanOrEqual=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllVillesByDesignationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where designation is less than or equal to DEFAULT_DESIGNATION
        defaultVilleShouldBeFound("designation.lessThanOrEqual=" + DEFAULT_DESIGNATION);

        // Get all the villeList where designation is less than or equal to SMALLER_DESIGNATION
        defaultVilleShouldNotBeFound("designation.lessThanOrEqual=" + SMALLER_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllVillesByDesignationIsLessThanSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where designation is less than DEFAULT_DESIGNATION
        defaultVilleShouldNotBeFound("designation.lessThan=" + DEFAULT_DESIGNATION);

        // Get all the villeList where designation is less than UPDATED_DESIGNATION
        defaultVilleShouldBeFound("designation.lessThan=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllVillesByDesignationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where designation is greater than DEFAULT_DESIGNATION
        defaultVilleShouldNotBeFound("designation.greaterThan=" + DEFAULT_DESIGNATION);

        // Get all the villeList where designation is greater than SMALLER_DESIGNATION
        defaultVilleShouldBeFound("designation.greaterThan=" + SMALLER_DESIGNATION);
    }


    @Test
    @Transactional
    public void getAllVillesByCodevilleIsEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where codeville equals to DEFAULT_CODEVILLE
        defaultVilleShouldBeFound("codeville.equals=" + DEFAULT_CODEVILLE);

        // Get all the villeList where codeville equals to UPDATED_CODEVILLE
        defaultVilleShouldNotBeFound("codeville.equals=" + UPDATED_CODEVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByCodevilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where codeville not equals to DEFAULT_CODEVILLE
        defaultVilleShouldNotBeFound("codeville.notEquals=" + DEFAULT_CODEVILLE);

        // Get all the villeList where codeville not equals to UPDATED_CODEVILLE
        defaultVilleShouldBeFound("codeville.notEquals=" + UPDATED_CODEVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByCodevilleIsInShouldWork() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where codeville in DEFAULT_CODEVILLE or UPDATED_CODEVILLE
        defaultVilleShouldBeFound("codeville.in=" + DEFAULT_CODEVILLE + "," + UPDATED_CODEVILLE);

        // Get all the villeList where codeville equals to UPDATED_CODEVILLE
        defaultVilleShouldNotBeFound("codeville.in=" + UPDATED_CODEVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByCodevilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where codeville is not null
        defaultVilleShouldBeFound("codeville.specified=true");

        // Get all the villeList where codeville is null
        defaultVilleShouldNotBeFound("codeville.specified=false");
    }
                @Test
    @Transactional
    public void getAllVillesByCodevilleContainsSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where codeville contains DEFAULT_CODEVILLE
        defaultVilleShouldBeFound("codeville.contains=" + DEFAULT_CODEVILLE);

        // Get all the villeList where codeville contains UPDATED_CODEVILLE
        defaultVilleShouldNotBeFound("codeville.contains=" + UPDATED_CODEVILLE);
    }

    @Test
    @Transactional
    public void getAllVillesByCodevilleNotContainsSomething() throws Exception {
        // Initialize the database
        villeRepository.saveAndFlush(ville);

        // Get all the villeList where codeville does not contain DEFAULT_CODEVILLE
        defaultVilleShouldNotBeFound("codeville.doesNotContain=" + DEFAULT_CODEVILLE);

        // Get all the villeList where codeville does not contain UPDATED_CODEVILLE
        defaultVilleShouldBeFound("codeville.doesNotContain=" + UPDATED_CODEVILLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVilleShouldBeFound(String filter) throws Exception {
        restVilleMockMvc.perform(get("/api/villes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ville.getId().intValue())))
            .andExpect(jsonPath("$.[*].idville").value(hasItem(DEFAULT_IDVILLE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].codeville").value(hasItem(DEFAULT_CODEVILLE)));

        // Check, that the count call also returns 1
        restVilleMockMvc.perform(get("/api/villes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVilleShouldNotBeFound(String filter) throws Exception {
        restVilleMockMvc.perform(get("/api/villes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVilleMockMvc.perform(get("/api/villes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVille() throws Exception {
        // Get the ville
        restVilleMockMvc.perform(get("/api/villes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVille() throws Exception {
        // Initialize the database
        villeService.save(ville);

        int databaseSizeBeforeUpdate = villeRepository.findAll().size();

        // Update the ville
        Ville updatedVille = villeRepository.findById(ville.getId()).get();
        // Disconnect from session so that the updates on updatedVille are not directly saved in db
        em.detach(updatedVille);
        updatedVille
            .idville(UPDATED_IDVILLE)
            .designation(UPDATED_DESIGNATION)
            .codeville(UPDATED_CODEVILLE);

        restVilleMockMvc.perform(put("/api/villes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVille)))
            .andExpect(status().isOk());

        // Validate the Ville in the database
        List<Ville> villeList = villeRepository.findAll();
        assertThat(villeList).hasSize(databaseSizeBeforeUpdate);
        Ville testVille = villeList.get(villeList.size() - 1);
        assertThat(testVille.getIdville()).isEqualTo(UPDATED_IDVILLE);
        assertThat(testVille.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testVille.getCodeville()).isEqualTo(UPDATED_CODEVILLE);
    }

    @Test
    @Transactional
    public void updateNonExistingVille() throws Exception {
        int databaseSizeBeforeUpdate = villeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVilleMockMvc.perform(put("/api/villes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ville)))
            .andExpect(status().isBadRequest());

        // Validate the Ville in the database
        List<Ville> villeList = villeRepository.findAll();
        assertThat(villeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVille() throws Exception {
        // Initialize the database
        villeService.save(ville);

        int databaseSizeBeforeDelete = villeRepository.findAll().size();

        // Delete the ville
        restVilleMockMvc.perform(delete("/api/villes/{id}", ville.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ville> villeList = villeRepository.findAll();
        assertThat(villeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
