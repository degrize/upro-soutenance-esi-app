package com.esi.uppro.repository;

import com.esi.uppro.domain.AnneeAcademique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnneeAcademique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnneeAcademiqueRepository extends JpaRepository<AnneeAcademique, Long> {}
