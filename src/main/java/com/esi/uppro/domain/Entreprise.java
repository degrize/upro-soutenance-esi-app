package com.esi.uppro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "entreprise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "code_e_ntreprise", nullable = false)
    private String codeENtreprise;

    @Column(name = "secteur_activite")
    private String secteurActivite;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "nom_directeur")
    private String nomDirecteur;

    @Column(name = "contact_directeur")
    private String contactDirecteur;

    @Column(name = "email_directeur")
    private String emailDirecteur;

    @Column(name = "nom_maitre_stage")
    private String nomMaitreStage;

    @Column(name = "contact_maitre_stage")
    private String contactMaitreStage;

    @Column(name = "email_maitre_stage")
    private String emailMaitreStage;

    @ManyToMany(mappedBy = "entreprises")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anneeAcademique", "entreprises" }, allowSetters = true)
    private Set<Projet> projets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entreprise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Entreprise nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeENtreprise() {
        return this.codeENtreprise;
    }

    public Entreprise codeENtreprise(String codeENtreprise) {
        this.setCodeENtreprise(codeENtreprise);
        return this;
    }

    public void setCodeENtreprise(String codeENtreprise) {
        this.codeENtreprise = codeENtreprise;
    }

    public String getSecteurActivite() {
        return this.secteurActivite;
    }

    public Entreprise secteurActivite(String secteurActivite) {
        this.setSecteurActivite(secteurActivite);
        return this;
    }

    public void setSecteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Entreprise adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNomDirecteur() {
        return this.nomDirecteur;
    }

    public Entreprise nomDirecteur(String nomDirecteur) {
        this.setNomDirecteur(nomDirecteur);
        return this;
    }

    public void setNomDirecteur(String nomDirecteur) {
        this.nomDirecteur = nomDirecteur;
    }

    public String getContactDirecteur() {
        return this.contactDirecteur;
    }

    public Entreprise contactDirecteur(String contactDirecteur) {
        this.setContactDirecteur(contactDirecteur);
        return this;
    }

    public void setContactDirecteur(String contactDirecteur) {
        this.contactDirecteur = contactDirecteur;
    }

    public String getEmailDirecteur() {
        return this.emailDirecteur;
    }

    public Entreprise emailDirecteur(String emailDirecteur) {
        this.setEmailDirecteur(emailDirecteur);
        return this;
    }

    public void setEmailDirecteur(String emailDirecteur) {
        this.emailDirecteur = emailDirecteur;
    }

    public String getNomMaitreStage() {
        return this.nomMaitreStage;
    }

    public Entreprise nomMaitreStage(String nomMaitreStage) {
        this.setNomMaitreStage(nomMaitreStage);
        return this;
    }

    public void setNomMaitreStage(String nomMaitreStage) {
        this.nomMaitreStage = nomMaitreStage;
    }

    public String getContactMaitreStage() {
        return this.contactMaitreStage;
    }

    public Entreprise contactMaitreStage(String contactMaitreStage) {
        this.setContactMaitreStage(contactMaitreStage);
        return this;
    }

    public void setContactMaitreStage(String contactMaitreStage) {
        this.contactMaitreStage = contactMaitreStage;
    }

    public String getEmailMaitreStage() {
        return this.emailMaitreStage;
    }

    public Entreprise emailMaitreStage(String emailMaitreStage) {
        this.setEmailMaitreStage(emailMaitreStage);
        return this;
    }

    public void setEmailMaitreStage(String emailMaitreStage) {
        this.emailMaitreStage = emailMaitreStage;
    }

    public Set<Projet> getProjets() {
        return this.projets;
    }

    public void setProjets(Set<Projet> projets) {
        if (this.projets != null) {
            this.projets.forEach(i -> i.removeEntreprise(this));
        }
        if (projets != null) {
            projets.forEach(i -> i.addEntreprise(this));
        }
        this.projets = projets;
    }

    public Entreprise projets(Set<Projet> projets) {
        this.setProjets(projets);
        return this;
    }

    public Entreprise addProjet(Projet projet) {
        this.projets.add(projet);
        projet.getEntreprises().add(this);
        return this;
    }

    public Entreprise removeProjet(Projet projet) {
        this.projets.remove(projet);
        projet.getEntreprises().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entreprise)) {
            return false;
        }
        return id != null && id.equals(((Entreprise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entreprise{" +
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
