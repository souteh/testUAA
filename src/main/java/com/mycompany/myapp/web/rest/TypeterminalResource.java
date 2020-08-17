package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Typeterminal;
import com.mycompany.myapp.service.TypeterminalService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.TypeterminalCriteria;
import com.mycompany.myapp.service.TypeterminalQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Typeterminal}.
 */
@RestController
@RequestMapping("/api")
public class TypeterminalResource {

    private final Logger log = LoggerFactory.getLogger(TypeterminalResource.class);

    private static final String ENTITY_NAME = "typeterminal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeterminalService typeterminalService;

    private final TypeterminalQueryService typeterminalQueryService;

    public TypeterminalResource(TypeterminalService typeterminalService, TypeterminalQueryService typeterminalQueryService) {
        this.typeterminalService = typeterminalService;
        this.typeterminalQueryService = typeterminalQueryService;
    }

    /**
     * {@code POST  /typeterminals} : Create a new typeterminal.
     *
     * @param typeterminal the typeterminal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeterminal, or with status {@code 400 (Bad Request)} if the typeterminal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/typeterminals")
    public ResponseEntity<Typeterminal> createTypeterminal(@Valid @RequestBody Typeterminal typeterminal) throws URISyntaxException {
        log.debug("REST request to save Typeterminal : {}", typeterminal);
        if (typeterminal.getId() != null) {
            throw new BadRequestAlertException("A new typeterminal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Typeterminal result = typeterminalService.save(typeterminal);
        return ResponseEntity.created(new URI("/api/typeterminals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /typeterminals} : Updates an existing typeterminal.
     *
     * @param typeterminal the typeterminal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeterminal,
     * or with status {@code 400 (Bad Request)} if the typeterminal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeterminal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/typeterminals")
    public ResponseEntity<Typeterminal> updateTypeterminal(@Valid @RequestBody Typeterminal typeterminal) throws URISyntaxException {
        log.debug("REST request to update Typeterminal : {}", typeterminal);
        if (typeterminal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Typeterminal result = typeterminalService.save(typeterminal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeterminal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /typeterminals} : get all the typeterminals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeterminals in body.
     */
    @GetMapping("/typeterminals")
    public ResponseEntity<List<Typeterminal>> getAllTypeterminals(TypeterminalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Typeterminals by criteria: {}", criteria);
        Page<Typeterminal> page = typeterminalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /typeterminals/count} : count all the typeterminals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/typeterminals/count")
    public ResponseEntity<Long> countTypeterminals(TypeterminalCriteria criteria) {
        log.debug("REST request to count Typeterminals by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeterminalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /typeterminals/:id} : get the "id" typeterminal.
     *
     * @param id the id of the typeterminal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeterminal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/typeterminals/{id}")
    public ResponseEntity<Typeterminal> getTypeterminal(@PathVariable Long id) {
        log.debug("REST request to get Typeterminal : {}", id);
        Optional<Typeterminal> typeterminal = typeterminalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeterminal);
    }

    /**
     * {@code DELETE  /typeterminals/:id} : delete the "id" typeterminal.
     *
     * @param id the id of the typeterminal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/typeterminals/{id}")
    public ResponseEntity<Void> deleteTypeterminal(@PathVariable Long id) {
        log.debug("REST request to delete Typeterminal : {}", id);
        typeterminalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
