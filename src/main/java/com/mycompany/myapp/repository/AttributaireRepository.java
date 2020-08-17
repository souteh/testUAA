package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Attributaire;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Attributaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributaireRepository extends JpaRepository<Attributaire, Long>, JpaSpecificationExecutor<Attributaire> {
}
