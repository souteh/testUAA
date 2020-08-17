package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Pays;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Pays}.
 */
public interface PaysService {

    /**
     * Save a pays.
     *
     * @param pays the entity to save.
     * @return the persisted entity.
     */
    Pays save(Pays pays);

    /**
     * Get all the pays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pays> findAll(Pageable pageable);


    /**
     * Get the "id" pays.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pays> findOne(Long id);

    /**
     * Delete the "id" pays.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
