import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { SmtpComponent } from './smtp.component';
import { SmtpDetailComponent } from './smtp-detail.component';
import { SmtpUpdateComponent } from './smtp-update.component';
import { SmtpDeleteDialogComponent } from './smtp-delete-dialog.component';
import { smtpRoute } from './smtp.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(smtpRoute)],
  declarations: [SmtpComponent, SmtpDetailComponent, SmtpUpdateComponent, SmtpDeleteDialogComponent],
  entryComponents: [SmtpDeleteDialogComponent],
})
export class TestUaaSmtpModule {}
