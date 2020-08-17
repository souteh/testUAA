import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestUaaSharedModule } from 'app/shared/shared.module';
import { I18nTextTranslationComponent } from './i-18-n-text-translation.component';
import { I18nTextTranslationDetailComponent } from './i-18-n-text-translation-detail.component';
import { I18nTextTranslationUpdateComponent } from './i-18-n-text-translation-update.component';
import { I18nTextTranslationDeleteDialogComponent } from './i-18-n-text-translation-delete-dialog.component';
import { i18nTextTranslationRoute } from './i-18-n-text-translation.route';

@NgModule({
  imports: [TestUaaSharedModule, RouterModule.forChild(i18nTextTranslationRoute)],
  declarations: [
    I18nTextTranslationComponent,
    I18nTextTranslationDetailComponent,
    I18nTextTranslationUpdateComponent,
    I18nTextTranslationDeleteDialogComponent,
  ],
  entryComponents: [I18nTextTranslationDeleteDialogComponent],
})
export class TestUaaI18nTextTranslationModule {}
