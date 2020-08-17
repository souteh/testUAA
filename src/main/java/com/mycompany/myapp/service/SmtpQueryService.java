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

import com.mycompany.myapp.domain.Smtp;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SmtpRepository;
import com.mycompany.myapp.service.dto.SmtpCriteria;

/**
 * Service for executing complex queries for {@link Smtp} entities in the database.
 * The main input is a {@link SmtpCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Smtp} or a {@link Page} of {@link Smtp} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SmtpQueryService extends QueryService<Smtp> {

    private final Logger log = LoggerFactory.getLogger(SmtpQueryService.class);

    private final SmtpRepository smtpRepository;

    public SmtpQueryService(SmtpRepository smtpRepository) {
        this.smtpRepository = smtpRepository;
    }

    /**
     * Return a {@link List} of {@link Smtp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Smtp> findByCriteria(SmtpCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Smtp> specification = createSpecification(criteria);
        return smtpRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Smtp} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Smtp> findByCriteria(SmtpCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Smtp> specification = createSpecification(criteria);
        return smtpRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SmtpCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Smtp> specification = createSpecification(criteria);
        return smtpRepository.count(specification);
    }

    /**
     * Function to convert {@link SmtpCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Smtp> createSpecification(SmtpCriteria criteria) {
        Specification<Smtp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Smtp_.id));
            }
            if (criteria.getIdsmtp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdsmtp(), Smtp_.idsmtp));
            }
            if (criteria.getCodesmtp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodesmtp(), Smtp_.codesmtp));
            }
            if (criteria.getAdresseipsmtp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresseipsmtp(), Smtp_.adresseipsmtp));
            }
            if (criteria.getAuthentification() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthentification(), Smtp_.authentification));
            }
            if (criteria.getStatut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatut(), Smtp_.statut));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), Smtp_.login));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), Smtp_.password));
            }
        }
        return specification;
    }
}
