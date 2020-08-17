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

import com.mycompany.myapp.domain.Delaiannulation;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.DelaiannulationRepository;
import com.mycompany.myapp.service.dto.DelaiannulationCriteria;

/**
 * Service for executing complex queries for {@link Delaiannulation} entities in the database.
 * The main input is a {@link DelaiannulationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Delaiannulation} or a {@link Page} of {@link Delaiannulation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DelaiannulationQueryService extends QueryService<Delaiannulation> {

    private final Logger log = LoggerFactory.getLogger(DelaiannulationQueryService.class);

    private final DelaiannulationRepository delaiannulationRepository;

    public DelaiannulationQueryService(DelaiannulationRepository delaiannulationRepository) {
        this.delaiannulationRepository = delaiannulationRepository;
    }

    /**
     * Return a {@link List} of {@link Delaiannulation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Delaiannulation> findByCriteria(DelaiannulationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Delaiannulation> specification = createSpecification(criteria);
        return delaiannulationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Delaiannulation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Delaiannulation> findByCriteria(DelaiannulationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Delaiannulation> specification = createSpecification(criteria);
        return delaiannulationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DelaiannulationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Delaiannulation> specification = createSpecification(criteria);
        return delaiannulationRepository.count(specification);
    }

    /**
     * Function to convert {@link DelaiannulationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Delaiannulation> createSpecification(DelaiannulationCriteria criteria) {
        Specification<Delaiannulation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Delaiannulation_.id));
            }
            if (criteria.getIddelaiannulation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIddelaiannulation(), Delaiannulation_.iddelaiannulation));
            }
            if (criteria.getCodedelaiannulation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodedelaiannulation(), Delaiannulation_.codedelaiannulation));
            }
            if (criteria.getDesignationdelaiannulation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignationdelaiannulation(), Delaiannulation_.designationdelaiannulation));
            }
            if (criteria.getValeurdelaiannulation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeurdelaiannulation(), Delaiannulation_.valeurdelaiannulation));
            }
            if (criteria.getStatut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatut(), Delaiannulation_.statut));
            }
        }
        return specification;
    }
}
