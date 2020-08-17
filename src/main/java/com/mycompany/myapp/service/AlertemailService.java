package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Alertemail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Alertemail}.
 */
public interface AlertemailService {

    /**
     * Save a alertemail.
     *
     * @param alertemail the entity to save.
     * @return the persisted entity.
     */
    Alertemail save(Alertemail alertemail);

    /**
     * Get all the alertemails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Alertemail> findAll(Pageable pageable);


    /**
     * Get the "id" alertemail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Alertemail> findOne(Long id);

    /**
     * Delete the "id" alertemail.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
