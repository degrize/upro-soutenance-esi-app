package com.esi.uppro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Projet.
 */
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "theme", nullable = false)
    private String theme;

    @Lob
    @Column(name = "rapport")
    private byte[] rapport;

    @Column(name = "rapport_content_type")
    private String rapportContentType;

    @Column(name = "cout")
    private Integer cout;

    @Column(name = "date_ajout")
    private LocalDate dateAjout;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @Column(name = "valide")
    private boolean valide;

    @Column(name = "date_soutenance")
    private LocalDate dateSoutenance;

    @ManyToOne
    private AnneeAcademique anneeAcademique;

    @ManyToMany
    @JoinTable(
        name = "rel_projet__entreprise",
        joinColumns = @JoinColumn(name = "projet_id"),
        inverseJoinColumns = @JoinColumn(name = "entreprise_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "projets" }, allowSetters = true)
    private Set<Entreprise> entreprises = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return this.theme;
    }

    public Projet theme(String theme) {
        this.setTheme(theme);
        return this;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public byte[] getRapport() {
        return this.rapport;
    }

    public Projet rapport(byte[] rapport) {
        this.setRapport(rapport);
        return this;
    }

    public void setRapport(byte[] rapport) {
        this.rapport = rapport;
    }

    public String getRapportContentType() {
        return this.rapportContentType;
    }

    public Projet rapportContentType(String rapportContentType) {
        this.rapportContentType = rapportContentType;
        return this;
    }

    public void setRapportContentType(String rapportContentType) {
        this.rapportContentType = rapportContentType;
    }

    public Integer getCout() {
        return this.cout;
    }

    public Projet cout(Integer cout) {
        this.setCout(cout);
        return this;
    }

    public void setCout(Integer cout) {
        this.cout = cout;
    }

    public LocalDate getDateAjout() {
        return this.dateAjout;
    }

    public Projet dateAjout(LocalDate dateAjout) {
        this.setDateAjout(dateAjout);
        return this;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public LocalDate getDateModification() {
        return this.dateModification;
    }

    public Projet dateModification(LocalDate dateModification) {
        this.setDateModification(dateModification);
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public LocalDate getDateSoutenance() {
        return dateSoutenance;
    }

    public void setDateSoutenance(LocalDate dateSoutenance) {
        this.dateSoutenance = dateSoutenance;
    }

    public AnneeAcademique getAnneeAcademique() {
        return this.anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Projet anneeAcademique(AnneeAcademique anneeAcademique) {
        this.setAnneeAcademique(anneeAcademique);
        return this;
    }

    public Set<Entreprise> getEntreprises() {
        return this.entreprises;
    }

    public void setEntreprises(Set<Entreprise> entreprises) {
        this.entreprises = entreprises;
    }

    public Projet entreprises(Set<Entreprise> entreprises) {
        this.setEntreprises(entreprises);
        return this;
    }

    public Projet addEntreprise(Entreprise entreprise) {
        this.entreprises.add(entreprise);
        entreprise.getProjets().add(this);
        return this;
    }

    public Projet removeEntreprise(Entreprise entreprise) {
        this.entreprises.remove(entreprise);
        entreprise.getProjets().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projet)) {
            return false;
        }
        return id != null && id.equals(((Projet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", theme='" + getTheme() + "'" +
            ", rapport='" + getRapport() + "'" +
            ", rapportContentType='" + getRapportContentType() + "'" +
            ", cout=" + getCout() +
            ", dateAjout='" + getDateAjout() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            ", valide='" + isValide() + "'" +
            ", dateSoutenance='" + getDateSoutenance() + "'" +
            "}";
    }
}
