package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Paiementlots;
import com.mycompany.myapp.service.PaiementlotsService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.PaiementlotsCriteria;
import com.mycompany.myapp.service.PaiementlotsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Paiementlots}.
 */
@RestController
@RequestMapping("/api")
public class PaiementlotsResource {

    private final Logger log = LoggerFactory.getLogger(PaiementlotsResource.class);

    private static final String ENTITY_NAME = "paiementlots";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaiementlotsService paiementlotsService;

    private final PaiementlotsQueryService paiementlotsQueryService;

    public PaiementlotsResource(PaiementlotsService paiementlotsService, PaiementlotsQueryService paiementlotsQueryService) {
        this.paiementlotsService = paiementlotsService;
        this.paiementlotsQueryService = paiementlotsQueryService;
    }

    /**
     * {@code POST  /paiementlots} : Create a new paiementlots.
     *
     * @param paiementlots the paiementlots to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paiementlots, or with status {@code 400 (Bad Request)} if the paiementlots has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paiementlots")
    public ResponseEntity<Paiementlots> createPaiementlots(@Valid @RequestBody Paiementlots paiementlots) throws URISyntaxException {
        log.debug("REST request to save Paiementlots : {}", paiementlots);
        if (paiementlots.getId() != null) {
            throw new BadRequestAlertException("A new paiementlots cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paiementlots result = paiementlotsService.save(paiementlots);
        return ResponseEntity.created(new URI("/api/paiementlots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paiementlots} : Updates an existing paiementlots.
     *
     * @param paiementlots the paiementlots to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paiementlots,
     * or with status {@code 400 (Bad Request)} if the paiementlots is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paiementlots couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paiementlots")
    public ResponseEntity<Paiementlots> updatePaiementlots(@Valid @RequestBody Paiementlots paiementlots) throws URISyntaxException {
        log.debug("REST request to update Paiementlots : {}", paiementlots);
        if (paiementlots.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Paiementlots result = paiementlotsService.save(paiementlots);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paiementlots.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paiementlots} : get all the paiementlots.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paiementlots in body.
     */
    @GetMapping("/paiementlots")
    public ResponseEntity<List<Paiementlots>> getAllPaiementlots(PaiementlotsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Paiementlots by criteria: {}", criteria);
        Page<Paiementlots> page = paiementlotsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /paiementlots/count} : count all the paiementlots.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/paiementlots/count")
    public ResponseEntity<Long> countPaiementlots(PaiementlotsCriteria criteria) {
        log.debug("REST request to count Paiementlots by criteria: {}", criteria);
        return ResponseEntity.ok().body(paiementlotsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /paiementlots/:id} : get the "id" paiementlots.
     *
     * @param id the id of the paiementlots to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paiementlots, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paiementlots/{id}")
    public ResponseEntity<Paiementlots> getPaiementlots(@PathVariable Long id) {
        log.debug("REST request to get Paiementlots : {}", id);
        Optional<Paiementlots> paiementlots = paiementlotsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paiementlots);
    }

    /**
     * {@code DELETE  /paiementlots/:id} : delete the "id" paiementlots.
     *
     * @param id the id of the paiementlots to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paiementlots/{id}")
    public ResponseEntity<Void> deletePaiementlots(@PathVariable Long id) {
        log.debug("REST request to delete Paiementlots : {}", id);
        paiementlotsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
