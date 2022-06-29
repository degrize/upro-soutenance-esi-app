import { IEleve } from '../../entities/eleve/eleve.model';
import { IEncadreur } from '../../entities/encadreur/encadreur.model';
import { IJury } from '../../entities/jury/jury.model';
import { IEntreprise } from '../../entities/entreprise/entreprise.model';

export class Account {
  constructor(
    public activated: boolean,
    public authorities: string[],
    public email: string,
    public firstName: string | null,
    public langKey: string,
    public lastName: string | null,
    public login: string,
    public eleve: IEleve,
    public encadreur: IEncadreur,
    public jury: IJury,
    public entreprise: IEntreprise,
    public imageUrl: string | null
  ) {}
}
