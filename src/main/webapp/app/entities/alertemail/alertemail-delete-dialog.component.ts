import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlertemail } from 'app/shared/model/alertemail.model';
import { AlertemailService } from './alertemail.service';

@Component({
  templateUrl: './alertemail-delete-dialog.component.html',
})
export class AlertemailDeleteDialogComponent {
  alertemail?: IAlertemail;

  constructor(
    protected alertemailService: AlertemailService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alertemailService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alertemailListModification');
      this.activeModal.close();
    });
  }
}
