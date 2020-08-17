package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.TypepdvService;
import com.mycompany.myapp.domain.Typepdv;
import com.mycompany.myapp.repository.TypepdvRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Typepdv}.
 */
@Service
@Transactional
public class TypepdvServiceImpl implements TypepdvService {

    private final Logger log = LoggerFactory.getLogger(TypepdvServiceImpl.class);

    private final TypepdvRepository typepdvRepository;

    public TypepdvServiceImpl(TypepdvRepository typepdvRepository) {
        this.typepdvRepository = typepdvRepository;
    }

    @Override
    public Typepdv save(Typepdv typepdv) {
        log.debug("Request to save Typepdv : {}", typepdv);
        return typepdvRepository.save(typepdv);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Typepdv> findAll(Pageable pageable) {
        log.debug("Request to get all Typepdvs");
        return typepdvRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Typepdv> findOne(Long id) {
        log.debug("Request to get Typepdv : {}", id);
        return typepdvRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Typepdv : {}", id);
        typepdvRepository.deleteById(id);
    }
}
