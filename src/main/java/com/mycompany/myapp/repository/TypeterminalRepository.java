package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Typeterminal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Typeterminal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeterminalRepository extends JpaRepository<Typeterminal, Long>, JpaSpecificationExecutor<Typeterminal> {
}
