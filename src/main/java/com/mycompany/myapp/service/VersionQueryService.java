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

import com.mycompany.myapp.domain.Version;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.VersionRepository;
import com.mycompany.myapp.service.dto.VersionCriteria;

/**
 * Service for executing complex queries for {@link Version} entities in the database.
 * The main input is a {@link VersionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Version} or a {@link Page} of {@link Version} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VersionQueryService extends QueryService<Version> {

    private final Logger log = LoggerFactory.getLogger(VersionQueryService.class);

    private final VersionRepository versionRepository;

    public VersionQueryService(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    /**
     * Return a {@link List} of {@link Version} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Version> findByCriteria(VersionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Version> specification = createSpecification(criteria);
        return versionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Version} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Version> findByCriteria(VersionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Version> specification = createSpecification(criteria);
        return versionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VersionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Version> specification = createSpecification(criteria);
        return versionRepository.count(specification);
    }

    /**
     * Function to convert {@link VersionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Version> createSpecification(VersionCriteria criteria) {
        Specification<Version> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Version_.id));
            }
            if (criteria.getIdversion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdversion(), Version_.idversion));
            }
            if (criteria.getRefversion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefversion(), Version_.refversion));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Version_.libelle));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Version_.date));
            }
            if (criteria.getFichier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFichier(), Version_.fichier));
            }
            if (criteria.getIdtypeterminalId() != null) {
                specification = specification.and(buildSpecification(criteria.getIdtypeterminalId(),
                    root -> root.join(Version_.idtypeterminal, JoinType.LEFT).get(Typeterminal_.id)));
            }
        }
        return specification;
    }
}
