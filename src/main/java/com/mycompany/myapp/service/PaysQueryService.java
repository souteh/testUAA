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

import com.mycompany.myapp.domain.Pays;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.PaysRepository;
import com.mycompany.myapp.service.dto.PaysCriteria;

/**
 * Service for executing complex queries for {@link Pays} entities in the database.
 * The main input is a {@link PaysCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Pays} or a {@link Page} of {@link Pays} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaysQueryService extends QueryService<Pays> {

    private final Logger log = LoggerFactory.getLogger(PaysQueryService.class);

    private final PaysRepository paysRepository;

    public PaysQueryService(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }

    /**
     * Return a {@link List} of {@link Pays} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Pays> findByCriteria(PaysCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pays> specification = createSpecification(criteria);
        return paysRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Pays} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Pays> findByCriteria(PaysCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pays> specification = createSpecification(criteria);
        return paysRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaysCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pays> specification = createSpecification(criteria);
        return paysRepository.count(specification);
    }

    /**
     * Function to convert {@link PaysCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pays> createSpecification(PaysCriteria criteria) {
        Specification<Pays> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Pays_.id));
            }
            if (criteria.getIdpays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdpays(), Pays_.idpays));
            }
            if (criteria.getCodepays() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodepays(), Pays_.codepays));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Pays_.designation));
            }
        }
        return specification;
    }
}
