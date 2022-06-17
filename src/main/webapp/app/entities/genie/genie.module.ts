import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { GenieComponent } from './list/genie.component';
import { GenieDetailComponent } from './detail/genie-detail.component';
import { GenieUpdateComponent } from './update/genie-update.component';
import { GenieDeleteDialogComponent } from './delete/genie-delete-dialog.component';
import { GenieRoutingModule } from './route/genie-routing.module';

@NgModule({
  imports: [SharedModule, GenieRoutingModule],
  declarations: [GenieComponent, GenieDetailComponent, GenieUpdateComponent, GenieDeleteDialogComponent],
  entryComponents: [GenieDeleteDialogComponent],
})
export class GenieModule {}
