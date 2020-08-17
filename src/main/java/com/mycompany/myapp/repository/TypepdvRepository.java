package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Typepdv;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Typepdv entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypepdvRepository extends JpaRepository<Typepdv, Long>, JpaSpecificationExecutor<Typepdv> {
}
