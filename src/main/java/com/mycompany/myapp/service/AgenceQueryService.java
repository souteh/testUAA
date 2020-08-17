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

import com.mycompany.myapp.domain.Agence;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.AgenceRepository;
import com.mycompany.myapp.service.dto.AgenceCriteria;

/**
 * Service for executing complex queries for {@link Agence} entities in the database.
 * The main input is a {@link AgenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Agence} or a {@link Page} of {@link Agence} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AgenceQueryService extends QueryService<Agence> {

    private final Logger log = LoggerFactory.getLogger(AgenceQueryService.class);

    private final AgenceRepository agenceRepository;

    public AgenceQueryService(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    /**
     * Return a {@link List} of {@link Agence} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Agence> findByCriteria(AgenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Agence> specification = createSpecification(criteria);
        return agenceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Agence} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Agence> findByCriteria(AgenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Agence> specification = createSpecification(criteria);
        return agenceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AgenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Agence> specification = createSpecification(criteria);
        return agenceRepository.count(specification);
    }

    /**
     * Function to convert {@link AgenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Agence> createSpecification(AgenceCriteria criteria) {
        Specification<Agence> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Agence_.id));
            }
            if (criteria.getIdagence() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdagence(), Agence_.idagence));
            }
            if (criteria.getCodeagence() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeagence(), Agence_.codeagence));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumero(), Agence_.numero));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Agence_.nom));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Agence_.adresse));
            }
        }
        return specification;
    }
}
