package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Alertemail;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Alertemail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlertemailRepository extends JpaRepository<Alertemail, Long>, JpaSpecificationExecutor<Alertemail> {
}
