import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { GenieComponent } from '../list/genie.component';
import { GenieDetailComponent } from '../detail/genie-detail.component';
import { GenieUpdateComponent } from '../update/genie-update.component';
import { GenieRoutingResolveService } from './genie-routing-resolve.service';

const genieRoute: Routes = [
  {
    path: '',
    component: GenieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GenieDetailComponent,
    resolve: {
      genie: GenieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GenieUpdateComponent,
    resolve: {
      genie: GenieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GenieUpdateComponent,
    resolve: {
      genie: GenieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(genieRoute)],
  exports: [RouterModule],
})
export class GenieRoutingModule {}
