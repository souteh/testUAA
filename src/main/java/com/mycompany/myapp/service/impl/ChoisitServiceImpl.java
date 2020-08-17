package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ChoisitService;
import com.mycompany.myapp.domain.Choisit;
import com.mycompany.myapp.repository.ChoisitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Choisit}.
 */
@Service
@Transactional
public class ChoisitServiceImpl implements ChoisitService {

    private final Logger log = LoggerFactory.getLogger(ChoisitServiceImpl.class);

    private final ChoisitRepository choisitRepository;

    public ChoisitServiceImpl(ChoisitRepository choisitRepository) {
        this.choisitRepository = choisitRepository;
    }

    @Override
    public Choisit save(Choisit choisit) {
        log.debug("Request to save Choisit : {}", choisit);
        return choisitRepository.save(choisit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Choisit> findAll(Pageable pageable) {
        log.debug("Request to get all Choisits");
        return choisitRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Choisit> findOne(Long id) {
        log.debug("Request to get Choisit : {}", id);
        return choisitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Choisit : {}", id);
        choisitRepository.deleteById(id);
    }
}
