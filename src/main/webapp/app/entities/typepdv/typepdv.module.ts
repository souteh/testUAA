import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { TypepdvComponent } from './typepdv.component';
import { TypepdvDetailComponent } from './typepdv-detail.component';
import { TypepdvUpdateComponent } from './typepdv-update.component';
import { TypepdvDeleteDialogComponent } from './typepdv-delete-dialog.component';
import { typepdvRoute } from './typepdv.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(typepdvRoute)],
  declarations: [TypepdvComponent, TypepdvDetailComponent, TypepdvUpdateComponent, TypepdvDeleteDialogComponent],
  entryComponents: [TypepdvDeleteDialogComponent],
})
export class TestUaaTypepdvModule {}
