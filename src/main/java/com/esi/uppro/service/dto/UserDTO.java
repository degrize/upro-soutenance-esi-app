package com.esi.uppro.service.dto;

import com.esi.uppro.domain.*;
import javax.persistence.ManyToOne;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO {

    private Long id;

    private String login;

    private Eleve eleve;

    private Encadreur encadreur;

    private Jury jury;

    private Entreprise entreprise;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
        this.eleve = user.getEleve();
        this.encadreur = user.getEncadreur();
        this.entreprise = user.getEntreprise();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Encadreur getEncadreur() {
        return encadreur;
    }

    public void setEncadreur(Encadreur encadreur) {
        this.encadreur = encadreur;
    }

    public Jury getJury() {
        return jury;
    }

    public void setJury(Jury jury) {
        this.jury = jury;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            ", eleve=" + eleve +
            ", encadreur=" + encadreur +
            ", jury=" + jury +
            ", entreprise=" + entreprise +
            "}";
    }
}
