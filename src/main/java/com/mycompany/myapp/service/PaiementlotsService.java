package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Paiementlots;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Paiementlots}.
 */
public interface PaiementlotsService {

    /**
     * Save a paiementlots.
     *
     * @param paiementlots the entity to save.
     * @return the persisted entity.
     */
    Paiementlots save(Paiementlots paiementlots);

    /**
     * Get all the paiementlots.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Paiementlots> findAll(Pageable pageable);


    /**
     * Get the "id" paiementlots.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Paiementlots> findOne(Long id);

    /**
     * Delete the "id" paiementlots.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
