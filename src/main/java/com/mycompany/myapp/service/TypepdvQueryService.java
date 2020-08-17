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

import com.mycompany.myapp.domain.Typepdv;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.TypepdvRepository;
import com.mycompany.myapp.service.dto.TypepdvCriteria;

/**
 * Service for executing complex queries for {@link Typepdv} entities in the database.
 * The main input is a {@link TypepdvCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Typepdv} or a {@link Page} of {@link Typepdv} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypepdvQueryService extends QueryService<Typepdv> {

    private final Logger log = LoggerFactory.getLogger(TypepdvQueryService.class);

    private final TypepdvRepository typepdvRepository;

    public TypepdvQueryService(TypepdvRepository typepdvRepository) {
        this.typepdvRepository = typepdvRepository;
    }

    /**
     * Return a {@link List} of {@link Typepdv} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Typepdv> findByCriteria(TypepdvCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Typepdv> specification = createSpecification(criteria);
        return typepdvRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Typepdv} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Typepdv> findByCriteria(TypepdvCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Typepdv> specification = createSpecification(criteria);
        return typepdvRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypepdvCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Typepdv> specification = createSpecification(criteria);
        return typepdvRepository.count(specification);
    }

    /**
     * Function to convert {@link TypepdvCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Typepdv> createSpecification(TypepdvCriteria criteria) {
        Specification<Typepdv> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Typepdv_.id));
            }
            if (criteria.getIdtypepdv() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdtypepdv(), Typepdv_.idtypepdv));
            }
            if (criteria.getReftypepdv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReftypepdv(), Typepdv_.reftypepdv));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Typepdv_.type));
            }
            if (criteria.getNbremaxterminaux() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbremaxterminaux(), Typepdv_.nbremaxterminaux));
            }
            if (criteria.getPlafondpostpaye() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPlafondpostpaye(), Typepdv_.plafondpostpaye));
            }
        }
        return specification;
    }
}
