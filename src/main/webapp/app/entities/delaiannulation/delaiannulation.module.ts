import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { DelaiannulationComponent } from './delaiannulation.component';
import { DelaiannulationDetailComponent } from './delaiannulation-detail.component';
import { DelaiannulationUpdateComponent } from './delaiannulation-update.component';
import { DelaiannulationDeleteDialogComponent } from './delaiannulation-delete-dialog.component';
import { delaiannulationRoute } from './delaiannulation.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(delaiannulationRoute)],
  declarations: [
    DelaiannulationComponent,
    DelaiannulationDetailComponent,
    DelaiannulationUpdateComponent,
    DelaiannulationDeleteDialogComponent,
  ],
  entryComponents: [DelaiannulationDeleteDialogComponent],
})
export class TestUaaDelaiannulationModule {}
