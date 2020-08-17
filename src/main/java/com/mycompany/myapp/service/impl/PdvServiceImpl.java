package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PdvService;
import com.mycompany.myapp.domain.Pdv;
import com.mycompany.myapp.repository.PdvRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pdv}.
 */
@Service
@Transactional
public class PdvServiceImpl implements PdvService {

    private final Logger log = LoggerFactory.getLogger(PdvServiceImpl.class);

    private final PdvRepository pdvRepository;

    public PdvServiceImpl(PdvRepository pdvRepository) {
        this.pdvRepository = pdvRepository;
    }

    @Override
    public Pdv save(Pdv pdv) {
        log.debug("Request to save Pdv : {}", pdv);
        return pdvRepository.save(pdv);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pdv> findAll(Pageable pageable) {
        log.debug("Request to get all Pdvs");
        return pdvRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Pdv> findOne(Long id) {
        log.debug("Request to get Pdv : {}", id);
        return pdvRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pdv : {}", id);
        pdvRepository.deleteById(id);
    }
}
