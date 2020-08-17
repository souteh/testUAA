package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AttributaireService;
import com.mycompany.myapp.domain.Attributaire;
import com.mycompany.myapp.repository.AttributaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Attributaire}.
 */
@Service
@Transactional
public class AttributaireServiceImpl implements AttributaireService {

    private final Logger log = LoggerFactory.getLogger(AttributaireServiceImpl.class);

    private final AttributaireRepository attributaireRepository;

    public AttributaireServiceImpl(AttributaireRepository attributaireRepository) {
        this.attributaireRepository = attributaireRepository;
    }

    @Override
    public Attributaire save(Attributaire attributaire) {
        log.debug("Request to save Attributaire : {}", attributaire);
        return attributaireRepository.save(attributaire);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Attributaire> findAll(Pageable pageable) {
        log.debug("Request to get all Attributaires");
        return attributaireRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Attributaire> findOne(Long id) {
        log.debug("Request to get Attributaire : {}", id);
        return attributaireRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attributaire : {}", id);
        attributaireRepository.deleteById(id);
    }
}
