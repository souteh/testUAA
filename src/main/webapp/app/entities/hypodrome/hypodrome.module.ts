import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { HypodromeComponent } from './hypodrome.component';
import { HypodromeDetailComponent } from './hypodrome-detail.component';
import { HypodromeUpdateComponent } from './hypodrome-update.component';
import { HypodromeDeleteDialogComponent } from './hypodrome-delete-dialog.component';
import { hypodromeRoute } from './hypodrome.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(hypodromeRoute)],
  declarations: [HypodromeComponent, HypodromeDetailComponent, HypodromeUpdateComponent, HypodromeDeleteDialogComponent],
  entryComponents: [HypodromeDeleteDialogComponent],
})
export class TestUaaHypodromeModule {}
