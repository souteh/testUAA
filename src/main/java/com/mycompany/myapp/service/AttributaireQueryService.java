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

import com.mycompany.myapp.domain.Attributaire;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.AttributaireRepository;
import com.mycompany.myapp.service.dto.AttributaireCriteria;

/**
 * Service for executing complex queries for {@link Attributaire} entities in the database.
 * The main input is a {@link AttributaireCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Attributaire} or a {@link Page} of {@link Attributaire} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttributaireQueryService extends QueryService<Attributaire> {

    private final Logger log = LoggerFactory.getLogger(AttributaireQueryService.class);

    private final AttributaireRepository attributaireRepository;

    public AttributaireQueryService(AttributaireRepository attributaireRepository) {
        this.attributaireRepository = attributaireRepository;
    }

    /**
     * Return a {@link List} of {@link Attributaire} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Attributaire> findByCriteria(AttributaireCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Attributaire> specification = createSpecification(criteria);
        return attributaireRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Attributaire} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Attributaire> findByCriteria(AttributaireCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Attributaire> specification = createSpecification(criteria);
        return attributaireRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttributaireCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Attributaire> specification = createSpecification(criteria);
        return attributaireRepository.count(specification);
    }

    /**
     * Function to convert {@link AttributaireCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Attributaire> createSpecification(AttributaireCriteria criteria) {
        Specification<Attributaire> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Attributaire_.id));
            }
            if (criteria.getIdattributaire() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdattributaire(), Attributaire_.idattributaire));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Attributaire_.libelle));
            }
            if (criteria.getCodeattributaire() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeattributaire(), Attributaire_.codeattributaire));
            }
        }
        return specification;
    }
}
