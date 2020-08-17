package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Commandesensible;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Commandesensible}.
 */
public interface CommandesensibleService {

    /**
     * Save a commandesensible.
     *
     * @param commandesensible the entity to save.
     * @return the persisted entity.
     */
    Commandesensible save(Commandesensible commandesensible);

    /**
     * Get all the commandesensibles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Commandesensible> findAll(Pageable pageable);


    /**
     * Get the "id" commandesensible.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Commandesensible> findOne(Long id);

    /**
     * Delete the "id" commandesensible.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
