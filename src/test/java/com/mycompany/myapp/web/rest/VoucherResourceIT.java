package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestUaaApp;
import com.mycompany.myapp.config.TestSecurityConfiguration;
import com.mycompany.myapp.domain.Voucher;
import com.mycompany.myapp.repository.VoucherRepository;
import com.mycompany.myapp.service.VoucherService;
import com.mycompany.myapp.service.dto.VoucherCriteria;
import com.mycompany.myapp.service.VoucherQueryService;

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
 * Integration tests for the {@link VoucherResource} REST controller.
 */
@SpringBootTest(classes = { TestUaaApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class VoucherResourceIT {

    private static final Integer DEFAULT_IDVOUCHER = 1;
    private static final Integer UPDATED_IDVOUCHER = 2;
    private static final Integer SMALLER_IDVOUCHER = 1 - 1;

    private static final String DEFAULT_CODEVOUCHER = "AAAAAAAAAA";
    private static final String UPDATED_CODEVOUCHER = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU = "AAAAAAAAAA";
    private static final String UPDATED_LIEU = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEUIL = 1;
    private static final Integer UPDATED_SEUIL = 2;
    private static final Integer SMALLER_SEUIL = 1 - 1;

    private static final Integer DEFAULT_DELAIPURGE = 1;
    private static final Integer UPDATED_DELAIPURGE = 2;
    private static final Integer SMALLER_DELAIPURGE = 1 - 1;

    private static final Integer DEFAULT_PLAFOND = 1;
    private static final Integer UPDATED_PLAFOND = 2;
    private static final Integer SMALLER_PLAFOND = 1 - 1;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherQueryService voucherQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoucherMockMvc;

    private Voucher voucher;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voucher createEntity(EntityManager em) {
        Voucher voucher = new Voucher()
            .idvoucher(DEFAULT_IDVOUCHER)
            .codevoucher(DEFAULT_CODEVOUCHER)
            .statut(DEFAULT_STATUT)
            .lieu(DEFAULT_LIEU)
            .seuil(DEFAULT_SEUIL)
            .delaipurge(DEFAULT_DELAIPURGE)
            .plafond(DEFAULT_PLAFOND);
        return voucher;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voucher createUpdatedEntity(EntityManager em) {
        Voucher voucher = new Voucher()
            .idvoucher(UPDATED_IDVOUCHER)
            .codevoucher(UPDATED_CODEVOUCHER)
            .statut(UPDATED_STATUT)
            .lieu(UPDATED_LIEU)
            .seuil(UPDATED_SEUIL)
            .delaipurge(UPDATED_DELAIPURGE)
            .plafond(UPDATED_PLAFOND);
        return voucher;
    }

    @BeforeEach
    public void initTest() {
        voucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoucher() throws Exception {
        int databaseSizeBeforeCreate = voucherRepository.findAll().size();
        // Create the Voucher
        restVoucherMockMvc.perform(post("/api/vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isCreated());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate + 1);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getIdvoucher()).isEqualTo(DEFAULT_IDVOUCHER);
        assertThat(testVoucher.getCodevoucher()).isEqualTo(DEFAULT_CODEVOUCHER);
        assertThat(testVoucher.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testVoucher.getLieu()).isEqualTo(DEFAULT_LIEU);
        assertThat(testVoucher.getSeuil()).isEqualTo(DEFAULT_SEUIL);
        assertThat(testVoucher.getDelaipurge()).isEqualTo(DEFAULT_DELAIPURGE);
        assertThat(testVoucher.getPlafond()).isEqualTo(DEFAULT_PLAFOND);
    }

    @Test
    @Transactional
    public void createVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voucherRepository.findAll().size();

        // Create the Voucher with an existing ID
        voucher.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherMockMvc.perform(post("/api/vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIdvoucherIsRequired() throws Exception {
        int databaseSizeBeforeTest = voucherRepository.findAll().size();
        // set the field null
        voucher.setIdvoucher(null);

        // Create the Voucher, which fails.


        restVoucherMockMvc.perform(post("/api/vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isBadRequest());

        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVouchers() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList
        restVoucherMockMvc.perform(get("/api/vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].idvoucher").value(hasItem(DEFAULT_IDVOUCHER)))
            .andExpect(jsonPath("$.[*].codevoucher").value(hasItem(DEFAULT_CODEVOUCHER)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU)))
            .andExpect(jsonPath("$.[*].seuil").value(hasItem(DEFAULT_SEUIL)))
            .andExpect(jsonPath("$.[*].delaipurge").value(hasItem(DEFAULT_DELAIPURGE)))
            .andExpect(jsonPath("$.[*].plafond").value(hasItem(DEFAULT_PLAFOND)));
    }
    
    @Test
    @Transactional
    public void getVoucher() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get the voucher
        restVoucherMockMvc.perform(get("/api/vouchers/{id}", voucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucher.getId().intValue()))
            .andExpect(jsonPath("$.idvoucher").value(DEFAULT_IDVOUCHER))
            .andExpect(jsonPath("$.codevoucher").value(DEFAULT_CODEVOUCHER))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.lieu").value(DEFAULT_LIEU))
            .andExpect(jsonPath("$.seuil").value(DEFAULT_SEUIL))
            .andExpect(jsonPath("$.delaipurge").value(DEFAULT_DELAIPURGE))
            .andExpect(jsonPath("$.plafond").value(DEFAULT_PLAFOND));
    }


    @Test
    @Transactional
    public void getVouchersByIdFiltering() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        Long id = voucher.getId();

        defaultVoucherShouldBeFound("id.equals=" + id);
        defaultVoucherShouldNotBeFound("id.notEquals=" + id);

        defaultVoucherShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVoucherShouldNotBeFound("id.greaterThan=" + id);

        defaultVoucherShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVoucherShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVouchersByIdvoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where idvoucher equals to DEFAULT_IDVOUCHER
        defaultVoucherShouldBeFound("idvoucher.equals=" + DEFAULT_IDVOUCHER);

        // Get all the voucherList where idvoucher equals to UPDATED_IDVOUCHER
        defaultVoucherShouldNotBeFound("idvoucher.equals=" + UPDATED_IDVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByIdvoucherIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where idvoucher not equals to DEFAULT_IDVOUCHER
        defaultVoucherShouldNotBeFound("idvoucher.notEquals=" + DEFAULT_IDVOUCHER);

        // Get all the voucherList where idvoucher not equals to UPDATED_IDVOUCHER
        defaultVoucherShouldBeFound("idvoucher.notEquals=" + UPDATED_IDVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByIdvoucherIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where idvoucher in DEFAULT_IDVOUCHER or UPDATED_IDVOUCHER
        defaultVoucherShouldBeFound("idvoucher.in=" + DEFAULT_IDVOUCHER + "," + UPDATED_IDVOUCHER);

        // Get all the voucherList where idvoucher equals to UPDATED_IDVOUCHER
        defaultVoucherShouldNotBeFound("idvoucher.in=" + UPDATED_IDVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByIdvoucherIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where idvoucher is not null
        defaultVoucherShouldBeFound("idvoucher.specified=true");

        // Get all the voucherList where idvoucher is null
        defaultVoucherShouldNotBeFound("idvoucher.specified=false");
    }

    @Test
    @Transactional
    public void getAllVouchersByIdvoucherIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where idvoucher is greater than or equal to DEFAULT_IDVOUCHER
        defaultVoucherShouldBeFound("idvoucher.greaterThanOrEqual=" + DEFAULT_IDVOUCHER);

        // Get all the voucherList where idvoucher is greater than or equal to UPDATED_IDVOUCHER
        defaultVoucherShouldNotBeFound("idvoucher.greaterThanOrEqual=" + UPDATED_IDVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByIdvoucherIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where idvoucher is less than or equal to DEFAULT_IDVOUCHER
        defaultVoucherShouldBeFound("idvoucher.lessThanOrEqual=" + DEFAULT_IDVOUCHER);

        // Get all the voucherList where idvoucher is less than or equal to SMALLER_IDVOUCHER
        defaultVoucherShouldNotBeFound("idvoucher.lessThanOrEqual=" + SMALLER_IDVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByIdvoucherIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where idvoucher is less than DEFAULT_IDVOUCHER
        defaultVoucherShouldNotBeFound("idvoucher.lessThan=" + DEFAULT_IDVOUCHER);

        // Get all the voucherList where idvoucher is less than UPDATED_IDVOUCHER
        defaultVoucherShouldBeFound("idvoucher.lessThan=" + UPDATED_IDVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByIdvoucherIsGreaterThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where idvoucher is greater than DEFAULT_IDVOUCHER
        defaultVoucherShouldNotBeFound("idvoucher.greaterThan=" + DEFAULT_IDVOUCHER);

        // Get all the voucherList where idvoucher is greater than SMALLER_IDVOUCHER
        defaultVoucherShouldBeFound("idvoucher.greaterThan=" + SMALLER_IDVOUCHER);
    }


    @Test
    @Transactional
    public void getAllVouchersByCodevoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where codevoucher equals to DEFAULT_CODEVOUCHER
        defaultVoucherShouldBeFound("codevoucher.equals=" + DEFAULT_CODEVOUCHER);

        // Get all the voucherList where codevoucher equals to UPDATED_CODEVOUCHER
        defaultVoucherShouldNotBeFound("codevoucher.equals=" + UPDATED_CODEVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByCodevoucherIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where codevoucher not equals to DEFAULT_CODEVOUCHER
        defaultVoucherShouldNotBeFound("codevoucher.notEquals=" + DEFAULT_CODEVOUCHER);

        // Get all the voucherList where codevoucher not equals to UPDATED_CODEVOUCHER
        defaultVoucherShouldBeFound("codevoucher.notEquals=" + UPDATED_CODEVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByCodevoucherIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where codevoucher in DEFAULT_CODEVOUCHER or UPDATED_CODEVOUCHER
        defaultVoucherShouldBeFound("codevoucher.in=" + DEFAULT_CODEVOUCHER + "," + UPDATED_CODEVOUCHER);

        // Get all the voucherList where codevoucher equals to UPDATED_CODEVOUCHER
        defaultVoucherShouldNotBeFound("codevoucher.in=" + UPDATED_CODEVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByCodevoucherIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where codevoucher is not null
        defaultVoucherShouldBeFound("codevoucher.specified=true");

        // Get all the voucherList where codevoucher is null
        defaultVoucherShouldNotBeFound("codevoucher.specified=false");
    }
                @Test
    @Transactional
    public void getAllVouchersByCodevoucherContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where codevoucher contains DEFAULT_CODEVOUCHER
        defaultVoucherShouldBeFound("codevoucher.contains=" + DEFAULT_CODEVOUCHER);

        // Get all the voucherList where codevoucher contains UPDATED_CODEVOUCHER
        defaultVoucherShouldNotBeFound("codevoucher.contains=" + UPDATED_CODEVOUCHER);
    }

    @Test
    @Transactional
    public void getAllVouchersByCodevoucherNotContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where codevoucher does not contain DEFAULT_CODEVOUCHER
        defaultVoucherShouldNotBeFound("codevoucher.doesNotContain=" + DEFAULT_CODEVOUCHER);

        // Get all the voucherList where codevoucher does not contain UPDATED_CODEVOUCHER
        defaultVoucherShouldBeFound("codevoucher.doesNotContain=" + UPDATED_CODEVOUCHER);
    }


    @Test
    @Transactional
    public void getAllVouchersByStatutIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where statut equals to DEFAULT_STATUT
        defaultVoucherShouldBeFound("statut.equals=" + DEFAULT_STATUT);

        // Get all the voucherList where statut equals to UPDATED_STATUT
        defaultVoucherShouldNotBeFound("statut.equals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllVouchersByStatutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where statut not equals to DEFAULT_STATUT
        defaultVoucherShouldNotBeFound("statut.notEquals=" + DEFAULT_STATUT);

        // Get all the voucherList where statut not equals to UPDATED_STATUT
        defaultVoucherShouldBeFound("statut.notEquals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllVouchersByStatutIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where statut in DEFAULT_STATUT or UPDATED_STATUT
        defaultVoucherShouldBeFound("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT);

        // Get all the voucherList where statut equals to UPDATED_STATUT
        defaultVoucherShouldNotBeFound("statut.in=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllVouchersByStatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where statut is not null
        defaultVoucherShouldBeFound("statut.specified=true");

        // Get all the voucherList where statut is null
        defaultVoucherShouldNotBeFound("statut.specified=false");
    }
                @Test
    @Transactional
    public void getAllVouchersByStatutContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where statut contains DEFAULT_STATUT
        defaultVoucherShouldBeFound("statut.contains=" + DEFAULT_STATUT);

        // Get all the voucherList where statut contains UPDATED_STATUT
        defaultVoucherShouldNotBeFound("statut.contains=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllVouchersByStatutNotContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where statut does not contain DEFAULT_STATUT
        defaultVoucherShouldNotBeFound("statut.doesNotContain=" + DEFAULT_STATUT);

        // Get all the voucherList where statut does not contain UPDATED_STATUT
        defaultVoucherShouldBeFound("statut.doesNotContain=" + UPDATED_STATUT);
    }


    @Test
    @Transactional
    public void getAllVouchersByLieuIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lieu equals to DEFAULT_LIEU
        defaultVoucherShouldBeFound("lieu.equals=" + DEFAULT_LIEU);

        // Get all the voucherList where lieu equals to UPDATED_LIEU
        defaultVoucherShouldNotBeFound("lieu.equals=" + UPDATED_LIEU);
    }

    @Test
    @Transactional
    public void getAllVouchersByLieuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lieu not equals to DEFAULT_LIEU
        defaultVoucherShouldNotBeFound("lieu.notEquals=" + DEFAULT_LIEU);

        // Get all the voucherList where lieu not equals to UPDATED_LIEU
        defaultVoucherShouldBeFound("lieu.notEquals=" + UPDATED_LIEU);
    }

    @Test
    @Transactional
    public void getAllVouchersByLieuIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lieu in DEFAULT_LIEU or UPDATED_LIEU
        defaultVoucherShouldBeFound("lieu.in=" + DEFAULT_LIEU + "," + UPDATED_LIEU);

        // Get all the voucherList where lieu equals to UPDATED_LIEU
        defaultVoucherShouldNotBeFound("lieu.in=" + UPDATED_LIEU);
    }

    @Test
    @Transactional
    public void getAllVouchersByLieuIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lieu is not null
        defaultVoucherShouldBeFound("lieu.specified=true");

        // Get all the voucherList where lieu is null
        defaultVoucherShouldNotBeFound("lieu.specified=false");
    }
                @Test
    @Transactional
    public void getAllVouchersByLieuContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lieu contains DEFAULT_LIEU
        defaultVoucherShouldBeFound("lieu.contains=" + DEFAULT_LIEU);

        // Get all the voucherList where lieu contains UPDATED_LIEU
        defaultVoucherShouldNotBeFound("lieu.contains=" + UPDATED_LIEU);
    }

    @Test
    @Transactional
    public void getAllVouchersByLieuNotContainsSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where lieu does not contain DEFAULT_LIEU
        defaultVoucherShouldNotBeFound("lieu.doesNotContain=" + DEFAULT_LIEU);

        // Get all the voucherList where lieu does not contain UPDATED_LIEU
        defaultVoucherShouldBeFound("lieu.doesNotContain=" + UPDATED_LIEU);
    }


    @Test
    @Transactional
    public void getAllVouchersBySeuilIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where seuil equals to DEFAULT_SEUIL
        defaultVoucherShouldBeFound("seuil.equals=" + DEFAULT_SEUIL);

        // Get all the voucherList where seuil equals to UPDATED_SEUIL
        defaultVoucherShouldNotBeFound("seuil.equals=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllVouchersBySeuilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where seuil not equals to DEFAULT_SEUIL
        defaultVoucherShouldNotBeFound("seuil.notEquals=" + DEFAULT_SEUIL);

        // Get all the voucherList where seuil not equals to UPDATED_SEUIL
        defaultVoucherShouldBeFound("seuil.notEquals=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllVouchersBySeuilIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where seuil in DEFAULT_SEUIL or UPDATED_SEUIL
        defaultVoucherShouldBeFound("seuil.in=" + DEFAULT_SEUIL + "," + UPDATED_SEUIL);

        // Get all the voucherList where seuil equals to UPDATED_SEUIL
        defaultVoucherShouldNotBeFound("seuil.in=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllVouchersBySeuilIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where seuil is not null
        defaultVoucherShouldBeFound("seuil.specified=true");

        // Get all the voucherList where seuil is null
        defaultVoucherShouldNotBeFound("seuil.specified=false");
    }

    @Test
    @Transactional
    public void getAllVouchersBySeuilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where seuil is greater than or equal to DEFAULT_SEUIL
        defaultVoucherShouldBeFound("seuil.greaterThanOrEqual=" + DEFAULT_SEUIL);

        // Get all the voucherList where seuil is greater than or equal to UPDATED_SEUIL
        defaultVoucherShouldNotBeFound("seuil.greaterThanOrEqual=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllVouchersBySeuilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where seuil is less than or equal to DEFAULT_SEUIL
        defaultVoucherShouldBeFound("seuil.lessThanOrEqual=" + DEFAULT_SEUIL);

        // Get all the voucherList where seuil is less than or equal to SMALLER_SEUIL
        defaultVoucherShouldNotBeFound("seuil.lessThanOrEqual=" + SMALLER_SEUIL);
    }

    @Test
    @Transactional
    public void getAllVouchersBySeuilIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where seuil is less than DEFAULT_SEUIL
        defaultVoucherShouldNotBeFound("seuil.lessThan=" + DEFAULT_SEUIL);

        // Get all the voucherList where seuil is less than UPDATED_SEUIL
        defaultVoucherShouldBeFound("seuil.lessThan=" + UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void getAllVouchersBySeuilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where seuil is greater than DEFAULT_SEUIL
        defaultVoucherShouldNotBeFound("seuil.greaterThan=" + DEFAULT_SEUIL);

        // Get all the voucherList where seuil is greater than SMALLER_SEUIL
        defaultVoucherShouldBeFound("seuil.greaterThan=" + SMALLER_SEUIL);
    }


    @Test
    @Transactional
    public void getAllVouchersByDelaipurgeIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where delaipurge equals to DEFAULT_DELAIPURGE
        defaultVoucherShouldBeFound("delaipurge.equals=" + DEFAULT_DELAIPURGE);

        // Get all the voucherList where delaipurge equals to UPDATED_DELAIPURGE
        defaultVoucherShouldNotBeFound("delaipurge.equals=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllVouchersByDelaipurgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where delaipurge not equals to DEFAULT_DELAIPURGE
        defaultVoucherShouldNotBeFound("delaipurge.notEquals=" + DEFAULT_DELAIPURGE);

        // Get all the voucherList where delaipurge not equals to UPDATED_DELAIPURGE
        defaultVoucherShouldBeFound("delaipurge.notEquals=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllVouchersByDelaipurgeIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where delaipurge in DEFAULT_DELAIPURGE or UPDATED_DELAIPURGE
        defaultVoucherShouldBeFound("delaipurge.in=" + DEFAULT_DELAIPURGE + "," + UPDATED_DELAIPURGE);

        // Get all the voucherList where delaipurge equals to UPDATED_DELAIPURGE
        defaultVoucherShouldNotBeFound("delaipurge.in=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllVouchersByDelaipurgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where delaipurge is not null
        defaultVoucherShouldBeFound("delaipurge.specified=true");

        // Get all the voucherList where delaipurge is null
        defaultVoucherShouldNotBeFound("delaipurge.specified=false");
    }

    @Test
    @Transactional
    public void getAllVouchersByDelaipurgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where delaipurge is greater than or equal to DEFAULT_DELAIPURGE
        defaultVoucherShouldBeFound("delaipurge.greaterThanOrEqual=" + DEFAULT_DELAIPURGE);

        // Get all the voucherList where delaipurge is greater than or equal to UPDATED_DELAIPURGE
        defaultVoucherShouldNotBeFound("delaipurge.greaterThanOrEqual=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllVouchersByDelaipurgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where delaipurge is less than or equal to DEFAULT_DELAIPURGE
        defaultVoucherShouldBeFound("delaipurge.lessThanOrEqual=" + DEFAULT_DELAIPURGE);

        // Get all the voucherList where delaipurge is less than or equal to SMALLER_DELAIPURGE
        defaultVoucherShouldNotBeFound("delaipurge.lessThanOrEqual=" + SMALLER_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllVouchersByDelaipurgeIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where delaipurge is less than DEFAULT_DELAIPURGE
        defaultVoucherShouldNotBeFound("delaipurge.lessThan=" + DEFAULT_DELAIPURGE);

        // Get all the voucherList where delaipurge is less than UPDATED_DELAIPURGE
        defaultVoucherShouldBeFound("delaipurge.lessThan=" + UPDATED_DELAIPURGE);
    }

    @Test
    @Transactional
    public void getAllVouchersByDelaipurgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where delaipurge is greater than DEFAULT_DELAIPURGE
        defaultVoucherShouldNotBeFound("delaipurge.greaterThan=" + DEFAULT_DELAIPURGE);

        // Get all the voucherList where delaipurge is greater than SMALLER_DELAIPURGE
        defaultVoucherShouldBeFound("delaipurge.greaterThan=" + SMALLER_DELAIPURGE);
    }


    @Test
    @Transactional
    public void getAllVouchersByPlafondIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where plafond equals to DEFAULT_PLAFOND
        defaultVoucherShouldBeFound("plafond.equals=" + DEFAULT_PLAFOND);

        // Get all the voucherList where plafond equals to UPDATED_PLAFOND
        defaultVoucherShouldNotBeFound("plafond.equals=" + UPDATED_PLAFOND);
    }

    @Test
    @Transactional
    public void getAllVouchersByPlafondIsNotEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where plafond not equals to DEFAULT_PLAFOND
        defaultVoucherShouldNotBeFound("plafond.notEquals=" + DEFAULT_PLAFOND);

        // Get all the voucherList where plafond not equals to UPDATED_PLAFOND
        defaultVoucherShouldBeFound("plafond.notEquals=" + UPDATED_PLAFOND);
    }

    @Test
    @Transactional
    public void getAllVouchersByPlafondIsInShouldWork() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where plafond in DEFAULT_PLAFOND or UPDATED_PLAFOND
        defaultVoucherShouldBeFound("plafond.in=" + DEFAULT_PLAFOND + "," + UPDATED_PLAFOND);

        // Get all the voucherList where plafond equals to UPDATED_PLAFOND
        defaultVoucherShouldNotBeFound("plafond.in=" + UPDATED_PLAFOND);
    }

    @Test
    @Transactional
    public void getAllVouchersByPlafondIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where plafond is not null
        defaultVoucherShouldBeFound("plafond.specified=true");

        // Get all the voucherList where plafond is null
        defaultVoucherShouldNotBeFound("plafond.specified=false");
    }

    @Test
    @Transactional
    public void getAllVouchersByPlafondIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where plafond is greater than or equal to DEFAULT_PLAFOND
        defaultVoucherShouldBeFound("plafond.greaterThanOrEqual=" + DEFAULT_PLAFOND);

        // Get all the voucherList where plafond is greater than or equal to UPDATED_PLAFOND
        defaultVoucherShouldNotBeFound("plafond.greaterThanOrEqual=" + UPDATED_PLAFOND);
    }

    @Test
    @Transactional
    public void getAllVouchersByPlafondIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where plafond is less than or equal to DEFAULT_PLAFOND
        defaultVoucherShouldBeFound("plafond.lessThanOrEqual=" + DEFAULT_PLAFOND);

        // Get all the voucherList where plafond is less than or equal to SMALLER_PLAFOND
        defaultVoucherShouldNotBeFound("plafond.lessThanOrEqual=" + SMALLER_PLAFOND);
    }

    @Test
    @Transactional
    public void getAllVouchersByPlafondIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where plafond is less than DEFAULT_PLAFOND
        defaultVoucherShouldNotBeFound("plafond.lessThan=" + DEFAULT_PLAFOND);

        // Get all the voucherList where plafond is less than UPDATED_PLAFOND
        defaultVoucherShouldBeFound("plafond.lessThan=" + UPDATED_PLAFOND);
    }

    @Test
    @Transactional
    public void getAllVouchersByPlafondIsGreaterThanSomething() throws Exception {
        // Initialize the database
        voucherRepository.saveAndFlush(voucher);

        // Get all the voucherList where plafond is greater than DEFAULT_PLAFOND
        defaultVoucherShouldNotBeFound("plafond.greaterThan=" + DEFAULT_PLAFOND);

        // Get all the voucherList where plafond is greater than SMALLER_PLAFOND
        defaultVoucherShouldBeFound("plafond.greaterThan=" + SMALLER_PLAFOND);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVoucherShouldBeFound(String filter) throws Exception {
        restVoucherMockMvc.perform(get("/api/vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].idvoucher").value(hasItem(DEFAULT_IDVOUCHER)))
            .andExpect(jsonPath("$.[*].codevoucher").value(hasItem(DEFAULT_CODEVOUCHER)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].lieu").value(hasItem(DEFAULT_LIEU)))
            .andExpect(jsonPath("$.[*].seuil").value(hasItem(DEFAULT_SEUIL)))
            .andExpect(jsonPath("$.[*].delaipurge").value(hasItem(DEFAULT_DELAIPURGE)))
            .andExpect(jsonPath("$.[*].plafond").value(hasItem(DEFAULT_PLAFOND)));

        // Check, that the count call also returns 1
        restVoucherMockMvc.perform(get("/api/vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVoucherShouldNotBeFound(String filter) throws Exception {
        restVoucherMockMvc.perform(get("/api/vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoucherMockMvc.perform(get("/api/vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVoucher() throws Exception {
        // Get the voucher
        restVoucherMockMvc.perform(get("/api/vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucher() throws Exception {
        // Initialize the database
        voucherService.save(voucher);

        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // Update the voucher
        Voucher updatedVoucher = voucherRepository.findById(voucher.getId()).get();
        // Disconnect from session so that the updates on updatedVoucher are not directly saved in db
        em.detach(updatedVoucher);
        updatedVoucher
            .idvoucher(UPDATED_IDVOUCHER)
            .codevoucher(UPDATED_CODEVOUCHER)
            .statut(UPDATED_STATUT)
            .lieu(UPDATED_LIEU)
            .seuil(UPDATED_SEUIL)
            .delaipurge(UPDATED_DELAIPURGE)
            .plafond(UPDATED_PLAFOND);

        restVoucherMockMvc.perform(put("/api/vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoucher)))
            .andExpect(status().isOk());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
        Voucher testVoucher = voucherList.get(voucherList.size() - 1);
        assertThat(testVoucher.getIdvoucher()).isEqualTo(UPDATED_IDVOUCHER);
        assertThat(testVoucher.getCodevoucher()).isEqualTo(UPDATED_CODEVOUCHER);
        assertThat(testVoucher.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testVoucher.getLieu()).isEqualTo(UPDATED_LIEU);
        assertThat(testVoucher.getSeuil()).isEqualTo(UPDATED_SEUIL);
        assertThat(testVoucher.getDelaipurge()).isEqualTo(UPDATED_DELAIPURGE);
        assertThat(testVoucher.getPlafond()).isEqualTo(UPDATED_PLAFOND);
    }

    @Test
    @Transactional
    public void updateNonExistingVoucher() throws Exception {
        int databaseSizeBeforeUpdate = voucherRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherMockMvc.perform(put("/api/vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voucher)))
            .andExpect(status().isBadRequest());

        // Validate the Voucher in the database
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoucher() throws Exception {
        // Initialize the database
        voucherService.save(voucher);

        int databaseSizeBeforeDelete = voucherRepository.findAll().size();

        // Delete the voucher
        restVoucherMockMvc.perform(delete("/api/vouchers/{id}", voucher.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voucher> voucherList = voucherRepository.findAll();
        assertThat(voucherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
