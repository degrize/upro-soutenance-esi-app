package com.esi.uppro.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String ELEVE = "ROLE_ELEVE";

    public static final String DIRECTEUR_ETUDE = "ROLE_DIRECTEUR_ETUDE";

    public static final String PROF_ENCADREUR = "ROLE_PROF_ENCADREUR";

    public static final String ENTREPRISE = "ROLE_ENTREPRISE";

    public static final String INSPECTEUR = "ROLE_INSPECTEUR";

    public static final String DIRECTEUR_ESI = "ROLE_DIRECTEUR_ESI";

    public static final String AGENT_SCOLARITE = "ROLE_AGENT_SCOLARITE";

    private AuthoritiesConstants() {}
}
