package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Hypodrome;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Hypodrome}.
 */
public interface HypodromeService {

    /**
     * Save a hypodrome.
     *
     * @param hypodrome the entity to save.
     * @return the persisted entity.
     */
    Hypodrome save(Hypodrome hypodrome);

    /**
     * Get all the hypodromes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Hypodrome> findAll(Pageable pageable);


    /**
     * Get the "id" hypodrome.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Hypodrome> findOne(Long id);

    /**
     * Delete the "id" hypodrome.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
