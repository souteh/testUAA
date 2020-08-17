import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { II18nTextTranslation } from 'app/shared/model/i-18-n-text-translation.model';
import { I18nTextTranslationService } from './i-18-n-text-translation.service';

@Component({
  templateUrl: './i-18-n-text-translation-delete-dialog.component.html',
})
export class I18nTextTranslationDeleteDialogComponent {
  i18nTextTranslation?: II18nTextTranslation;

  constructor(
    protected i18nTextTranslationService: I18nTextTranslationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.i18nTextTranslationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('i18nTextTranslationListModification');
      this.activeModal.close();
    });
  }
}
