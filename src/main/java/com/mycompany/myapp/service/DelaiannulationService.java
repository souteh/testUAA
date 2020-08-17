package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Delaiannulation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Delaiannulation}.
 */
public interface DelaiannulationService {

    /**
     * Save a delaiannulation.
     *
     * @param delaiannulation the entity to save.
     * @return the persisted entity.
     */
    Delaiannulation save(Delaiannulation delaiannulation);

    /**
     * Get all the delaiannulations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Delaiannulation> findAll(Pageable pageable);


    /**
     * Get the "id" delaiannulation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Delaiannulation> findOne(Long id);

    /**
     * Delete the "id" delaiannulation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
