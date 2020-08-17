package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Delaiannulation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Delaiannulation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelaiannulationRepository extends JpaRepository<Delaiannulation, Long>, JpaSpecificationExecutor<Delaiannulation> {
}
