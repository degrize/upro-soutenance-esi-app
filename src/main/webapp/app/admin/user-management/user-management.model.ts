import { IEleve } from '../../entities/eleve/eleve.model';
import { IEncadreur } from '../../entities/encadreur/encadreur.model';
import { IJury } from '../../entities/jury/jury.model';
import { IEntreprise } from '../../entities/entreprise/entreprise.model';

export interface IUser {
  id?: number;
  login?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  eleve?: IEleve;
  encadreur?: IEncadreur;
  jury?: IJury;
  entreprise?: IEntreprise;
}

export class User implements IUser {
  constructor(
    public id?: number,
    public login?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public eleve?: IEleve,
    public encadreur?: IEncadreur,
    public jury?: IJury,
    public entreprise?: IEntreprise
  ) {}
}
