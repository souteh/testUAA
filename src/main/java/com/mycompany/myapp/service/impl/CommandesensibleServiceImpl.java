package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CommandesensibleService;
import com.mycompany.myapp.domain.Commandesensible;
import com.mycompany.myapp.repository.CommandesensibleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Commandesensible}.
 */
@Service
@Transactional
public class CommandesensibleServiceImpl implements CommandesensibleService {

    private final Logger log = LoggerFactory.getLogger(CommandesensibleServiceImpl.class);

    private final CommandesensibleRepository commandesensibleRepository;

    public CommandesensibleServiceImpl(CommandesensibleRepository commandesensibleRepository) {
        this.commandesensibleRepository = commandesensibleRepository;
    }

    @Override
    public Commandesensible save(Commandesensible commandesensible) {
        log.debug("Request to save Commandesensible : {}", commandesensible);
        return commandesensibleRepository.save(commandesensible);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Commandesensible> findAll(Pageable pageable) {
        log.debug("Request to get all Commandesensibles");
        return commandesensibleRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Commandesensible> findOne(Long id) {
        log.debug("Request to get Commandesensible : {}", id);
        return commandesensibleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commandesensible : {}", id);
        commandesensibleRepository.deleteById(id);
    }
}
