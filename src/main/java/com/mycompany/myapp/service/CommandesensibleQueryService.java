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

import com.mycompany.myapp.domain.Commandesensible;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.CommandesensibleRepository;
import com.mycompany.myapp.service.dto.CommandesensibleCriteria;

/**
 * Service for executing complex queries for {@link Commandesensible} entities in the database.
 * The main input is a {@link CommandesensibleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Commandesensible} or a {@link Page} of {@link Commandesensible} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommandesensibleQueryService extends QueryService<Commandesensible> {

    private final Logger log = LoggerFactory.getLogger(CommandesensibleQueryService.class);

    private final CommandesensibleRepository commandesensibleRepository;

    public CommandesensibleQueryService(CommandesensibleRepository commandesensibleRepository) {
        this.commandesensibleRepository = commandesensibleRepository;
    }

    /**
     * Return a {@link List} of {@link Commandesensible} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Commandesensible> findByCriteria(CommandesensibleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Commandesensible> specification = createSpecification(criteria);
        return commandesensibleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Commandesensible} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Commandesensible> findByCriteria(CommandesensibleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Commandesensible> specification = createSpecification(criteria);
        return commandesensibleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommandesensibleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Commandesensible> specification = createSpecification(criteria);
        return commandesensibleRepository.count(specification);
    }

    /**
     * Function to convert {@link CommandesensibleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Commandesensible> createSpecification(CommandesensibleCriteria criteria) {
        Specification<Commandesensible> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Commandesensible_.id));
            }
            if (criteria.getIdcommandesensible() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdcommandesensible(), Commandesensible_.idcommandesensible));
            }
            if (criteria.getCodecommandesensible() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodecommandesensible(), Commandesensible_.codecommandesensible));
            }
            if (criteria.getLibellecommandesensible() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibellecommandesensible(), Commandesensible_.libellecommandesensible));
            }
        }
        return specification;
    }
}
