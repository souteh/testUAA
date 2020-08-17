package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Smtp;
import com.mycompany.myapp.service.SmtpService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.SmtpCriteria;
import com.mycompany.myapp.service.SmtpQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Smtp}.
 */
@RestController
@RequestMapping("/api")
public class SmtpResource {

    private final Logger log = LoggerFactory.getLogger(SmtpResource.class);

    private static final String ENTITY_NAME = "smtp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SmtpService smtpService;

    private final SmtpQueryService smtpQueryService;

    public SmtpResource(SmtpService smtpService, SmtpQueryService smtpQueryService) {
        this.smtpService = smtpService;
        this.smtpQueryService = smtpQueryService;
    }

    /**
     * {@code POST  /smtps} : Create a new smtp.
     *
     * @param smtp the smtp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new smtp, or with status {@code 400 (Bad Request)} if the smtp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/smtps")
    public ResponseEntity<Smtp> createSmtp(@Valid @RequestBody Smtp smtp) throws URISyntaxException {
        log.debug("REST request to save Smtp : {}", smtp);
        if (smtp.getId() != null) {
            throw new BadRequestAlertException("A new smtp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Smtp result = smtpService.save(smtp);
        return ResponseEntity.created(new URI("/api/smtps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /smtps} : Updates an existing smtp.
     *
     * @param smtp the smtp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated smtp,
     * or with status {@code 400 (Bad Request)} if the smtp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the smtp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/smtps")
    public ResponseEntity<Smtp> updateSmtp(@Valid @RequestBody Smtp smtp) throws URISyntaxException {
        log.debug("REST request to update Smtp : {}", smtp);
        if (smtp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Smtp result = smtpService.save(smtp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, smtp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /smtps} : get all the smtps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of smtps in body.
     */
    @GetMapping("/smtps")
    public ResponseEntity<List<Smtp>> getAllSmtps(SmtpCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Smtps by criteria: {}", criteria);
        Page<Smtp> page = smtpQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /smtps/count} : count all the smtps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/smtps/count")
    public ResponseEntity<Long> countSmtps(SmtpCriteria criteria) {
        log.debug("REST request to count Smtps by criteria: {}", criteria);
        return ResponseEntity.ok().body(smtpQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /smtps/:id} : get the "id" smtp.
     *
     * @param id the id of the smtp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the smtp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/smtps/{id}")
    public ResponseEntity<Smtp> getSmtp(@PathVariable Long id) {
        log.debug("REST request to get Smtp : {}", id);
        Optional<Smtp> smtp = smtpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(smtp);
    }

    /**
     * {@code DELETE  /smtps/:id} : delete the "id" smtp.
     *
     * @param id the id of the smtp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/smtps/{id}")
    public ResponseEntity<Void> deleteSmtp(@PathVariable Long id) {
        log.debug("REST request to delete Smtp : {}", id);
        smtpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
