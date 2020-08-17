package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TypeterminalService;
import com.mycompany.myapp.domain.Typeterminal;
import com.mycompany.myapp.repository.TypeterminalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Typeterminal}.
 */
@Service
@Transactional
public class TypeterminalServiceImpl implements TypeterminalService {

    private final Logger log = LoggerFactory.getLogger(TypeterminalServiceImpl.class);

    private final TypeterminalRepository typeterminalRepository;

    public TypeterminalServiceImpl(TypeterminalRepository typeterminalRepository) {
        this.typeterminalRepository = typeterminalRepository;
    }

    @Override
    public Typeterminal save(Typeterminal typeterminal) {
        log.debug("Request to save Typeterminal : {}", typeterminal);
        return typeterminalRepository.save(typeterminal);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Typeterminal> findAll(Pageable pageable) {
        log.debug("Request to get all Typeterminals");
        return typeterminalRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Typeterminal> findOne(Long id) {
        log.debug("Request to get Typeterminal : {}", id);
        return typeterminalRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Typeterminal : {}", id);
        typeterminalRepository.deleteById(id);
    }
}
