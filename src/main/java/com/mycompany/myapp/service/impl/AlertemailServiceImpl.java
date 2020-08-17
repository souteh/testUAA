package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AlertemailService;
import com.mycompany.myapp.domain.Alertemail;
import com.mycompany.myapp.repository.AlertemailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Alertemail}.
 */
@Service
@Transactional
public class AlertemailServiceImpl implements AlertemailService {

    private final Logger log = LoggerFactory.getLogger(AlertemailServiceImpl.class);

    private final AlertemailRepository alertemailRepository;

    public AlertemailServiceImpl(AlertemailRepository alertemailRepository) {
        this.alertemailRepository = alertemailRepository;
    }

    @Override
    public Alertemail save(Alertemail alertemail) {
        log.debug("Request to save Alertemail : {}", alertemail);
        return alertemailRepository.save(alertemail);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Alertemail> findAll(Pageable pageable) {
        log.debug("Request to get all Alertemails");
        return alertemailRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Alertemail> findOne(Long id) {
        log.debug("Request to get Alertemail : {}", id);
        return alertemailRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alertemail : {}", id);
        alertemailRepository.deleteById(id);
    }
}
