package com.esi.uppro.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Genie.
 */
@Entity
@Table(name = "genie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Genie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "nom_directeur")
    private String nomDirecteur;

    @Column(name = "contact_directeur")
    private String contactDirecteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Genie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Genie nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomDirecteur() {
        return this.nomDirecteur;
    }

    public Genie nomDirecteur(String nomDirecteur) {
        this.setNomDirecteur(nomDirecteur);
        return this;
    }

    public void setNomDirecteur(String nomDirecteur) {
        this.nomDirecteur = nomDirecteur;
    }

    public String getContactDirecteur() {
        return this.contactDirecteur;
    }

    public Genie contactDirecteur(String contactDirecteur) {
        this.setContactDirecteur(contactDirecteur);
        return this;
    }

    public void setContactDirecteur(String contactDirecteur) {
        this.contactDirecteur = contactDirecteur;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Genie)) {
            return false;
        }
        return id != null && id.equals(((Genie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Genie{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomDirecteur='" + getNomDirecteur() + "'" +
            ", contactDirecteur='" + getContactDirecteur() + "'" +
            "}";
    }
}
