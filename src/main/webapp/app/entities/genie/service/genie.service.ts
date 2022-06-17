import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGenie, getGenieIdentifier } from '../genie.model';

export type EntityResponseType = HttpResponse<IGenie>;
export type EntityArrayResponseType = HttpResponse<IGenie[]>;

@Injectable({ providedIn: 'root' })
export class GenieService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/genies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(genie: IGenie): Observable<EntityResponseType> {
    return this.http.post<IGenie>(this.resourceUrl, genie, { observe: 'response' });
  }

  update(genie: IGenie): Observable<EntityResponseType> {
    return this.http.put<IGenie>(`${this.resourceUrl}/${getGenieIdentifier(genie) as number}`, genie, { observe: 'response' });
  }

  partialUpdate(genie: IGenie): Observable<EntityResponseType> {
    return this.http.patch<IGenie>(`${this.resourceUrl}/${getGenieIdentifier(genie) as number}`, genie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGenie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGenie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addGenieToCollectionIfMissing(genieCollection: IGenie[], ...geniesToCheck: (IGenie | null | undefined)[]): IGenie[] {
    const genies: IGenie[] = geniesToCheck.filter(isPresent);
    if (genies.length > 0) {
      const genieCollectionIdentifiers = genieCollection.map(genieItem => getGenieIdentifier(genieItem)!);
      const geniesToAdd = genies.filter(genieItem => {
        const genieIdentifier = getGenieIdentifier(genieItem);
        if (genieIdentifier == null || genieCollectionIdentifiers.includes(genieIdentifier)) {
          return false;
        }
        genieCollectionIdentifiers.push(genieIdentifier);
        return true;
      });
      return [...geniesToAdd, ...genieCollection];
    }
    return genieCollection;
  }
}
