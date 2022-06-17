package com.esi.uppro.repository;

import com.esi.uppro.domain.Genie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Genie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenieRepository extends JpaRepository<Genie, Long> {}
