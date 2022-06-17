package com.esi.uppro.repository;

import com.esi.uppro.domain.Encadreur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Encadreur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncadreurRepository extends JpaRepository<Encadreur, Long> {}
