package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Pdv;
import com.mycompany.myapp.service.PdvService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.PdvCriteria;
import com.mycompany.myapp.service.PdvQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Pdv}.
 */
@RestController
@RequestMapping("/api")
public class PdvResource {

    private final Logger log = LoggerFactory.getLogger(PdvResource.class);

    private static final String ENTITY_NAME = "pdv";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PdvService pdvService;

    private final PdvQueryService pdvQueryService;

    public PdvResource(PdvService pdvService, PdvQueryService pdvQueryService) {
        this.pdvService = pdvService;
        this.pdvQueryService = pdvQueryService;
    }

    /**
     * {@code POST  /pdvs} : Create a new pdv.
     *
     * @param pdv the pdv to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pdv, or with status {@code 400 (Bad Request)} if the pdv has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pdvs")
    public ResponseEntity<Pdv> createPdv(@Valid @RequestBody Pdv pdv) throws URISyntaxException {
        log.debug("REST request to save Pdv : {}", pdv);
        if (pdv.getId() != null) {
            throw new BadRequestAlertException("A new pdv cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pdv result = pdvService.save(pdv);
        return ResponseEntity.created(new URI("/api/pdvs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pdvs} : Updates an existing pdv.
     *
     * @param pdv the pdv to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pdv,
     * or with status {@code 400 (Bad Request)} if the pdv is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pdv couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pdvs")
    public ResponseEntity<Pdv> updatePdv(@Valid @RequestBody Pdv pdv) throws URISyntaxException {
        log.debug("REST request to update Pdv : {}", pdv);
        if (pdv.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pdv result = pdvService.save(pdv);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pdv.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pdvs} : get all the pdvs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pdvs in body.
     */
    @GetMapping("/pdvs")
    public ResponseEntity<List<Pdv>> getAllPdvs(PdvCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Pdvs by criteria: {}", criteria);
        Page<Pdv> page = pdvQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pdvs/count} : count all the pdvs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pdvs/count")
    public ResponseEntity<Long> countPdvs(PdvCriteria criteria) {
        log.debug("REST request to count Pdvs by criteria: {}", criteria);
        return ResponseEntity.ok().body(pdvQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pdvs/:id} : get the "id" pdv.
     *
     * @param id the id of the pdv to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pdv, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pdvs/{id}")
    public ResponseEntity<Pdv> getPdv(@PathVariable Long id) {
        log.debug("REST request to get Pdv : {}", id);
        Optional<Pdv> pdv = pdvService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pdv);
    }

    /**
     * {@code DELETE  /pdvs/:id} : delete the "id" pdv.
     *
     * @param id the id of the pdv to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pdvs/{id}")
    public ResponseEntity<Void> deletePdv(@PathVariable Long id) {
        log.debug("REST request to delete Pdv : {}", id);
        pdvService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
