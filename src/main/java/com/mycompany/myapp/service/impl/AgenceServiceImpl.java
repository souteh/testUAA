package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AgenceService;
import com.mycompany.myapp.domain.Agence;
import com.mycompany.myapp.repository.AgenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Agence}.
 */
@Service
@Transactional
public class AgenceServiceImpl implements AgenceService {

    private final Logger log = LoggerFactory.getLogger(AgenceServiceImpl.class);

    private final AgenceRepository agenceRepository;

    public AgenceServiceImpl(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    @Override
    public Agence save(Agence agence) {
        log.debug("Request to save Agence : {}", agence);
        return agenceRepository.save(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Agence> findAll(Pageable pageable) {
        log.debug("Request to get all Agences");
        return agenceRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Agence> findOne(Long id) {
        log.debug("Request to get Agence : {}", id);
        return agenceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agence : {}", id);
        agenceRepository.deleteById(id);
    }
}
