import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { AlertemailComponent } from './alertemail.component';
import { AlertemailDetailComponent } from './alertemail-detail.component';
import { AlertemailUpdateComponent } from './alertemail-update.component';
import { AlertemailDeleteDialogComponent } from './alertemail-delete-dialog.component';
import { alertemailRoute } from './alertemail.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(alertemailRoute)],
  declarations: [AlertemailComponent, AlertemailDetailComponent, AlertemailUpdateComponent, AlertemailDeleteDialogComponent],
  entryComponents: [AlertemailDeleteDialogComponent],
})
export class TestUaaAlertemailModule {}
