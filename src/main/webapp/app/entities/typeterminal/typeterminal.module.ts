import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { TypeterminalComponent } from './typeterminal.component';
import { TypeterminalDetailComponent } from './typeterminal-detail.component';
import { TypeterminalUpdateComponent } from './typeterminal-update.component';
import { TypeterminalDeleteDialogComponent } from './typeterminal-delete-dialog.component';
import { typeterminalRoute } from './typeterminal.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(typeterminalRoute)],
  declarations: [TypeterminalComponent, TypeterminalDetailComponent, TypeterminalUpdateComponent, TypeterminalDeleteDialogComponent],
  entryComponents: [TypeterminalDeleteDialogComponent],
})
export class TestUaaTypeterminalModule {}
