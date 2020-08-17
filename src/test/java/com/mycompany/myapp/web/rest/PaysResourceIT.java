package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Pays;
import com.mycompany.myapp.repository.PaysRepository;
import com.mycompany.myapp.service.PaysService;
import com.mycompany.myapp.service.dto.PaysCriteria;
import com.mycompany.myapp.service.PaysQueryService;

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
 * Integration tests for the {@link PaysResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class PaysResourceIT {

    private static final Integer DEFAULT_IDPAYS = 1;
    private static final Integer UPDATED_IDPAYS = 2;
    private static final Integer SMALLER_IDPAYS = 1 - 1;

    private static final String DEFAULT_CODEPAYS = "AAAAAAAAAA";
    private static final String UPDATED_CODEPAYS = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    @Autowired
    private PaysRepository paysRepository;

    @Autowired
    private PaysService paysService;

    @Autowired
    private PaysQueryService paysQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaysMockMvc;

    private Pays pays;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pays createEntity(EntityManager em) {
        Pays pays = new Pays()
            .idpays(DEFAULT_IDPAYS)
            .codepays(DEFAULT_CODEPAYS)
            .designation(DEFAULT_DESIGNATION);
        return pays;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pays createUpdatedEntity(EntityManager em) {
        Pays pays = new Pays()
            .idpays(UPDATED_IDPAYS)
            .codepays(UPDATED_CODEPAYS)
            .designation(UPDATED_DESIGNATION);
        return pays;
    }

    @BeforeEach
    public void initTest() {
        pays = createEntity(em);
    }

    @Test
    @Transactional
    public void createPays() throws Exception {
        int databaseSizeBeforeCreate = paysRepository.findAll().size();
        // Create the Pays
        restPaysMockMvc.perform(post("/api/pays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pays)))
            .andExpect(status().isCreated());

        // Validate the Pays in the database
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeCreate + 1);
        Pays testPays = paysList.get(paysList.size() - 1);
        assertThat(testPays.getIdpays()).isEqualTo(DEFAULT_IDPAYS);
        assertThat(testPays.getCodepays()).isEqualTo(DEFAULT_CODEPAYS);
        assertThat(testPays.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    @Transactional
    public void createPaysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paysRepository.findAll().size();

        // Create the Pays with an existing ID
        pays.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaysMockMvc.perform(post("/api/pays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pays)))
            .andExpect(status().isBadRequest());

        // Validate the Pays in the database
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdpaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = paysRepository.findAll().size();
        // set the field null
        pays.setIdpays(null);

        // Create the Pays, which fails.


        restPaysMockMvc.perform(post("/api/pays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pays)))
            .andExpect(status().isBadRequest());

        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPays() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList
        restPaysMockMvc.perform(get("/api/pays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pays.getId().intValue())))
            .andExpect(jsonPath("$.[*].idpays").value(hasItem(DEFAULT_IDPAYS)))
            .andExpect(jsonPath("$.[*].codepays").value(hasItem(DEFAULT_CODEPAYS)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }
    
    @Test
    @Transactional
    public void getPays() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get the pays
        restPaysMockMvc.perform(get("/api/pays/{id}", pays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pays.getId().intValue()))
            .andExpect(jsonPath("$.idpays").value(DEFAULT_IDPAYS))
            .andExpect(jsonPath("$.codepays").value(DEFAULT_CODEPAYS))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }


    @Test
    @Transactional
    public void getPaysByIdFiltering() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        Long id = pays.getId();

        defaultPaysShouldBeFound("id.equals=" + id);
        defaultPaysShouldNotBeFound("id.notEquals=" + id);

        defaultPaysShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaysShouldNotBeFound("id.greaterThan=" + id);

        defaultPaysShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaysShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaysByIdpaysIsEqualToSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where idpays equals to DEFAULT_IDPAYS
        defaultPaysShouldBeFound("idpays.equals=" + DEFAULT_IDPAYS);

        // Get all the paysList where idpays equals to UPDATED_IDPAYS
        defaultPaysShouldNotBeFound("idpays.equals=" + UPDATED_IDPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByIdpaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where idpays not equals to DEFAULT_IDPAYS
        defaultPaysShouldNotBeFound("idpays.notEquals=" + DEFAULT_IDPAYS);

        // Get all the paysList where idpays not equals to UPDATED_IDPAYS
        defaultPaysShouldBeFound("idpays.notEquals=" + UPDATED_IDPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByIdpaysIsInShouldWork() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where idpays in DEFAULT_IDPAYS or UPDATED_IDPAYS
        defaultPaysShouldBeFound("idpays.in=" + DEFAULT_IDPAYS + "," + UPDATED_IDPAYS);

        // Get all the paysList where idpays equals to UPDATED_IDPAYS
        defaultPaysShouldNotBeFound("idpays.in=" + UPDATED_IDPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByIdpaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where idpays is not null
        defaultPaysShouldBeFound("idpays.specified=true");

        // Get all the paysList where idpays is null
        defaultPaysShouldNotBeFound("idpays.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaysByIdpaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where idpays is greater than or equal to DEFAULT_IDPAYS
        defaultPaysShouldBeFound("idpays.greaterThanOrEqual=" + DEFAULT_IDPAYS);

        // Get all the paysList where idpays is greater than or equal to UPDATED_IDPAYS
        defaultPaysShouldNotBeFound("idpays.greaterThanOrEqual=" + UPDATED_IDPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByIdpaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where idpays is less than or equal to DEFAULT_IDPAYS
        defaultPaysShouldBeFound("idpays.lessThanOrEqual=" + DEFAULT_IDPAYS);

        // Get all the paysList where idpays is less than or equal to SMALLER_IDPAYS
        defaultPaysShouldNotBeFound("idpays.lessThanOrEqual=" + SMALLER_IDPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByIdpaysIsLessThanSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where idpays is less than DEFAULT_IDPAYS
        defaultPaysShouldNotBeFound("idpays.lessThan=" + DEFAULT_IDPAYS);

        // Get all the paysList where idpays is less than UPDATED_IDPAYS
        defaultPaysShouldBeFound("idpays.lessThan=" + UPDATED_IDPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByIdpaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where idpays is greater than DEFAULT_IDPAYS
        defaultPaysShouldNotBeFound("idpays.greaterThan=" + DEFAULT_IDPAYS);

        // Get all the paysList where idpays is greater than SMALLER_IDPAYS
        defaultPaysShouldBeFound("idpays.greaterThan=" + SMALLER_IDPAYS);
    }


    @Test
    @Transactional
    public void getAllPaysByCodepaysIsEqualToSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where codepays equals to DEFAULT_CODEPAYS
        defaultPaysShouldBeFound("codepays.equals=" + DEFAULT_CODEPAYS);

        // Get all the paysList where codepays equals to UPDATED_CODEPAYS
        defaultPaysShouldNotBeFound("codepays.equals=" + UPDATED_CODEPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByCodepaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where codepays not equals to DEFAULT_CODEPAYS
        defaultPaysShouldNotBeFound("codepays.notEquals=" + DEFAULT_CODEPAYS);

        // Get all the paysList where codepays not equals to UPDATED_CODEPAYS
        defaultPaysShouldBeFound("codepays.notEquals=" + UPDATED_CODEPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByCodepaysIsInShouldWork() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where codepays in DEFAULT_CODEPAYS or UPDATED_CODEPAYS
        defaultPaysShouldBeFound("codepays.in=" + DEFAULT_CODEPAYS + "," + UPDATED_CODEPAYS);

        // Get all the paysList where codepays equals to UPDATED_CODEPAYS
        defaultPaysShouldNotBeFound("codepays.in=" + UPDATED_CODEPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByCodepaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where codepays is not null
        defaultPaysShouldBeFound("codepays.specified=true");

        // Get all the paysList where codepays is null
        defaultPaysShouldNotBeFound("codepays.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaysByCodepaysContainsSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where codepays contains DEFAULT_CODEPAYS
        defaultPaysShouldBeFound("codepays.contains=" + DEFAULT_CODEPAYS);

        // Get all the paysList where codepays contains UPDATED_CODEPAYS
        defaultPaysShouldNotBeFound("codepays.contains=" + UPDATED_CODEPAYS);
    }

    @Test
    @Transactional
    public void getAllPaysByCodepaysNotContainsSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where codepays does not contain DEFAULT_CODEPAYS
        defaultPaysShouldNotBeFound("codepays.doesNotContain=" + DEFAULT_CODEPAYS);

        // Get all the paysList where codepays does not contain UPDATED_CODEPAYS
        defaultPaysShouldBeFound("codepays.doesNotContain=" + UPDATED_CODEPAYS);
    }


    @Test
    @Transactional
    public void getAllPaysByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where designation equals to DEFAULT_DESIGNATION
        defaultPaysShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the paysList where designation equals to UPDATED_DESIGNATION
        defaultPaysShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllPaysByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where designation not equals to DEFAULT_DESIGNATION
        defaultPaysShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the paysList where designation not equals to UPDATED_DESIGNATION
        defaultPaysShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllPaysByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultPaysShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the paysList where designation equals to UPDATED_DESIGNATION
        defaultPaysShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllPaysByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where designation is not null
        defaultPaysShouldBeFound("designation.specified=true");

        // Get all the paysList where designation is null
        defaultPaysShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaysByDesignationContainsSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where designation contains DEFAULT_DESIGNATION
        defaultPaysShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the paysList where designation contains UPDATED_DESIGNATION
        defaultPaysShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllPaysByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        paysRepository.saveAndFlush(pays);

        // Get all the paysList where designation does not contain DEFAULT_DESIGNATION
        defaultPaysShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the paysList where designation does not contain UPDATED_DESIGNATION
        defaultPaysShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaysShouldBeFound(String filter) throws Exception {
        restPaysMockMvc.perform(get("/api/pays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pays.getId().intValue())))
            .andExpect(jsonPath("$.[*].idpays").value(hasItem(DEFAULT_IDPAYS)))
            .andExpect(jsonPath("$.[*].codepays").value(hasItem(DEFAULT_CODEPAYS)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));

        // Check, that the count call also returns 1
        restPaysMockMvc.perform(get("/api/pays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaysShouldNotBeFound(String filter) throws Exception {
        restPaysMockMvc.perform(get("/api/pays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaysMockMvc.perform(get("/api/pays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPays() throws Exception {
        // Get the pays
        restPaysMockMvc.perform(get("/api/pays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePays() throws Exception {
        // Initialize the database
        paysService.save(pays);

        int databaseSizeBeforeUpdate = paysRepository.findAll().size();

        // Update the pays
        Pays updatedPays = paysRepository.findById(pays.getId()).get();
        // Disconnect from session so that the updates on updatedPays are not directly saved in db
        em.detach(updatedPays);
        updatedPays
            .idpays(UPDATED_IDPAYS)
            .codepays(UPDATED_CODEPAYS)
            .designation(UPDATED_DESIGNATION);

        restPaysMockMvc.perform(put("/api/pays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPays)))
            .andExpect(status().isOk());

        // Validate the Pays in the database
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeUpdate);
        Pays testPays = paysList.get(paysList.size() - 1);
        assertThat(testPays.getIdpays()).isEqualTo(UPDATED_IDPAYS);
        assertThat(testPays.getCodepays()).isEqualTo(UPDATED_CODEPAYS);
        assertThat(testPays.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void updateNonExistingPays() throws Exception {
        int databaseSizeBeforeUpdate = paysRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaysMockMvc.perform(put("/api/pays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pays)))
            .andExpect(status().isBadRequest());

        // Validate the Pays in the database
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePays() throws Exception {
        // Initialize the database
        paysService.save(pays);

        int databaseSizeBeforeDelete = paysRepository.findAll().size();

        // Delete the pays
        restPaysMockMvc.perform(delete("/api/pays/{id}", pays.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pays> paysList = paysRepository.findAll();
        assertThat(paysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
