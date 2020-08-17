package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.I18nTextTranslation;
import com.mycompany.myapp.service.I18nTextTranslationService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.I18nTextTranslationCriteria;
import com.mycompany.myapp.service.I18nTextTranslationQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.I18nTextTranslation}.
 */
@RestController
@RequestMapping("/api")
public class I18nTextTranslationResource {

    private final Logger log = LoggerFactory.getLogger(I18nTextTranslationResource.class);

    private static final String ENTITY_NAME = "i18nTextTranslation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final I18nTextTranslationService i18nTextTranslationService;

    private final I18nTextTranslationQueryService i18nTextTranslationQueryService;

    public I18nTextTranslationResource(I18nTextTranslationService i18nTextTranslationService, I18nTextTranslationQueryService i18nTextTranslationQueryService) {
        this.i18nTextTranslationService = i18nTextTranslationService;
        this.i18nTextTranslationQueryService = i18nTextTranslationQueryService;
    }

    /**
     * {@code POST  /i-18-n-text-translations} : Create a new i18nTextTranslation.
     *
     * @param i18nTextTranslation the i18nTextTranslation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new i18nTextTranslation, or with status {@code 400 (Bad Request)} if the i18nTextTranslation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/i-18-n-text-translations")
    public ResponseEntity<I18nTextTranslation> createI18nTextTranslation(@Valid @RequestBody I18nTextTranslation i18nTextTranslation) throws URISyntaxException {
        log.debug("REST request to save I18nTextTranslation : {}", i18nTextTranslation);
        if (i18nTextTranslation.getId() != null) {
            throw new BadRequestAlertException("A new i18nTextTranslation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        I18nTextTranslation result = i18nTextTranslationService.save(i18nTextTranslation);
        return ResponseEntity.created(new URI("/api/i-18-n-text-translations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /i-18-n-text-translations} : Updates an existing i18nTextTranslation.
     *
     * @param i18nTextTranslation the i18nTextTranslation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated i18nTextTranslation,
     * or with status {@code 400 (Bad Request)} if the i18nTextTranslation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the i18nTextTranslation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/i-18-n-text-translations")
    public ResponseEntity<I18nTextTranslation> updateI18nTextTranslation(@Valid @RequestBody I18nTextTranslation i18nTextTranslation) throws URISyntaxException {
        log.debug("REST request to update I18nTextTranslation : {}", i18nTextTranslation);
        if (i18nTextTranslation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        I18nTextTranslation result = i18nTextTranslationService.save(i18nTextTranslation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, i18nTextTranslation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /i-18-n-text-translations} : get all the i18nTextTranslations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of i18nTextTranslations in body.
     */
    @GetMapping("/i-18-n-text-translations")
    public ResponseEntity<List<I18nTextTranslation>> getAllI18nTextTranslations(I18nTextTranslationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get I18nTextTranslations by criteria: {}", criteria);
        Page<I18nTextTranslation> page = i18nTextTranslationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /i-18-n-text-translations/count} : count all the i18nTextTranslations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/i-18-n-text-translations/count")
    public ResponseEntity<Long> countI18nTextTranslations(I18nTextTranslationCriteria criteria) {
        log.debug("REST request to count I18nTextTranslations by criteria: {}", criteria);
        return ResponseEntity.ok().body(i18nTextTranslationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /i-18-n-text-translations/:id} : get the "id" i18nTextTranslation.
     *
     * @param id the id of the i18nTextTranslation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the i18nTextTranslation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/i-18-n-text-translations/{id}")
    public ResponseEntity<I18nTextTranslation> getI18nTextTranslation(@PathVariable Long id) {
        log.debug("REST request to get I18nTextTranslation : {}", id);
        Optional<I18nTextTranslation> i18nTextTranslation = i18nTextTranslationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(i18nTextTranslation);
    }

    /**
     * {@code DELETE  /i-18-n-text-translations/:id} : delete the "id" i18nTextTranslation.
     *
     * @param id the id of the i18nTextTranslation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/i-18-n-text-translations/{id}")
    public ResponseEntity<Void> deleteI18nTextTranslation(@PathVariable Long id) {
        log.debug("REST request to delete I18nTextTranslation : {}", id);
        i18nTextTranslationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
