package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Smtp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Smtp}.
 */
public interface SmtpService {

    /**
     * Save a smtp.
     *
     * @param smtp the entity to save.
     * @return the persisted entity.
     */
    Smtp save(Smtp smtp);

    /**
     * Get all the smtps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Smtp> findAll(Pageable pageable);


    /**
     * Get the "id" smtp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Smtp> findOne(Long id);

    /**
     * Delete the "id" smtp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
