package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.I18nTextTranslation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link I18nTextTranslation}.
 */
public interface I18nTextTranslationService {

    /**
     * Save a i18nTextTranslation.
     *
     * @param i18nTextTranslation the entity to save.
     * @return the persisted entity.
     */
    I18nTextTranslation save(I18nTextTranslation i18nTextTranslation);

    /**
     * Get all the i18nTextTranslations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<I18nTextTranslation> findAll(Pageable pageable);


    /**
     * Get the "id" i18nTextTranslation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<I18nTextTranslation> findOne(Long id);

    /**
     * Delete the "id" i18nTextTranslation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
