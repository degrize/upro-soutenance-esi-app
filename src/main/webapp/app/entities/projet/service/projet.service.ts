import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProjet, getProjetIdentifier } from '../projet.model';

export type EntityResponseType = HttpResponse<IProjet>;
export type EntityArrayResponseType = HttpResponse<IProjet[]>;

@Injectable({ providedIn: 'root' })
export class ProjetService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/projets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(projet: IProjet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projet);
    return this.http
      .post<IProjet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(projet: IProjet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projet);
    return this.http
      .put<IProjet>(`${this.resourceUrl}/${getProjetIdentifier(projet) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(projet: IProjet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(projet);
    return this.http
      .patch<IProjet>(`${this.resourceUrl}/${getProjetIdentifier(projet) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProjet>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProjet[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProjetToCollectionIfMissing(projetCollection: IProjet[], ...projetsToCheck: (IProjet | null | undefined)[]): IProjet[] {
    const projets: IProjet[] = projetsToCheck.filter(isPresent);
    if (projets.length > 0) {
      const projetCollectionIdentifiers = projetCollection.map(projetItem => getProjetIdentifier(projetItem)!);
      const projetsToAdd = projets.filter(projetItem => {
        const projetIdentifier = getProjetIdentifier(projetItem);
        if (projetIdentifier == null || projetCollectionIdentifiers.includes(projetIdentifier)) {
          return false;
        }
        projetCollectionIdentifiers.push(projetIdentifier);
        return true;
      });
      return [...projetsToAdd, ...projetCollection];
    }
    return projetCollection;
  }

  protected convertDateFromClient(projet: IProjet): IProjet {
    return Object.assign({}, projet, {
      dateAjout: projet.dateAjout?.isValid() ? projet.dateAjout.format(DATE_FORMAT) : undefined,
      dateModification: projet.dateModification?.isValid() ? projet.dateModification.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAjout = res.body.dateAjout ? dayjs(res.body.dateAjout) : undefined;
      res.body.dateModification = res.body.dateModification ? dayjs(res.body.dateModification) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((projet: IProjet) => {
        projet.dateAjout = projet.dateAjout ? dayjs(projet.dateAjout) : undefined;
        projet.dateModification = projet.dateModification ? dayjs(projet.dateModification) : undefined;
      });
    }
    return res;
  }
}
