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

import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.ProduitRepository;
import com.mycompany.myapp.service.dto.ProduitCriteria;

/**
 * Service for executing complex queries for {@link Produit} entities in the database.
 * The main input is a {@link ProduitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Produit} or a {@link Page} of {@link Produit} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProduitQueryService extends QueryService<Produit> {

    private final Logger log = LoggerFactory.getLogger(ProduitQueryService.class);

    private final ProduitRepository produitRepository;

    public ProduitQueryService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    /**
     * Return a {@link List} of {@link Produit} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Produit> findByCriteria(ProduitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Produit} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Produit> findByCriteria(ProduitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.count(specification);
    }

    /**
     * Function to convert {@link ProduitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Produit> createSpecification(ProduitCriteria criteria) {
        Specification<Produit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Produit_.id));
            }
            if (criteria.getIdproduit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdproduit(), Produit_.idproduit));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Produit_.designation));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Produit_.libelle));
            }
            if (criteria.getCodeproduit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeproduit(), Produit_.codeproduit));
            }
            if (criteria.getEnjeumin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnjeumin(), Produit_.enjeumin));
            }
            if (criteria.getOrdre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrdre(), Produit_.ordre));
            }
            if (criteria.getNbrechevauxbase() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbrechevauxbase(), Produit_.nbrechevauxbase));
            }
            if (criteria.getNbreminpartant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNbreminpartant(), Produit_.nbreminpartant));
            }
            if (criteria.getChampx() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChampx(), Produit_.champx));
            }
            if (criteria.getExpress() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpress(), Produit_.express));
            }
            if (criteria.getAppartenance() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAppartenance(), Produit_.appartenance));
            }
            if (criteria.getEnjeumax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnjeumax(), Produit_.enjeumax));
            }
            if (criteria.getStatut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatut(), Produit_.statut));
            }
        }
        return specification;
    }
}
