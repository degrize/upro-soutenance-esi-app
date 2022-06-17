package com.esi.uppro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esi.uppro.domain.Encadreur} entity.
 */
public class EncadreurDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenoms;

    @NotNull
    private String contact;

    private String email;

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

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncadreurDTO)) {
            return false;
        }

        EncadreurDTO encadreurDTO = (EncadreurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, encadreurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EncadreurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenoms='" + getPrenoms() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
