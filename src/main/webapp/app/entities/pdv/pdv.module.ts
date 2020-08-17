import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { PdvComponent } from './pdv.component';
import { PdvDetailComponent } from './pdv-detail.component';
import { PdvUpdateComponent } from './pdv-update.component';
import { PdvDeleteDialogComponent } from './pdv-delete-dialog.component';
import { pdvRoute } from './pdv.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(pdvRoute)],
  declarations: [PdvComponent, PdvDetailComponent, PdvUpdateComponent, PdvDeleteDialogComponent],
  entryComponents: [PdvDeleteDialogComponent],
})
export class TestUaaPdvModule {}
