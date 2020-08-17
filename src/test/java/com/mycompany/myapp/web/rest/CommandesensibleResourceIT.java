package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Commandesensible;
import com.mycompany.myapp.repository.CommandesensibleRepository;
import com.mycompany.myapp.service.CommandesensibleService;
import com.mycompany.myapp.service.dto.CommandesensibleCriteria;
import com.mycompany.myapp.service.CommandesensibleQueryService;

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
 * Integration tests for the {@link CommandesensibleResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CommandesensibleResourceIT {

    private static final Integer DEFAULT_IDCOMMANDESENSIBLE = 1;
    private static final Integer UPDATED_IDCOMMANDESENSIBLE = 2;
    private static final Integer SMALLER_IDCOMMANDESENSIBLE = 1 - 1;

    private static final String DEFAULT_CODECOMMANDESENSIBLE = "AAAAAAAAAA";
    private static final String UPDATED_CODECOMMANDESENSIBLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLECOMMANDESENSIBLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLECOMMANDESENSIBLE = "BBBBBBBBBB";

    @Autowired
    private CommandesensibleRepository commandesensibleRepository;

    @Autowired
    private CommandesensibleService commandesensibleService;

    @Autowired
    private CommandesensibleQueryService commandesensibleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommandesensibleMockMvc;

    private Commandesensible commandesensible;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commandesensible createEntity(EntityManager em) {
        Commandesensible commandesensible = new Commandesensible()
            .idcommandesensible(DEFAULT_IDCOMMANDESENSIBLE)
            .codecommandesensible(DEFAULT_CODECOMMANDESENSIBLE)
            .libellecommandesensible(DEFAULT_LIBELLECOMMANDESENSIBLE);
        return commandesensible;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commandesensible createUpdatedEntity(EntityManager em) {
        Commandesensible commandesensible = new Commandesensible()
            .idcommandesensible(UPDATED_IDCOMMANDESENSIBLE)
            .codecommandesensible(UPDATED_CODECOMMANDESENSIBLE)
            .libellecommandesensible(UPDATED_LIBELLECOMMANDESENSIBLE);
        return commandesensible;
    }

    @BeforeEach
    public void initTest() {
        commandesensible = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandesensible() throws Exception {
        int databaseSizeBeforeCreate = commandesensibleRepository.findAll().size();
        // Create the Commandesensible
        restCommandesensibleMockMvc.perform(post("/api/commandesensibles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandesensible)))
            .andExpect(status().isCreated());

        // Validate the Commandesensible in the database
        List<Commandesensible> commandesensibleList = commandesensibleRepository.findAll();
        assertThat(commandesensibleList).hasSize(databaseSizeBeforeCreate + 1);
        Commandesensible testCommandesensible = commandesensibleList.get(commandesensibleList.size() - 1);
        assertThat(testCommandesensible.getIdcommandesensible()).isEqualTo(DEFAULT_IDCOMMANDESENSIBLE);
        assertThat(testCommandesensible.getCodecommandesensible()).isEqualTo(DEFAULT_CODECOMMANDESENSIBLE);
        assertThat(testCommandesensible.getLibellecommandesensible()).isEqualTo(DEFAULT_LIBELLECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void createCommandesensibleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandesensibleRepository.findAll().size();

        // Create the Commandesensible with an existing ID
        commandesensible.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandesensibleMockMvc.perform(post("/api/commandesensibles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandesensible)))
            .andExpect(status().isBadRequest());

        // Validate the Commandesensible in the database
        List<Commandesensible> commandesensibleList = commandesensibleRepository.findAll();
        assertThat(commandesensibleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdcommandesensibleIsRequired() throws Exception {
        int databaseSizeBeforeTest = commandesensibleRepository.findAll().size();
        // set the field null
        commandesensible.setIdcommandesensible(null);

        // Create the Commandesensible, which fails.


        restCommandesensibleMockMvc.perform(post("/api/commandesensibles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandesensible)))
            .andExpect(status().isBadRequest());

        List<Commandesensible> commandesensibleList = commandesensibleRepository.findAll();
        assertThat(commandesensibleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommandesensibles() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList
        restCommandesensibleMockMvc.perform(get("/api/commandesensibles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandesensible.getId().intValue())))
            .andExpect(jsonPath("$.[*].idcommandesensible").value(hasItem(DEFAULT_IDCOMMANDESENSIBLE)))
            .andExpect(jsonPath("$.[*].codecommandesensible").value(hasItem(DEFAULT_CODECOMMANDESENSIBLE)))
            .andExpect(jsonPath("$.[*].libellecommandesensible").value(hasItem(DEFAULT_LIBELLECOMMANDESENSIBLE)));
    }
    
    @Test
    @Transactional
    public void getCommandesensible() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get the commandesensible
        restCommandesensibleMockMvc.perform(get("/api/commandesensibles/{id}", commandesensible.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commandesensible.getId().intValue()))
            .andExpect(jsonPath("$.idcommandesensible").value(DEFAULT_IDCOMMANDESENSIBLE))
            .andExpect(jsonPath("$.codecommandesensible").value(DEFAULT_CODECOMMANDESENSIBLE))
            .andExpect(jsonPath("$.libellecommandesensible").value(DEFAULT_LIBELLECOMMANDESENSIBLE));
    }


    @Test
    @Transactional
    public void getCommandesensiblesByIdFiltering() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        Long id = commandesensible.getId();

        defaultCommandesensibleShouldBeFound("id.equals=" + id);
        defaultCommandesensibleShouldNotBeFound("id.notEquals=" + id);

        defaultCommandesensibleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommandesensibleShouldNotBeFound("id.greaterThan=" + id);

        defaultCommandesensibleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommandesensibleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommandesensiblesByIdcommandesensibleIsEqualToSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where idcommandesensible equals to DEFAULT_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("idcommandesensible.equals=" + DEFAULT_IDCOMMANDESENSIBLE);

        // Get all the commandesensibleList where idcommandesensible equals to UPDATED_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("idcommandesensible.equals=" + UPDATED_IDCOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByIdcommandesensibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where idcommandesensible not equals to DEFAULT_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("idcommandesensible.notEquals=" + DEFAULT_IDCOMMANDESENSIBLE);

        // Get all the commandesensibleList where idcommandesensible not equals to UPDATED_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("idcommandesensible.notEquals=" + UPDATED_IDCOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByIdcommandesensibleIsInShouldWork() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where idcommandesensible in DEFAULT_IDCOMMANDESENSIBLE or UPDATED_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("idcommandesensible.in=" + DEFAULT_IDCOMMANDESENSIBLE + "," + UPDATED_IDCOMMANDESENSIBLE);

        // Get all the commandesensibleList where idcommandesensible equals to UPDATED_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("idcommandesensible.in=" + UPDATED_IDCOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByIdcommandesensibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where idcommandesensible is not null
        defaultCommandesensibleShouldBeFound("idcommandesensible.specified=true");

        // Get all the commandesensibleList where idcommandesensible is null
        defaultCommandesensibleShouldNotBeFound("idcommandesensible.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByIdcommandesensibleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where idcommandesensible is greater than or equal to DEFAULT_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("idcommandesensible.greaterThanOrEqual=" + DEFAULT_IDCOMMANDESENSIBLE);

        // Get all the commandesensibleList where idcommandesensible is greater than or equal to UPDATED_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("idcommandesensible.greaterThanOrEqual=" + UPDATED_IDCOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByIdcommandesensibleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where idcommandesensible is less than or equal to DEFAULT_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("idcommandesensible.lessThanOrEqual=" + DEFAULT_IDCOMMANDESENSIBLE);

        // Get all the commandesensibleList where idcommandesensible is less than or equal to SMALLER_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("idcommandesensible.lessThanOrEqual=" + SMALLER_IDCOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByIdcommandesensibleIsLessThanSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where idcommandesensible is less than DEFAULT_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("idcommandesensible.lessThan=" + DEFAULT_IDCOMMANDESENSIBLE);

        // Get all the commandesensibleList where idcommandesensible is less than UPDATED_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("idcommandesensible.lessThan=" + UPDATED_IDCOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByIdcommandesensibleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where idcommandesensible is greater than DEFAULT_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("idcommandesensible.greaterThan=" + DEFAULT_IDCOMMANDESENSIBLE);

        // Get all the commandesensibleList where idcommandesensible is greater than SMALLER_IDCOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("idcommandesensible.greaterThan=" + SMALLER_IDCOMMANDESENSIBLE);
    }


    @Test
    @Transactional
    public void getAllCommandesensiblesByCodecommandesensibleIsEqualToSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where codecommandesensible equals to DEFAULT_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("codecommandesensible.equals=" + DEFAULT_CODECOMMANDESENSIBLE);

        // Get all the commandesensibleList where codecommandesensible equals to UPDATED_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("codecommandesensible.equals=" + UPDATED_CODECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByCodecommandesensibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where codecommandesensible not equals to DEFAULT_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("codecommandesensible.notEquals=" + DEFAULT_CODECOMMANDESENSIBLE);

        // Get all the commandesensibleList where codecommandesensible not equals to UPDATED_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("codecommandesensible.notEquals=" + UPDATED_CODECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByCodecommandesensibleIsInShouldWork() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where codecommandesensible in DEFAULT_CODECOMMANDESENSIBLE or UPDATED_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("codecommandesensible.in=" + DEFAULT_CODECOMMANDESENSIBLE + "," + UPDATED_CODECOMMANDESENSIBLE);

        // Get all the commandesensibleList where codecommandesensible equals to UPDATED_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("codecommandesensible.in=" + UPDATED_CODECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByCodecommandesensibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where codecommandesensible is not null
        defaultCommandesensibleShouldBeFound("codecommandesensible.specified=true");

        // Get all the commandesensibleList where codecommandesensible is null
        defaultCommandesensibleShouldNotBeFound("codecommandesensible.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesensiblesByCodecommandesensibleContainsSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where codecommandesensible contains DEFAULT_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("codecommandesensible.contains=" + DEFAULT_CODECOMMANDESENSIBLE);

        // Get all the commandesensibleList where codecommandesensible contains UPDATED_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("codecommandesensible.contains=" + UPDATED_CODECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByCodecommandesensibleNotContainsSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where codecommandesensible does not contain DEFAULT_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("codecommandesensible.doesNotContain=" + DEFAULT_CODECOMMANDESENSIBLE);

        // Get all the commandesensibleList where codecommandesensible does not contain UPDATED_CODECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("codecommandesensible.doesNotContain=" + UPDATED_CODECOMMANDESENSIBLE);
    }


    @Test
    @Transactional
    public void getAllCommandesensiblesByLibellecommandesensibleIsEqualToSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where libellecommandesensible equals to DEFAULT_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("libellecommandesensible.equals=" + DEFAULT_LIBELLECOMMANDESENSIBLE);

        // Get all the commandesensibleList where libellecommandesensible equals to UPDATED_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("libellecommandesensible.equals=" + UPDATED_LIBELLECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByLibellecommandesensibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where libellecommandesensible not equals to DEFAULT_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("libellecommandesensible.notEquals=" + DEFAULT_LIBELLECOMMANDESENSIBLE);

        // Get all the commandesensibleList where libellecommandesensible not equals to UPDATED_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("libellecommandesensible.notEquals=" + UPDATED_LIBELLECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByLibellecommandesensibleIsInShouldWork() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where libellecommandesensible in DEFAULT_LIBELLECOMMANDESENSIBLE or UPDATED_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("libellecommandesensible.in=" + DEFAULT_LIBELLECOMMANDESENSIBLE + "," + UPDATED_LIBELLECOMMANDESENSIBLE);

        // Get all the commandesensibleList where libellecommandesensible equals to UPDATED_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("libellecommandesensible.in=" + UPDATED_LIBELLECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByLibellecommandesensibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where libellecommandesensible is not null
        defaultCommandesensibleShouldBeFound("libellecommandesensible.specified=true");

        // Get all the commandesensibleList where libellecommandesensible is null
        defaultCommandesensibleShouldNotBeFound("libellecommandesensible.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommandesensiblesByLibellecommandesensibleContainsSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where libellecommandesensible contains DEFAULT_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("libellecommandesensible.contains=" + DEFAULT_LIBELLECOMMANDESENSIBLE);

        // Get all the commandesensibleList where libellecommandesensible contains UPDATED_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("libellecommandesensible.contains=" + UPDATED_LIBELLECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void getAllCommandesensiblesByLibellecommandesensibleNotContainsSomething() throws Exception {
        // Initialize the database
        commandesensibleRepository.saveAndFlush(commandesensible);

        // Get all the commandesensibleList where libellecommandesensible does not contain DEFAULT_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldNotBeFound("libellecommandesensible.doesNotContain=" + DEFAULT_LIBELLECOMMANDESENSIBLE);

        // Get all the commandesensibleList where libellecommandesensible does not contain UPDATED_LIBELLECOMMANDESENSIBLE
        defaultCommandesensibleShouldBeFound("libellecommandesensible.doesNotContain=" + UPDATED_LIBELLECOMMANDESENSIBLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommandesensibleShouldBeFound(String filter) throws Exception {
        restCommandesensibleMockMvc.perform(get("/api/commandesensibles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandesensible.getId().intValue())))
            .andExpect(jsonPath("$.[*].idcommandesensible").value(hasItem(DEFAULT_IDCOMMANDESENSIBLE)))
            .andExpect(jsonPath("$.[*].codecommandesensible").value(hasItem(DEFAULT_CODECOMMANDESENSIBLE)))
            .andExpect(jsonPath("$.[*].libellecommandesensible").value(hasItem(DEFAULT_LIBELLECOMMANDESENSIBLE)));

        // Check, that the count call also returns 1
        restCommandesensibleMockMvc.perform(get("/api/commandesensibles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommandesensibleShouldNotBeFound(String filter) throws Exception {
        restCommandesensibleMockMvc.perform(get("/api/commandesensibles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommandesensibleMockMvc.perform(get("/api/commandesensibles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCommandesensible() throws Exception {
        // Get the commandesensible
        restCommandesensibleMockMvc.perform(get("/api/commandesensibles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandesensible() throws Exception {
        // Initialize the database
        commandesensibleService.save(commandesensible);

        int databaseSizeBeforeUpdate = commandesensibleRepository.findAll().size();

        // Update the commandesensible
        Commandesensible updatedCommandesensible = commandesensibleRepository.findById(commandesensible.getId()).get();
        // Disconnect from session so that the updates on updatedCommandesensible are not directly saved in db
        em.detach(updatedCommandesensible);
        updatedCommandesensible
            .idcommandesensible(UPDATED_IDCOMMANDESENSIBLE)
            .codecommandesensible(UPDATED_CODECOMMANDESENSIBLE)
            .libellecommandesensible(UPDATED_LIBELLECOMMANDESENSIBLE);

        restCommandesensibleMockMvc.perform(put("/api/commandesensibles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommandesensible)))
            .andExpect(status().isOk());

        // Validate the Commandesensible in the database
        List<Commandesensible> commandesensibleList = commandesensibleRepository.findAll();
        assertThat(commandesensibleList).hasSize(databaseSizeBeforeUpdate);
        Commandesensible testCommandesensible = commandesensibleList.get(commandesensibleList.size() - 1);
        assertThat(testCommandesensible.getIdcommandesensible()).isEqualTo(UPDATED_IDCOMMANDESENSIBLE);
        assertThat(testCommandesensible.getCodecommandesensible()).isEqualTo(UPDATED_CODECOMMANDESENSIBLE);
        assertThat(testCommandesensible.getLibellecommandesensible()).isEqualTo(UPDATED_LIBELLECOMMANDESENSIBLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandesensible() throws Exception {
        int databaseSizeBeforeUpdate = commandesensibleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandesensibleMockMvc.perform(put("/api/commandesensibles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commandesensible)))
            .andExpect(status().isBadRequest());

        // Validate the Commandesensible in the database
        List<Commandesensible> commandesensibleList = commandesensibleRepository.findAll();
        assertThat(commandesensibleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommandesensible() throws Exception {
        // Initialize the database
        commandesensibleService.save(commandesensible);

        int databaseSizeBeforeDelete = commandesensibleRepository.findAll().size();

        // Delete the commandesensible
        restCommandesensibleMockMvc.perform(delete("/api/commandesensibles/{id}", commandesensible.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commandesensible> commandesensibleList = commandesensibleRepository.findAll();
        assertThat(commandesensibleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
