package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Attributaire;
import com.mycompany.myapp.repository.AttributaireRepository;
import com.mycompany.myapp.service.AttributaireService;
import com.mycompany.myapp.service.dto.AttributaireCriteria;
import com.mycompany.myapp.service.AttributaireQueryService;

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
 * Integration tests for the {@link AttributaireResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class AttributaireResourceIT {

    private static final Integer DEFAULT_IDATTRIBUTAIRE = 1;
    private static final Integer UPDATED_IDATTRIBUTAIRE = 2;
    private static final Integer SMALLER_IDATTRIBUTAIRE = 1 - 1;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODEATTRIBUTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_CODEATTRIBUTAIRE = "BBBBBBBBBB";

    @Autowired
    private AttributaireRepository attributaireRepository;

    @Autowired
    private AttributaireService attributaireService;

    @Autowired
    private AttributaireQueryService attributaireQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributaireMockMvc;

    private Attributaire attributaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attributaire createEntity(EntityManager em) {
        Attributaire attributaire = new Attributaire()
            .idattributaire(DEFAULT_IDATTRIBUTAIRE)
            .libelle(DEFAULT_LIBELLE)
            .codeattributaire(DEFAULT_CODEATTRIBUTAIRE);
        return attributaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attributaire createUpdatedEntity(EntityManager em) {
        Attributaire attributaire = new Attributaire()
            .idattributaire(UPDATED_IDATTRIBUTAIRE)
            .libelle(UPDATED_LIBELLE)
            .codeattributaire(UPDATED_CODEATTRIBUTAIRE);
        return attributaire;
    }

    @BeforeEach
    public void initTest() {
        attributaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttributaire() throws Exception {
        int databaseSizeBeforeCreate = attributaireRepository.findAll().size();
        // Create the Attributaire
        restAttributaireMockMvc.perform(post("/api/attributaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attributaire)))
            .andExpect(status().isCreated());

        // Validate the Attributaire in the database
        List<Attributaire> attributaireList = attributaireRepository.findAll();
        assertThat(attributaireList).hasSize(databaseSizeBeforeCreate + 1);
        Attributaire testAttributaire = attributaireList.get(attributaireList.size() - 1);
        assertThat(testAttributaire.getIdattributaire()).isEqualTo(DEFAULT_IDATTRIBUTAIRE);
        assertThat(testAttributaire.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testAttributaire.getCodeattributaire()).isEqualTo(DEFAULT_CODEATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void createAttributaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attributaireRepository.findAll().size();

        // Create the Attributaire with an existing ID
        attributaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributaireMockMvc.perform(post("/api/attributaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attributaire)))
            .andExpect(status().isBadRequest());

        // Validate the Attributaire in the database
        List<Attributaire> attributaireList = attributaireRepository.findAll();
        assertThat(attributaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdattributaireIsRequired() throws Exception {
        int databaseSizeBeforeTest = attributaireRepository.findAll().size();
        // set the field null
        attributaire.setIdattributaire(null);

        // Create the Attributaire, which fails.


        restAttributaireMockMvc.perform(post("/api/attributaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attributaire)))
            .andExpect(status().isBadRequest());

        List<Attributaire> attributaireList = attributaireRepository.findAll();
        assertThat(attributaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttributaires() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList
        restAttributaireMockMvc.perform(get("/api/attributaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].idattributaire").value(hasItem(DEFAULT_IDATTRIBUTAIRE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].codeattributaire").value(hasItem(DEFAULT_CODEATTRIBUTAIRE)));
    }
    
    @Test
    @Transactional
    public void getAttributaire() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get the attributaire
        restAttributaireMockMvc.perform(get("/api/attributaires/{id}", attributaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributaire.getId().intValue()))
            .andExpect(jsonPath("$.idattributaire").value(DEFAULT_IDATTRIBUTAIRE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.codeattributaire").value(DEFAULT_CODEATTRIBUTAIRE));
    }


    @Test
    @Transactional
    public void getAttributairesByIdFiltering() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        Long id = attributaire.getId();

        defaultAttributaireShouldBeFound("id.equals=" + id);
        defaultAttributaireShouldNotBeFound("id.notEquals=" + id);

        defaultAttributaireShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttributaireShouldNotBeFound("id.greaterThan=" + id);

        defaultAttributaireShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttributaireShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAttributairesByIdattributaireIsEqualToSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where idattributaire equals to DEFAULT_IDATTRIBUTAIRE
        defaultAttributaireShouldBeFound("idattributaire.equals=" + DEFAULT_IDATTRIBUTAIRE);

        // Get all the attributaireList where idattributaire equals to UPDATED_IDATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("idattributaire.equals=" + UPDATED_IDATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByIdattributaireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where idattributaire not equals to DEFAULT_IDATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("idattributaire.notEquals=" + DEFAULT_IDATTRIBUTAIRE);

        // Get all the attributaireList where idattributaire not equals to UPDATED_IDATTRIBUTAIRE
        defaultAttributaireShouldBeFound("idattributaire.notEquals=" + UPDATED_IDATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByIdattributaireIsInShouldWork() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where idattributaire in DEFAULT_IDATTRIBUTAIRE or UPDATED_IDATTRIBUTAIRE
        defaultAttributaireShouldBeFound("idattributaire.in=" + DEFAULT_IDATTRIBUTAIRE + "," + UPDATED_IDATTRIBUTAIRE);

        // Get all the attributaireList where idattributaire equals to UPDATED_IDATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("idattributaire.in=" + UPDATED_IDATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByIdattributaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where idattributaire is not null
        defaultAttributaireShouldBeFound("idattributaire.specified=true");

        // Get all the attributaireList where idattributaire is null
        defaultAttributaireShouldNotBeFound("idattributaire.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttributairesByIdattributaireIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where idattributaire is greater than or equal to DEFAULT_IDATTRIBUTAIRE
        defaultAttributaireShouldBeFound("idattributaire.greaterThanOrEqual=" + DEFAULT_IDATTRIBUTAIRE);

        // Get all the attributaireList where idattributaire is greater than or equal to UPDATED_IDATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("idattributaire.greaterThanOrEqual=" + UPDATED_IDATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByIdattributaireIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where idattributaire is less than or equal to DEFAULT_IDATTRIBUTAIRE
        defaultAttributaireShouldBeFound("idattributaire.lessThanOrEqual=" + DEFAULT_IDATTRIBUTAIRE);

        // Get all the attributaireList where idattributaire is less than or equal to SMALLER_IDATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("idattributaire.lessThanOrEqual=" + SMALLER_IDATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByIdattributaireIsLessThanSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where idattributaire is less than DEFAULT_IDATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("idattributaire.lessThan=" + DEFAULT_IDATTRIBUTAIRE);

        // Get all the attributaireList where idattributaire is less than UPDATED_IDATTRIBUTAIRE
        defaultAttributaireShouldBeFound("idattributaire.lessThan=" + UPDATED_IDATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByIdattributaireIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where idattributaire is greater than DEFAULT_IDATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("idattributaire.greaterThan=" + DEFAULT_IDATTRIBUTAIRE);

        // Get all the attributaireList where idattributaire is greater than SMALLER_IDATTRIBUTAIRE
        defaultAttributaireShouldBeFound("idattributaire.greaterThan=" + SMALLER_IDATTRIBUTAIRE);
    }


    @Test
    @Transactional
    public void getAllAttributairesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where libelle equals to DEFAULT_LIBELLE
        defaultAttributaireShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the attributaireList where libelle equals to UPDATED_LIBELLE
        defaultAttributaireShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where libelle not equals to DEFAULT_LIBELLE
        defaultAttributaireShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the attributaireList where libelle not equals to UPDATED_LIBELLE
        defaultAttributaireShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultAttributaireShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the attributaireList where libelle equals to UPDATED_LIBELLE
        defaultAttributaireShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where libelle is not null
        defaultAttributaireShouldBeFound("libelle.specified=true");

        // Get all the attributaireList where libelle is null
        defaultAttributaireShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttributairesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where libelle contains DEFAULT_LIBELLE
        defaultAttributaireShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the attributaireList where libelle contains UPDATED_LIBELLE
        defaultAttributaireShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where libelle does not contain DEFAULT_LIBELLE
        defaultAttributaireShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the attributaireList where libelle does not contain UPDATED_LIBELLE
        defaultAttributaireShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }


    @Test
    @Transactional
    public void getAllAttributairesByCodeattributaireIsEqualToSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where codeattributaire equals to DEFAULT_CODEATTRIBUTAIRE
        defaultAttributaireShouldBeFound("codeattributaire.equals=" + DEFAULT_CODEATTRIBUTAIRE);

        // Get all the attributaireList where codeattributaire equals to UPDATED_CODEATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("codeattributaire.equals=" + UPDATED_CODEATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByCodeattributaireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where codeattributaire not equals to DEFAULT_CODEATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("codeattributaire.notEquals=" + DEFAULT_CODEATTRIBUTAIRE);

        // Get all the attributaireList where codeattributaire not equals to UPDATED_CODEATTRIBUTAIRE
        defaultAttributaireShouldBeFound("codeattributaire.notEquals=" + UPDATED_CODEATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByCodeattributaireIsInShouldWork() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where codeattributaire in DEFAULT_CODEATTRIBUTAIRE or UPDATED_CODEATTRIBUTAIRE
        defaultAttributaireShouldBeFound("codeattributaire.in=" + DEFAULT_CODEATTRIBUTAIRE + "," + UPDATED_CODEATTRIBUTAIRE);

        // Get all the attributaireList where codeattributaire equals to UPDATED_CODEATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("codeattributaire.in=" + UPDATED_CODEATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByCodeattributaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where codeattributaire is not null
        defaultAttributaireShouldBeFound("codeattributaire.specified=true");

        // Get all the attributaireList where codeattributaire is null
        defaultAttributaireShouldNotBeFound("codeattributaire.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttributairesByCodeattributaireContainsSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where codeattributaire contains DEFAULT_CODEATTRIBUTAIRE
        defaultAttributaireShouldBeFound("codeattributaire.contains=" + DEFAULT_CODEATTRIBUTAIRE);

        // Get all the attributaireList where codeattributaire contains UPDATED_CODEATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("codeattributaire.contains=" + UPDATED_CODEATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void getAllAttributairesByCodeattributaireNotContainsSomething() throws Exception {
        // Initialize the database
        attributaireRepository.saveAndFlush(attributaire);

        // Get all the attributaireList where codeattributaire does not contain DEFAULT_CODEATTRIBUTAIRE
        defaultAttributaireShouldNotBeFound("codeattributaire.doesNotContain=" + DEFAULT_CODEATTRIBUTAIRE);

        // Get all the attributaireList where codeattributaire does not contain UPDATED_CODEATTRIBUTAIRE
        defaultAttributaireShouldBeFound("codeattributaire.doesNotContain=" + UPDATED_CODEATTRIBUTAIRE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttributaireShouldBeFound(String filter) throws Exception {
        restAttributaireMockMvc.perform(get("/api/attributaires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].idattributaire").value(hasItem(DEFAULT_IDATTRIBUTAIRE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].codeattributaire").value(hasItem(DEFAULT_CODEATTRIBUTAIRE)));

        // Check, that the count call also returns 1
        restAttributaireMockMvc.perform(get("/api/attributaires/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttributaireShouldNotBeFound(String filter) throws Exception {
        restAttributaireMockMvc.perform(get("/api/attributaires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttributaireMockMvc.perform(get("/api/attributaires/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAttributaire() throws Exception {
        // Get the attributaire
        restAttributaireMockMvc.perform(get("/api/attributaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttributaire() throws Exception {
        // Initialize the database
        attributaireService.save(attributaire);

        int databaseSizeBeforeUpdate = attributaireRepository.findAll().size();

        // Update the attributaire
        Attributaire updatedAttributaire = attributaireRepository.findById(attributaire.getId()).get();
        // Disconnect from session so that the updates on updatedAttributaire are not directly saved in db
        em.detach(updatedAttributaire);
        updatedAttributaire
            .idattributaire(UPDATED_IDATTRIBUTAIRE)
            .libelle(UPDATED_LIBELLE)
            .codeattributaire(UPDATED_CODEATTRIBUTAIRE);

        restAttributaireMockMvc.perform(put("/api/attributaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttributaire)))
            .andExpect(status().isOk());

        // Validate the Attributaire in the database
        List<Attributaire> attributaireList = attributaireRepository.findAll();
        assertThat(attributaireList).hasSize(databaseSizeBeforeUpdate);
        Attributaire testAttributaire = attributaireList.get(attributaireList.size() - 1);
        assertThat(testAttributaire.getIdattributaire()).isEqualTo(UPDATED_IDATTRIBUTAIRE);
        assertThat(testAttributaire.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testAttributaire.getCodeattributaire()).isEqualTo(UPDATED_CODEATTRIBUTAIRE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttributaire() throws Exception {
        int databaseSizeBeforeUpdate = attributaireRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributaireMockMvc.perform(put("/api/attributaires").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attributaire)))
            .andExpect(status().isBadRequest());

        // Validate the Attributaire in the database
        List<Attributaire> attributaireList = attributaireRepository.findAll();
        assertThat(attributaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttributaire() throws Exception {
        // Initialize the database
        attributaireService.save(attributaire);

        int databaseSizeBeforeDelete = attributaireRepository.findAll().size();

        // Delete the attributaire
        restAttributaireMockMvc.perform(delete("/api/attributaires/{id}", attributaire.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attributaire> attributaireList = attributaireRepository.findAll();
        assertThat(attributaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
