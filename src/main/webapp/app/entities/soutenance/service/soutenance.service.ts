import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISoutenance, getSoutenanceIdentifier } from '../soutenance.model';
import { IEleve } from '../../eleve/eleve.model';
import { IAdminStatistics } from '../../enumerations/admin-statistics';

export type EntityResponseType = HttpResponse<ISoutenance>;
export type EntityArrayResponseType = HttpResponse<ISoutenance[]>;

@Injectable({ providedIn: 'root' })
export class SoutenanceService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/soutenances');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(soutenance: ISoutenance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soutenance);
    return this.http
      .post<ISoutenance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(soutenance: ISoutenance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soutenance);
    return this.http
      .put<ISoutenance>(`${this.resourceUrl}/${getSoutenanceIdentifier(soutenance) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(soutenance: ISoutenance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(soutenance);
    return this.http
      .patch<ISoutenance>(`${this.resourceUrl}/${getSoutenanceIdentifier(soutenance) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISoutenance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findAdminStatistics(req?: any): Observable<HttpResponse<IAdminStatistics>> {
    const options = createRequestOption(req);
    return this.http.get<IAdminStatistics>(this.resourceUrl + '/admin-statstics', { params: options, observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISoutenance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSoutenanceToCollectionIfMissing(
    soutenanceCollection: ISoutenance[],
    ...soutenancesToCheck: (ISoutenance | null | undefined)[]
  ): ISoutenance[] {
    const soutenances: ISoutenance[] = soutenancesToCheck.filter(isPresent);
    if (soutenances.length > 0) {
      const soutenanceCollectionIdentifiers = soutenanceCollection.map(soutenanceItem => getSoutenanceIdentifier(soutenanceItem)!);
      const soutenancesToAdd = soutenances.filter(soutenanceItem => {
        const soutenanceIdentifier = getSoutenanceIdentifier(soutenanceItem);
        if (soutenanceIdentifier == null || soutenanceCollectionIdentifiers.includes(soutenanceIdentifier)) {
          return false;
        }
        soutenanceCollectionIdentifiers.push(soutenanceIdentifier);
        return true;
      });
      return [...soutenancesToAdd, ...soutenanceCollection];
    }
    return soutenanceCollection;
  }

  protected convertDateFromClient(soutenance: ISoutenance): ISoutenance {
    return Object.assign({}, soutenance, {
      dateDuJour: soutenance.dateDuJour?.isValid() ? soutenance.dateDuJour.format(DATE_FORMAT) : undefined,
      dateAjout: soutenance.dateAjout?.isValid() ? soutenance.dateAjout.format(DATE_FORMAT) : undefined,
      dateModification: soutenance.dateModification?.isValid() ? soutenance.dateModification.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDuJour = res.body.dateDuJour ? dayjs(res.body.dateDuJour) : undefined;
      res.body.dateAjout = res.body.dateAjout ? dayjs(res.body.dateAjout) : undefined;
      res.body.dateModification = res.body.dateModification ? dayjs(res.body.dateModification) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((soutenance: ISoutenance) => {
        soutenance.dateDuJour = soutenance.dateDuJour ? dayjs(soutenance.dateDuJour) : undefined;
        soutenance.dateAjout = soutenance.dateAjout ? dayjs(soutenance.dateAjout) : undefined;
        soutenance.dateModification = soutenance.dateModification ? dayjs(soutenance.dateModification) : undefined;
      });
    }
    return res;
  }
}
