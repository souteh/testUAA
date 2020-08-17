package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Commandesensible;
import com.mycompany.myapp.service.CommandesensibleService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CommandesensibleCriteria;
import com.mycompany.myapp.service.CommandesensibleQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Commandesensible}.
 */
@RestController
@RequestMapping("/api")
public class CommandesensibleResource {

    private final Logger log = LoggerFactory.getLogger(CommandesensibleResource.class);

    private static final String ENTITY_NAME = "commandesensible";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommandesensibleService commandesensibleService;

    private final CommandesensibleQueryService commandesensibleQueryService;

    public CommandesensibleResource(CommandesensibleService commandesensibleService, CommandesensibleQueryService commandesensibleQueryService) {
        this.commandesensibleService = commandesensibleService;
        this.commandesensibleQueryService = commandesensibleQueryService;
    }

    /**
     * {@code POST  /commandesensibles} : Create a new commandesensible.
     *
     * @param commandesensible the commandesensible to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commandesensible, or with status {@code 400 (Bad Request)} if the commandesensible has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commandesensibles")
    public ResponseEntity<Commandesensible> createCommandesensible(@Valid @RequestBody Commandesensible commandesensible) throws URISyntaxException {
        log.debug("REST request to save Commandesensible : {}", commandesensible);
        if (commandesensible.getId() != null) {
            throw new BadRequestAlertException("A new commandesensible cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Commandesensible result = commandesensibleService.save(commandesensible);
        return ResponseEntity.created(new URI("/api/commandesensibles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commandesensibles} : Updates an existing commandesensible.
     *
     * @param commandesensible the commandesensible to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commandesensible,
     * or with status {@code 400 (Bad Request)} if the commandesensible is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commandesensible couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commandesensibles")
    public ResponseEntity<Commandesensible> updateCommandesensible(@Valid @RequestBody Commandesensible commandesensible) throws URISyntaxException {
        log.debug("REST request to update Commandesensible : {}", commandesensible);
        if (commandesensible.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Commandesensible result = commandesensibleService.save(commandesensible);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commandesensible.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commandesensibles} : get all the commandesensibles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commandesensibles in body.
     */
    @GetMapping("/commandesensibles")
    public ResponseEntity<List<Commandesensible>> getAllCommandesensibles(CommandesensibleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Commandesensibles by criteria: {}", criteria);
        Page<Commandesensible> page = commandesensibleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commandesensibles/count} : count all the commandesensibles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/commandesensibles/count")
    public ResponseEntity<Long> countCommandesensibles(CommandesensibleCriteria criteria) {
        log.debug("REST request to count Commandesensibles by criteria: {}", criteria);
        return ResponseEntity.ok().body(commandesensibleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /commandesensibles/:id} : get the "id" commandesensible.
     *
     * @param id the id of the commandesensible to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commandesensible, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commandesensibles/{id}")
    public ResponseEntity<Commandesensible> getCommandesensible(@PathVariable Long id) {
        log.debug("REST request to get Commandesensible : {}", id);
        Optional<Commandesensible> commandesensible = commandesensibleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commandesensible);
    }

    /**
     * {@code DELETE  /commandesensibles/:id} : delete the "id" commandesensible.
     *
     * @param id the id of the commandesensible to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commandesensibles/{id}")
    public ResponseEntity<Void> deleteCommandesensible(@PathVariable Long id) {
        log.debug("REST request to delete Commandesensible : {}", id);
        commandesensibleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
