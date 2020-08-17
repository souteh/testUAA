package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DelaiannulationService;
import com.mycompany.myapp.domain.Delaiannulation;
import com.mycompany.myapp.repository.DelaiannulationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Delaiannulation}.
 */
@Service
@Transactional
public class DelaiannulationServiceImpl implements DelaiannulationService {

    private final Logger log = LoggerFactory.getLogger(DelaiannulationServiceImpl.class);

    private final DelaiannulationRepository delaiannulationRepository;

    public DelaiannulationServiceImpl(DelaiannulationRepository delaiannulationRepository) {
        this.delaiannulationRepository = delaiannulationRepository;
    }

    @Override
    public Delaiannulation save(Delaiannulation delaiannulation) {
        log.debug("Request to save Delaiannulation : {}", delaiannulation);
        return delaiannulationRepository.save(delaiannulation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Delaiannulation> findAll(Pageable pageable) {
        log.debug("Request to get all Delaiannulations");
        return delaiannulationRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Delaiannulation> findOne(Long id) {
        log.debug("Request to get Delaiannulation : {}", id);
        return delaiannulationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Delaiannulation : {}", id);
        delaiannulationRepository.deleteById(id);
    }
}
