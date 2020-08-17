import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { PaiementlotsComponent } from './paiementlots.component';
import { PaiementlotsDetailComponent } from './paiementlots-detail.component';
import { PaiementlotsUpdateComponent } from './paiementlots-update.component';
import { PaiementlotsDeleteDialogComponent } from './paiementlots-delete-dialog.component';
import { paiementlotsRoute } from './paiementlots.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(paiementlotsRoute)],
  declarations: [PaiementlotsComponent, PaiementlotsDetailComponent, PaiementlotsUpdateComponent, PaiementlotsDeleteDialogComponent],
  entryComponents: [PaiementlotsDeleteDialogComponent],
})
export class TestUaaPaiementlotsModule {}
