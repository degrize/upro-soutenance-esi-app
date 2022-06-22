import { Mention } from '../enumerations/mention.model';

export interface Eleve {
  id: number;
  matricule: string;
  nomPrenoms: string;
  specialite: string;
  theme: string;
  anneeAcademique: string;
}
