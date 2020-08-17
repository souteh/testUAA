package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Attributaire;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Attributaire}.
 */
public interface AttributaireService {

    /**
     * Save a attributaire.
     *
     * @param attributaire the entity to save.
     * @return the persisted entity.
     */
    Attributaire save(Attributaire attributaire);

    /**
     * Get all the attributaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Attributaire> findAll(Pageable pageable);


    /**
     * Get the "id" attributaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Attributaire> findOne(Long id);

    /**
     * Delete the "id" attributaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
