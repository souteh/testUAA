package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SmtpService;
import com.mycompany.myapp.domain.Smtp;
import com.mycompany.myapp.repository.SmtpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Smtp}.
 */
@Service
@Transactional
public class SmtpServiceImpl implements SmtpService {

    private final Logger log = LoggerFactory.getLogger(SmtpServiceImpl.class);

    private final SmtpRepository smtpRepository;

    public SmtpServiceImpl(SmtpRepository smtpRepository) {
        this.smtpRepository = smtpRepository;
    }

    @Override
    public Smtp save(Smtp smtp) {
        log.debug("Request to save Smtp : {}", smtp);
        return smtpRepository.save(smtp);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Smtp> findAll(Pageable pageable) {
        log.debug("Request to get all Smtps");
        return smtpRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Smtp> findOne(Long id) {
        log.debug("Request to get Smtp : {}", id);
        return smtpRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Smtp : {}", id);
        smtpRepository.deleteById(id);
    }
}
