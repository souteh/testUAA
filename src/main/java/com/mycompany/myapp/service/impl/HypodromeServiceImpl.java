package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.HypodromeService;
import com.mycompany.myapp.domain.Hypodrome;
import com.mycompany.myapp.repository.HypodromeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Hypodrome}.
 */
@Service
@Transactional
public class HypodromeServiceImpl implements HypodromeService {

    private final Logger log = LoggerFactory.getLogger(HypodromeServiceImpl.class);

    private final HypodromeRepository hypodromeRepository;

    public HypodromeServiceImpl(HypodromeRepository hypodromeRepository) {
        this.hypodromeRepository = hypodromeRepository;
    }

    @Override
    public Hypodrome save(Hypodrome hypodrome) {
        log.debug("Request to save Hypodrome : {}", hypodrome);
        return hypodromeRepository.save(hypodrome);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Hypodrome> findAll(Pageable pageable) {
        log.debug("Request to get all Hypodromes");
        return hypodromeRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Hypodrome> findOne(Long id) {
        log.debug("Request to get Hypodrome : {}", id);
        return hypodromeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hypodrome : {}", id);
        hypodromeRepository.deleteById(id);
    }
}
