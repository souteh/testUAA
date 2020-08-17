package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ProduitService;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.repository.ProduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Produit}.
 */
@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {

    private final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private final ProduitRepository produitRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit save(Produit produit) {
        log.debug("Request to save Produit : {}", produit);
        return produitRepository.save(produit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Produit> findAll(Pageable pageable) {
        log.debug("Request to get all Produits");
        return produitRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Produit> findOne(Long id) {
        log.debug("Request to get Produit : {}", id);
        return produitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produit : {}", id);
        produitRepository.deleteById(id);
    }
}
