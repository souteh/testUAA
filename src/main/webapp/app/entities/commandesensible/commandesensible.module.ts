import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { CommandesensibleComponent } from './commandesensible.component';
import { CommandesensibleDetailComponent } from './commandesensible-detail.component';
import { CommandesensibleUpdateComponent } from './commandesensible-update.component';
import { CommandesensibleDeleteDialogComponent } from './commandesensible-delete-dialog.component';
import { commandesensibleRoute } from './commandesensible.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(commandesensibleRoute)],
  declarations: [
    CommandesensibleComponent,
    CommandesensibleDetailComponent,
    CommandesensibleUpdateComponent,
    CommandesensibleDeleteDialogComponent,
  ],
  entryComponents: [CommandesensibleDeleteDialogComponent],
})
export class TestUaaCommandesensibleModule {}
