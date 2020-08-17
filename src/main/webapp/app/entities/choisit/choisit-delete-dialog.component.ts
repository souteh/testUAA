import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChoisit } from 'app/shared/model/choisit.model';
import { ChoisitService } from './choisit.service';

@Component({
  templateUrl: './choisit-delete-dialog.component.html',
})
export class ChoisitDeleteDialogComponent {
  choisit?: IChoisit;

  constructor(protected choisitService: ChoisitService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.choisitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('choisitListModification');
      this.activeModal.close();
    });
  }
}
