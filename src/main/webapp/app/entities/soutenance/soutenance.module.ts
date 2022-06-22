import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SoutenanceComponent } from './list/soutenance.component';
import { SoutenanceDetailComponent } from './detail/soutenance-detail.component';
import { SoutenanceUpdateComponent } from './update/soutenance-update.component';
import { SoutenanceDeleteDialogComponent } from './delete/soutenance-delete-dialog.component';
import { SoutenanceRoutingModule } from './route/soutenance-routing.module';
import { NgWizardModule } from 'ng-wizard';
import { NgbdSortableHeaderDirective } from './list/search-table/sortable.directive';
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [SharedModule, SoutenanceRoutingModule, NgWizardModule, NgSelectModule],
  declarations: [
    SoutenanceComponent,
    SoutenanceDetailComponent,
    SoutenanceUpdateComponent,
    SoutenanceDeleteDialogComponent,
    NgbdSortableHeaderDirective,
  ],
  entryComponents: [SoutenanceDeleteDialogComponent],
})
export class SoutenanceModule {}
