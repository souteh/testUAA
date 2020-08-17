package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.VersionService;
import com.mycompany.myapp.domain.Version;
import com.mycompany.myapp.repository.VersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Version}.
 */
@Service
@Transactional
public class VersionServiceImpl implements VersionService {

    private final Logger log = LoggerFactory.getLogger(VersionServiceImpl.class);

    private final VersionRepository versionRepository;

    public VersionServiceImpl(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Override
    public Version save(Version version) {
        log.debug("Request to save Version : {}", version);
        return versionRepository.save(version);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Version> findAll(Pageable pageable) {
        log.debug("Request to get all Versions");
        return versionRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Version> findOne(Long id) {
        log.debug("Request to get Version : {}", id);
        return versionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Version : {}", id);
        versionRepository.deleteById(id);
    }
}
