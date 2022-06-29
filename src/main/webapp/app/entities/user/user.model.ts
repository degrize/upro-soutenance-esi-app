import { IEleve } from '../eleve/eleve.model';
import { IEncadreur } from '../encadreur/encadreur.model';
import { IJury } from '../jury/jury.model';
import { IEntreprise } from '../entreprise/entreprise.model';

export interface IUser {
  id?: number;
  login?: string;
  eleve?: IEleve;
  encadreur?: IEncadreur;
  jury?: IJury;
  entreprise?: IEntreprise;
}

export class User implements IUser {
  constructor(
    public id: number,
    public login: string,
    public eleve: IEleve,
    public encadreur: IEncadreur,
    public jury: IJury,
    public entreprise: IEntreprise
  ) {}
}

export function getUserIdentifier(user: IUser): number | undefined {
  return user.id;
}
