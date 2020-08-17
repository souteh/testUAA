package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Typeterminal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Typeterminal}.
 */
public interface TypeterminalService {

    /**
     * Save a typeterminal.
     *
     * @param typeterminal the entity to save.
     * @return the persisted entity.
     */
    Typeterminal save(Typeterminal typeterminal);

    /**
     * Get all the typeterminals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Typeterminal> findAll(Pageable pageable);


    /**
     * Get the "id" typeterminal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Typeterminal> findOne(Long id);

    /**
     * Delete the "id" typeterminal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
