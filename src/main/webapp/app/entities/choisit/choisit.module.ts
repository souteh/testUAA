import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { ChoisitComponent } from './choisit.component';
import { ChoisitDetailComponent } from './choisit-detail.component';
import { ChoisitUpdateComponent } from './choisit-update.component';
import { ChoisitDeleteDialogComponent } from './choisit-delete-dialog.component';
import { choisitRoute } from './choisit.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(choisitRoute)],
  declarations: [ChoisitComponent, ChoisitDetailComponent, ChoisitUpdateComponent, ChoisitDeleteDialogComponent],
  entryComponents: [ChoisitDeleteDialogComponent],
})
export class TestUaaChoisitModule {}
