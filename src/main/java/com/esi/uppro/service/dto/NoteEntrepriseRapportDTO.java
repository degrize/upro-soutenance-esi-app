package com.esi.uppro.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esi.uppro.domain.NoteEntrepriseRapport} entity.
 */
public class NoteEntrepriseRapportDTO implements Serializable {

    private Long id;

    @NotNull
    private Double note;

    private String observation;

    private LocalDate dateAjout;

    private LocalDate dateModification;

    private EntrepriseDTO entreprise;

    private ProjetDTO projet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public EntrepriseDTO getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(EntrepriseDTO entreprise) {
        this.entreprise = entreprise;
    }

    public ProjetDTO getProjet() {
        return projet;
    }

    public void setProjet(ProjetDTO projet) {
        this.projet = projet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoteEntrepriseRapportDTO)) {
            return false;
        }

        NoteEntrepriseRapportDTO noteEntrepriseRapportDTO = (NoteEntrepriseRapportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, noteEntrepriseRapportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoteEntrepriseRapportDTO{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", observation='" + getObservation() + "'" +
            ", dateAjout='" + getDateAjout() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            ", entreprise=" + getEntreprise() +
            ", projet=" + getProjet() +
            "}";
    }
}
