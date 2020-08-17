package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Choisit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Choisit}.
 */
public interface ChoisitService {

    /**
     * Save a choisit.
     *
     * @param choisit the entity to save.
     * @return the persisted entity.
     */
    Choisit save(Choisit choisit);

    /**
     * Get all the choisits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Choisit> findAll(Pageable pageable);


    /**
     * Get the "id" choisit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Choisit> findOne(Long id);

    /**
     * Delete the "id" choisit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
