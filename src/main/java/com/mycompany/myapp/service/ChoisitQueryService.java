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

import com.mycompany.myapp.domain.Choisit;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.ChoisitRepository;
import com.mycompany.myapp.service.dto.ChoisitCriteria;

/**
 * Service for executing complex queries for {@link Choisit} entities in the database.
 * The main input is a {@link ChoisitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Choisit} or a {@link Page} of {@link Choisit} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChoisitQueryService extends QueryService<Choisit> {

    private final Logger log = LoggerFactory.getLogger(ChoisitQueryService.class);

    private final ChoisitRepository choisitRepository;

    public ChoisitQueryService(ChoisitRepository choisitRepository) {
        this.choisitRepository = choisitRepository;
    }

    /**
     * Return a {@link List} of {@link Choisit} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Choisit> findByCriteria(ChoisitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Choisit> specification = createSpecification(criteria);
        return choisitRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Choisit} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Choisit> findByCriteria(ChoisitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Choisit> specification = createSpecification(criteria);
        return choisitRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChoisitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Choisit> specification = createSpecification(criteria);
        return choisitRepository.count(specification);
    }

    /**
     * Function to convert {@link ChoisitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Choisit> createSpecification(ChoisitCriteria criteria) {
        Specification<Choisit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Choisit_.id));
            }
            if (criteria.getIdproduit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdproduit(), Choisit_.idproduit));
            }
            if (criteria.getIdattributaireId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdattributaireId(),
                    root -> root.join(Choisit_.idattributaire, JoinType.LEFT).get(Attributaire_.id)));
            }
        }
        return specification;
    }
}
