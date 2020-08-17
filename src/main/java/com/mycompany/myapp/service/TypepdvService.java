package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Typepdv;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Typepdv}.
 */
public interface TypepdvService {

    /**
     * Save a typepdv.
     *
     * @param typepdv the entity to save.
     * @return the persisted entity.
     */
    Typepdv save(Typepdv typepdv);

    /**
     * Get all the typepdvs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Typepdv> findAll(Pageable pageable);


    /**
     * Get the "id" typepdv.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Typepdv> findOne(Long id);

    /**
     * Delete the "id" typepdv.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
