package com.esi.uppro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esi.uppro.domain.Genie} entity.
 */
public class GenieDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String nomDirecteur;

    private String contactDirecteur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomDirecteur() {
        return nomDirecteur;
    }

    public void setNomDirecteur(String nomDirecteur) {
        this.nomDirecteur = nomDirecteur;
    }

    public String getContactDirecteur() {
        return contactDirecteur;
    }

    public void setContactDirecteur(String contactDirecteur) {
        this.contactDirecteur = contactDirecteur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenieDTO)) {
            return false;
        }

        GenieDTO genieDTO = (GenieDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, genieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenieDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomDirecteur='" + getNomDirecteur() + "'" +
            ", contactDirecteur='" + getContactDirecteur() + "'" +
            "}";
    }
}
