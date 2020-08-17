package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Paiementlots;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Paiementlots entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaiementlotsRepository extends JpaRepository<Paiementlots, Long>, JpaSpecificationExecutor<Paiementlots> {
}
