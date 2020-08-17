import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISmtp } from 'app/shared/model/smtp.model';
import { SmtpService } from './smtp.service';

@Component({
  templateUrl: './smtp-delete-dialog.component.html',
})
export class SmtpDeleteDialogComponent {
  smtp?: ISmtp;

  constructor(protected smtpService: SmtpService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.smtpService.delete(id).subscribe(() => {
      this.eventManager.broadcast('smtpListModification');
      this.activeModal.close();
    });
  }
}
