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

import com.mycompany.myapp.domain.Pdv;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.PdvRepository;
import com.mycompany.myapp.service.dto.PdvCriteria;

/**
 * Service for executing complex queries for {@link Pdv} entities in the database.
 * The main input is a {@link PdvCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Pdv} or a {@link Page} of {@link Pdv} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PdvQueryService extends QueryService<Pdv> {

    private final Logger log = LoggerFactory.getLogger(PdvQueryService.class);

    private final PdvRepository pdvRepository;

    public PdvQueryService(PdvRepository pdvRepository) {
        this.pdvRepository = pdvRepository;
    }

    /**
     * Return a {@link List} of {@link Pdv} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Pdv> findByCriteria(PdvCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pdv> specification = createSpecification(criteria);
        return pdvRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Pdv} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Pdv> findByCriteria(PdvCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pdv> specification = createSpecification(criteria);
        return pdvRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PdvCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pdv> specification = createSpecification(criteria);
        return pdvRepository.count(specification);
    }

    /**
     * Function to convert {@link PdvCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pdv> createSpecification(PdvCriteria criteria) {
        Specification<Pdv> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Pdv_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumero(), Pdv_.numero));
            }
            if (criteria.getAgence() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgence(), Pdv_.agence));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Pdv_.designation));
            }
            if (criteria.getVille() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVille(), Pdv_.ville));
            }
            if (criteria.getStatut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatut(), Pdv_.statut));
            }
        }
        return specification;
    }
}
