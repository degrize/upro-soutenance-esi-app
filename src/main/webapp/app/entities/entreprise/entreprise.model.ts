import { IProjet } from 'app/entities/projet/projet.model';

export interface IEntreprise {
  id?: number;
  nom?: string;
  codeENtreprise?: string;
  secteurActivite?: string | null;
  adresse?: string | null;
  nomDirecteur?: string | null;
  contactDirecteur?: string | null;
  emailDirecteur?: string | null;
  nomMaitreStage?: string | null;
  contactMaitreStage?: string | null;
  emailMaitreStage?: string | null;
  projets?: IProjet[] | null;
}

export class Entreprise implements IEntreprise {
  constructor(
    public id?: number,
    public nom?: string,
    public codeENtreprise?: string,
    public secteurActivite?: string | null,
    public adresse?: string | null,
    public nomDirecteur?: string | null,
    public contactDirecteur?: string | null,
    public emailDirecteur?: string | null,
    public nomMaitreStage?: string | null,
    public contactMaitreStage?: string | null,
    public emailMaitreStage?: string | null,
    public projets?: IProjet[] | null
  ) {}
}

export function getEntrepriseIdentifier(entreprise: IEntreprise): number | undefined {
  return entreprise.id;
}
