package com.esi.uppro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esi.uppro.domain.AnneeAcademique} entity.
 */
public class AnneeAcademiqueDTO implements Serializable {

    private Long id;

    @NotNull
    private String anneeScolaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(String anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnneeAcademiqueDTO)) {
            return false;
        }

        AnneeAcademiqueDTO anneeAcademiqueDTO = (AnneeAcademiqueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anneeAcademiqueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnneeAcademiqueDTO{" +
            "id=" + getId() +
            ", anneeScolaire='" + getAnneeScolaire() + "'" +
            "}";
    }
}
