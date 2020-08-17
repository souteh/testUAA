package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.I18nTextTranslationService;
import com.mycompany.myapp.domain.I18nTextTranslation;
import com.mycompany.myapp.repository.I18nTextTranslationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link I18nTextTranslation}.
 */
@Service
@Transactional
public class I18nTextTranslationServiceImpl implements I18nTextTranslationService {

    private final Logger log = LoggerFactory.getLogger(I18nTextTranslationServiceImpl.class);

    private final I18nTextTranslationRepository i18nTextTranslationRepository;

    public I18nTextTranslationServiceImpl(I18nTextTranslationRepository i18nTextTranslationRepository) {
        this.i18nTextTranslationRepository = i18nTextTranslationRepository;
    }

    @Override
    public I18nTextTranslation save(I18nTextTranslation i18nTextTranslation) {
        log.debug("Request to save I18nTextTranslation : {}", i18nTextTranslation);
        return i18nTextTranslationRepository.save(i18nTextTranslation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<I18nTextTranslation> findAll(Pageable pageable) {
        log.debug("Request to get all I18nTextTranslations");
        return i18nTextTranslationRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<I18nTextTranslation> findOne(Long id) {
        log.debug("Request to get I18nTextTranslation : {}", id);
        return i18nTextTranslationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete I18nTextTranslation : {}", id);
        i18nTextTranslationRepository.deleteById(id);
    }
}
