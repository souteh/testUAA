package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pdv;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pdv entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PdvRepository extends JpaRepository<Pdv, Long>, JpaSpecificationExecutor<Pdv> {
}
