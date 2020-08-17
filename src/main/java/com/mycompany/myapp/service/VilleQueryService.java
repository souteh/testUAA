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

import com.mycompany.myapp.domain.Ville;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.VilleRepository;
import com.mycompany.myapp.service.dto.VilleCriteria;

/**
 * Service for executing complex queries for {@link Ville} entities in the database.
 * The main input is a {@link VilleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Ville} or a {@link Page} of {@link Ville} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VilleQueryService extends QueryService<Ville> {

    private final Logger log = LoggerFactory.getLogger(VilleQueryService.class);

    private final VilleRepository villeRepository;

    public VilleQueryService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    /**
     * Return a {@link List} of {@link Ville} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Ville> findByCriteria(VilleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ville> specification = createSpecification(criteria);
        return villeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Ville} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Ville> findByCriteria(VilleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ville> specification = createSpecification(criteria);
        return villeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VilleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ville> specification = createSpecification(criteria);
        return villeRepository.count(specification);
    }

    /**
     * Function to convert {@link VilleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ville> createSpecification(VilleCriteria criteria) {
        Specification<Ville> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ville_.id));
            }
            if (criteria.getIdville() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdville(), Ville_.idville));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDesignation(), Ville_.designation));
            }
            if (criteria.getCodeville() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeville(), Ville_.codeville));
            }
        }
        return specification;
    }
}
