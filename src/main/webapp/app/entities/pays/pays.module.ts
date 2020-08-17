import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { PaysComponent } from './pays.component';
import { PaysDetailComponent } from './pays-detail.component';
import { PaysUpdateComponent } from './pays-update.component';
import { PaysDeleteDialogComponent } from './pays-delete-dialog.component';
import { paysRoute } from './pays.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(paysRoute)],
  declarations: [PaysComponent, PaysDetailComponent, PaysUpdateComponent, PaysDeleteDialogComponent],
  entryComponents: [PaysDeleteDialogComponent],
})
export class TestUaaPaysModule {}
