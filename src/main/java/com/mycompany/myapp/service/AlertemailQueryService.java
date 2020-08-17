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

import com.mycompany.myapp.domain.Alertemail;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.AlertemailRepository;
import com.mycompany.myapp.service.dto.AlertemailCriteria;

/**
 * Service for executing complex queries for {@link Alertemail} entities in the database.
 * The main input is a {@link AlertemailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Alertemail} or a {@link Page} of {@link Alertemail} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlertemailQueryService extends QueryService<Alertemail> {

    private final Logger log = LoggerFactory.getLogger(AlertemailQueryService.class);

    private final AlertemailRepository alertemailRepository;

    public AlertemailQueryService(AlertemailRepository alertemailRepository) {
        this.alertemailRepository = alertemailRepository;
    }

    /**
     * Return a {@link List} of {@link Alertemail} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Alertemail> findByCriteria(AlertemailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Alertemail> specification = createSpecification(criteria);
        return alertemailRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Alertemail} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Alertemail> findByCriteria(AlertemailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Alertemail> specification = createSpecification(criteria);
        return alertemailRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlertemailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Alertemail> specification = createSpecification(criteria);
        return alertemailRepository.count(specification);
    }

    /**
     * Function to convert {@link AlertemailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Alertemail> createSpecification(AlertemailCriteria criteria) {
        Specification<Alertemail> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Alertemail_.id));
            }
            if (criteria.getIdalertemail() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdalertemail(), Alertemail_.idalertemail));
            }
            if (criteria.getCodealertemail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodealertemail(), Alertemail_.codealertemail));
            }
            if (criteria.getTypealertemail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypealertemail(), Alertemail_.typealertemail));
            }
            if (criteria.getObjetalertemail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObjetalertemail(), Alertemail_.objetalertemail));
            }
            if (criteria.getAdressemaildiffusion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdressemaildiffusion(), Alertemail_.adressemaildiffusion));
            }
        }
        return specification;
    }
}
