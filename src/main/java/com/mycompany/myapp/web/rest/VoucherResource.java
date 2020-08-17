package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Voucher;
import com.mycompany.myapp.service.VoucherService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.VoucherCriteria;
import com.mycompany.myapp.service.VoucherQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Voucher}.
 */
@RestController
@RequestMapping("/api")
public class VoucherResource {

    private final Logger log = LoggerFactory.getLogger(VoucherResource.class);

    private static final String ENTITY_NAME = "voucher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoucherService voucherService;

    private final VoucherQueryService voucherQueryService;

    public VoucherResource(VoucherService voucherService, VoucherQueryService voucherQueryService) {
        this.voucherService = voucherService;
        this.voucherQueryService = voucherQueryService;
    }

    /**
     * {@code POST  /vouchers} : Create a new voucher.
     *
     * @param voucher the voucher to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voucher, or with status {@code 400 (Bad Request)} if the voucher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vouchers")
    public ResponseEntity<Voucher> createVoucher(@Valid @RequestBody Voucher voucher) throws URISyntaxException {
        log.debug("REST request to save Voucher : {}", voucher);
        if (voucher.getId() != null) {
            throw new BadRequestAlertException("A new voucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Voucher result = voucherService.save(voucher);
        return ResponseEntity.created(new URI("/api/vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vouchers} : Updates an existing voucher.
     *
     * @param voucher the voucher to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voucher,
     * or with status {@code 400 (Bad Request)} if the voucher is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voucher couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vouchers")
    public ResponseEntity<Voucher> updateVoucher(@Valid @RequestBody Voucher voucher) throws URISyntaxException {
        log.debug("REST request to update Voucher : {}", voucher);
        if (voucher.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Voucher result = voucherService.save(voucher);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voucher.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vouchers} : get all the vouchers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vouchers in body.
     */
    @GetMapping("/vouchers")
    public ResponseEntity<List<Voucher>> getAllVouchers(VoucherCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Vouchers by criteria: {}", criteria);
        Page<Voucher> page = voucherQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vouchers/count} : count all the vouchers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vouchers/count")
    public ResponseEntity<Long> countVouchers(VoucherCriteria criteria) {
        log.debug("REST request to count Vouchers by criteria: {}", criteria);
        return ResponseEntity.ok().body(voucherQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vouchers/:id} : get the "id" voucher.
     *
     * @param id the id of the voucher to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voucher, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vouchers/{id}")
    public ResponseEntity<Voucher> getVoucher(@PathVariable Long id) {
        log.debug("REST request to get Voucher : {}", id);
        Optional<Voucher> voucher = voucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voucher);
    }

    /**
     * {@code DELETE  /vouchers/:id} : delete the "id" voucher.
     *
     * @param id the id of the voucher to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vouchers/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        log.debug("REST request to delete Voucher : {}", id);
        voucherService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
