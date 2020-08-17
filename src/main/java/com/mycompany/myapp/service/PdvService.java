package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Pdv;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Pdv}.
 */
public interface PdvService {

    /**
     * Save a pdv.
     *
     * @param pdv the entity to save.
     * @return the persisted entity.
     */
    Pdv save(Pdv pdv);

    /**
     * Get all the pdvs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pdv> findAll(Pageable pageable);


    /**
     * Get the "id" pdv.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pdv> findOne(Long id);

    /**
     * Delete the "id" pdv.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
