package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Delaiannulation;
import com.mycompany.myapp.service.DelaiannulationService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.DelaiannulationCriteria;
import com.mycompany.myapp.service.DelaiannulationQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Delaiannulation}.
 */
@RestController
@RequestMapping("/api")
public class DelaiannulationResource {

    private final Logger log = LoggerFactory.getLogger(DelaiannulationResource.class);

    private static final String ENTITY_NAME = "delaiannulation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DelaiannulationService delaiannulationService;

    private final DelaiannulationQueryService delaiannulationQueryService;

    public DelaiannulationResource(DelaiannulationService delaiannulationService, DelaiannulationQueryService delaiannulationQueryService) {
        this.delaiannulationService = delaiannulationService;
        this.delaiannulationQueryService = delaiannulationQueryService;
    }

    /**
     * {@code POST  /delaiannulations} : Create a new delaiannulation.
     *
     * @param delaiannulation the delaiannulation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new delaiannulation, or with status {@code 400 (Bad Request)} if the delaiannulation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/delaiannulations")
    public ResponseEntity<Delaiannulation> createDelaiannulation(@Valid @RequestBody Delaiannulation delaiannulation) throws URISyntaxException {
        log.debug("REST request to save Delaiannulation : {}", delaiannulation);
        if (delaiannulation.getId() != null) {
            throw new BadRequestAlertException("A new delaiannulation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Delaiannulation result = delaiannulationService.save(delaiannulation);
        return ResponseEntity.created(new URI("/api/delaiannulations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /delaiannulations} : Updates an existing delaiannulation.
     *
     * @param delaiannulation the delaiannulation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated delaiannulation,
     * or with status {@code 400 (Bad Request)} if the delaiannulation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the delaiannulation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/delaiannulations")
    public ResponseEntity<Delaiannulation> updateDelaiannulation(@Valid @RequestBody Delaiannulation delaiannulation) throws URISyntaxException {
        log.debug("REST request to update Delaiannulation : {}", delaiannulation);
        if (delaiannulation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Delaiannulation result = delaiannulationService.save(delaiannulation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, delaiannulation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /delaiannulations} : get all the delaiannulations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of delaiannulations in body.
     */
    @GetMapping("/delaiannulations")
    public ResponseEntity<List<Delaiannulation>> getAllDelaiannulations(DelaiannulationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Delaiannulations by criteria: {}", criteria);
        Page<Delaiannulation> page = delaiannulationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /delaiannulations/count} : count all the delaiannulations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/delaiannulations/count")
    public ResponseEntity<Long> countDelaiannulations(DelaiannulationCriteria criteria) {
        log.debug("REST request to count Delaiannulations by criteria: {}", criteria);
        return ResponseEntity.ok().body(delaiannulationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /delaiannulations/:id} : get the "id" delaiannulation.
     *
     * @param id the id of the delaiannulation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the delaiannulation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/delaiannulations/{id}")
    public ResponseEntity<Delaiannulation> getDelaiannulation(@PathVariable Long id) {
        log.debug("REST request to get Delaiannulation : {}", id);
        Optional<Delaiannulation> delaiannulation = delaiannulationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(delaiannulation);
    }

    /**
     * {@code DELETE  /delaiannulations/:id} : delete the "id" delaiannulation.
     *
     * @param id the id of the delaiannulation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/delaiannulations/{id}")
    public ResponseEntity<Void> deleteDelaiannulation(@PathVariable Long id) {
        log.debug("REST request to delete Delaiannulation : {}", id);
        delaiannulationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
