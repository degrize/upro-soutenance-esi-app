import dayjs from 'dayjs/esm';
import { IAnneeAcademique } from 'app/entities/annee-academique/annee-academique.model';
import { IEntreprise } from 'app/entities/entreprise/entreprise.model';

export interface IProjet {
  id?: number;
  theme?: string;
  rapportContentType?: string | null;
  rapport?: string | null;
  cout?: number | null;
  dateAjout?: dayjs.Dayjs | null;
  dateModification?: dayjs.Dayjs | null;
  anneeAcademique?: IAnneeAcademique | null;
  entreprises?: IEntreprise[] | null;
}

export class Projet implements IProjet {
  constructor(
    public id?: number,
    public theme?: string,
    public rapportContentType?: string | null,
    public rapport?: string | null,
    public cout?: number | null,
    public dateAjout?: dayjs.Dayjs | null,
    public dateModification?: dayjs.Dayjs | null,
    public anneeAcademique?: IAnneeAcademique | null,
    public entreprises?: IEntreprise[] | null
  ) {}
}

export function getProjetIdentifier(projet: IProjet): number | undefined {
  return projet.id;
}
