package com.esi.uppro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.esi.uppro.domain.Entreprise} entity.
 */
public class EntrepriseDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String codeENtreprise;

    private String secteurActivite;

    private String adresse;

    private String nomDirecteur;

    private String contactDirecteur;

    private String emailDirecteur;

    private String nomMaitreStage;

    private String contactMaitreStage;

    private String emailMaitreStage;

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

    public String getCodeENtreprise() {
        return codeENtreprise;
    }

    public void setCodeENtreprise(String codeENtreprise) {
        this.codeENtreprise = codeENtreprise;
    }

    public String getSecteurActivite() {
        return secteurActivite;
    }

    public void setSecteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public String getEmailDirecteur() {
        return emailDirecteur;
    }

    public void setEmailDirecteur(String emailDirecteur) {
        this.emailDirecteur = emailDirecteur;
    }

    public String getNomMaitreStage() {
        return nomMaitreStage;
    }

    public void setNomMaitreStage(String nomMaitreStage) {
        this.nomMaitreStage = nomMaitreStage;
    }

    public String getContactMaitreStage() {
        return contactMaitreStage;
    }

    public void setContactMaitreStage(String contactMaitreStage) {
        this.contactMaitreStage = contactMaitreStage;
    }

    public String getEmailMaitreStage() {
        return emailMaitreStage;
    }

    public void setEmailMaitreStage(String emailMaitreStage) {
        this.emailMaitreStage = emailMaitreStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntrepriseDTO)) {
            return false;
        }

        EntrepriseDTO entrepriseDTO = (EntrepriseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entrepriseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntrepriseDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", codeENtreprise='" + getCodeENtreprise() + "'" +
            ", secteurActivite='" + getSecteurActivite() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", nomDirecteur='" + getNomDirecteur() + "'" +
            ", contactDirecteur='" + getContactDirecteur() + "'" +
            ", emailDirecteur='" + getEmailDirecteur() + "'" +
            ", nomMaitreStage='" + getNomMaitreStage() + "'" +
            ", contactMaitreStage='" + getContactMaitreStage() + "'" +
            ", emailMaitreStage='" + getEmailMaitreStage() + "'" +
            "}";
    }
}
