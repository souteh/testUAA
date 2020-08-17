package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Hypodrome;
import com.mycompany.myapp.service.HypodromeService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.HypodromeCriteria;
import com.mycompany.myapp.service.HypodromeQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Hypodrome}.
 */
@RestController
@RequestMapping("/api")
public class HypodromeResource {

    private final Logger log = LoggerFactory.getLogger(HypodromeResource.class);

    private static final String ENTITY_NAME = "hypodrome";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HypodromeService hypodromeService;

    private final HypodromeQueryService hypodromeQueryService;

    public HypodromeResource(HypodromeService hypodromeService, HypodromeQueryService hypodromeQueryService) {
        this.hypodromeService = hypodromeService;
        this.hypodromeQueryService = hypodromeQueryService;
    }

    /**
     * {@code POST  /hypodromes} : Create a new hypodrome.
     *
     * @param hypodrome the hypodrome to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hypodrome, or with status {@code 400 (Bad Request)} if the hypodrome has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hypodromes")
    public ResponseEntity<Hypodrome> createHypodrome(@Valid @RequestBody Hypodrome hypodrome) throws URISyntaxException {
        log.debug("REST request to save Hypodrome : {}", hypodrome);
        if (hypodrome.getId() != null) {
            throw new BadRequestAlertException("A new hypodrome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hypodrome result = hypodromeService.save(hypodrome);
        return ResponseEntity.created(new URI("/api/hypodromes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hypodromes} : Updates an existing hypodrome.
     *
     * @param hypodrome the hypodrome to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hypodrome,
     * or with status {@code 400 (Bad Request)} if the hypodrome is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hypodrome couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hypodromes")
    public ResponseEntity<Hypodrome> updateHypodrome(@Valid @RequestBody Hypodrome hypodrome) throws URISyntaxException {
        log.debug("REST request to update Hypodrome : {}", hypodrome);
        if (hypodrome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hypodrome result = hypodromeService.save(hypodrome);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hypodrome.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hypodromes} : get all the hypodromes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hypodromes in body.
     */
    @GetMapping("/hypodromes")
    public ResponseEntity<List<Hypodrome>> getAllHypodromes(HypodromeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Hypodromes by criteria: {}", criteria);
        Page<Hypodrome> page = hypodromeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hypodromes/count} : count all the hypodromes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/hypodromes/count")
    public ResponseEntity<Long> countHypodromes(HypodromeCriteria criteria) {
        log.debug("REST request to count Hypodromes by criteria: {}", criteria);
        return ResponseEntity.ok().body(hypodromeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hypodromes/:id} : get the "id" hypodrome.
     *
     * @param id the id of the hypodrome to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hypodrome, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hypodromes/{id}")
    public ResponseEntity<Hypodrome> getHypodrome(@PathVariable Long id) {
        log.debug("REST request to get Hypodrome : {}", id);
        Optional<Hypodrome> hypodrome = hypodromeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hypodrome);
    }

    /**
     * {@code DELETE  /hypodromes/:id} : delete the "id" hypodrome.
     *
     * @param id the id of the hypodrome to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hypodromes/{id}")
    public ResponseEntity<Void> deleteHypodrome(@PathVariable Long id) {
        log.debug("REST request to delete Hypodrome : {}", id);
        hypodromeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
