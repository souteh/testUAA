package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Agence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Agence}.
 */
public interface AgenceService {

    /**
     * Save a agence.
     *
     * @param agence the entity to save.
     * @return the persisted entity.
     */
    Agence save(Agence agence);

    /**
     * Get all the agences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Agence> findAll(Pageable pageable);


    /**
     * Get the "id" agence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Agence> findOne(Long id);

    /**
     * Delete the "id" agence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
