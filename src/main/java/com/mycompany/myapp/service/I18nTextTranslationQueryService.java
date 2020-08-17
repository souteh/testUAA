package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.I18nTextTranslation;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.I18nTextTranslationRepository;
import com.mycompany.myapp.service.dto.I18nTextTranslationCriteria;

/**
 * Service for executing complex queries for {@link I18nTextTranslation} entities in the database.
 * The main input is a {@link I18nTextTranslationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link I18nTextTranslation} or a {@link Page} of {@link I18nTextTranslation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class I18nTextTranslationQueryService extends QueryService<I18nTextTranslation> {

    private final Logger log = LoggerFactory.getLogger(I18nTextTranslationQueryService.class);

    private final I18nTextTranslationRepository i18nTextTranslationRepository;

    public I18nTextTranslationQueryService(I18nTextTranslationRepository i18nTextTranslationRepository) {
        this.i18nTextTranslationRepository = i18nTextTranslationRepository;
    }

    /**
     * Return a {@link List} of {@link I18nTextTranslation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<I18nTextTranslation> findByCriteria(I18nTextTranslationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<I18nTextTranslation> specification = createSpecification(criteria);
        return i18nTextTranslationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link I18nTextTranslation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<I18nTextTranslation> findByCriteria(I18nTextTranslationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<I18nTextTranslation> specification = createSpecification(criteria);
        return i18nTextTranslationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(I18nTextTranslationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<I18nTextTranslation> specification = createSpecification(criteria);
        return i18nTextTranslationRepository.count(specification);
    }

    /**
     * Function to convert {@link I18nTextTranslationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<I18nTextTranslation> createSpecification(I18nTextTranslationCriteria criteria) {
        Specification<I18nTextTranslation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), I18nTextTranslation_.id));
            }
            if (criteria.getLang() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLang(), I18nTextTranslation_.lang));
            }
            if (criteria.getTranslation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTranslation(), I18nTextTranslation_.translation));
            }
            if (criteria.geti18nTextId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.geti18nTextId(), I18nTextTranslation_.i18nTextId));
            }
        }
        return specification;
    }
}
