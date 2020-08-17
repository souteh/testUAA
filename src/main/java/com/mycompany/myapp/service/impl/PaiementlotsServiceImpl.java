package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PaiementlotsService;
import com.mycompany.myapp.domain.Paiementlots;
import com.mycompany.myapp.repository.PaiementlotsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Paiementlots}.
 */
@Service
@Transactional
public class PaiementlotsServiceImpl implements PaiementlotsService {

    private final Logger log = LoggerFactory.getLogger(PaiementlotsServiceImpl.class);

    private final PaiementlotsRepository paiementlotsRepository;

    public PaiementlotsServiceImpl(PaiementlotsRepository paiementlotsRepository) {
        this.paiementlotsRepository = paiementlotsRepository;
    }

    @Override
    public Paiementlots save(Paiementlots paiementlots) {
        log.debug("Request to save Paiementlots : {}", paiementlots);
        return paiementlotsRepository.save(paiementlots);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Paiementlots> findAll(Pageable pageable) {
        log.debug("Request to get all Paiementlots");
        return paiementlotsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Paiementlots> findOne(Long id) {
        log.debug("Request to get Paiementlots : {}", id);
        return paiementlotsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Paiementlots : {}", id);
        paiementlotsRepository.deleteById(id);
    }
}
