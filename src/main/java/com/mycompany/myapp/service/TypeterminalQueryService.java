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

import com.mycompany.myapp.domain.Typeterminal;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.TypeterminalRepository;
import com.mycompany.myapp.service.dto.TypeterminalCriteria;

/**
 * Service for executing complex queries for {@link Typeterminal} entities in the database.
 * The main input is a {@link TypeterminalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Typeterminal} or a {@link Page} of {@link Typeterminal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeterminalQueryService extends QueryService<Typeterminal> {

    private final Logger log = LoggerFactory.getLogger(TypeterminalQueryService.class);

    private final TypeterminalRepository typeterminalRepository;

    public TypeterminalQueryService(TypeterminalRepository typeterminalRepository) {
        this.typeterminalRepository = typeterminalRepository;
    }

    /**
     * Return a {@link List} of {@link Typeterminal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Typeterminal> findByCriteria(TypeterminalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Typeterminal> specification = createSpecification(criteria);
        return typeterminalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Typeterminal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Typeterminal> findByCriteria(TypeterminalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Typeterminal> specification = createSpecification(criteria);
        return typeterminalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeterminalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Typeterminal> specification = createSpecification(criteria);
        return typeterminalRepository.count(specification);
    }

    /**
     * Function to convert {@link TypeterminalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Typeterminal> createSpecification(TypeterminalCriteria criteria) {
        Specification<Typeterminal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Typeterminal_.id));
            }
            if (criteria.getIdtypeterminal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdtypeterminal(), Typeterminal_.idtypeterminal));
            }
            if (criteria.getCodetypeterminal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodetypeterminal(), Typeterminal_.codetypeterminal));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Typeterminal_.libelle));
            }
        }
        return specification;
    }
}
