import dayjs from 'dayjs/esm';
import { IEncadreur } from 'app/entities/encadreur/encadreur.model';
import { IProjet } from 'app/entities/projet/projet.model';
import { ISpecialite } from 'app/entities/specialite/specialite.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { SituationMatrimoniale } from 'app/entities/enumerations/situation-matrimoniale.model';

export interface IEleve {
  id?: number;
  matricule?: string;
  nom?: string;
  prenoms?: string;
  sexe?: Sexe | null;
  situationMatrimoniale?: SituationMatrimoniale;
  dateParcoursDebut?: dayjs.Dayjs | null;
  dateParcoursFin?: dayjs.Dayjs | null;
  encadreur?: IEncadreur | null;
  projet?: IProjet | null;
  specialite?: ISpecialite | null;
}

export class Eleve implements IEleve {
  constructor(
    public id?: number,
    public matricule?: string,
    public nom?: string,
    public prenoms?: string,
    public sexe?: Sexe | null,
    public situationMatrimoniale?: SituationMatrimoniale,
    public dateParcoursDebut?: dayjs.Dayjs | null,
    public dateParcoursFin?: dayjs.Dayjs | null,
    public encadreur?: IEncadreur | null,
    public projet?: IProjet | null,
    public specialite?: ISpecialite | null
  ) {}
}

export function getEleveIdentifier(eleve: IEleve): number | undefined {
  return eleve.id;
}
