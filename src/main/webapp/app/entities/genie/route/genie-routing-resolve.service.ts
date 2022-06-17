import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGenie, Genie } from '../genie.model';
import { GenieService } from '../service/genie.service';

@Injectable({ providedIn: 'root' })
export class GenieRoutingResolveService implements Resolve<IGenie> {
  constructor(protected service: GenieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGenie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((genie: HttpResponse<Genie>) => {
          if (genie.body) {
            return of(genie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Genie());
  }
}
