import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { AttributaireComponent } from './attributaire.component';
import { AttributaireDetailComponent } from './attributaire-detail.component';
import { AttributaireUpdateComponent } from './attributaire-update.component';
import { AttributaireDeleteDialogComponent } from './attributaire-delete-dialog.component';
import { attributaireRoute } from './attributaire.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(attributaireRoute)],
  declarations: [AttributaireComponent, AttributaireDetailComponent, AttributaireUpdateComponent, AttributaireDeleteDialogComponent],
  entryComponents: [AttributaireDeleteDialogComponent],
})
export class TestUaaAttributaireModule {}
