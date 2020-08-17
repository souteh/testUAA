import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgence } from 'app/shared/model/agence.model';
import { AgenceService } from './agence.service';

@Component({
  templateUrl: './agence-delete-dialog.component.html',
})
export class AgenceDeleteDialogComponent {
  agence?: IAgence;

  constructor(protected agenceService: AgenceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('agenceListModification');
      this.activeModal.close();
    });
  }
}
