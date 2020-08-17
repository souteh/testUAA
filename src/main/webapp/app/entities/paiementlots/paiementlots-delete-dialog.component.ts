import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaiementlots } from 'app/shared/model/paiementlots.model';
import { PaiementlotsService } from './paiementlots.service';

@Component({
  templateUrl: './paiementlots-delete-dialog.component.html',
})
export class PaiementlotsDeleteDialogComponent {
  paiementlots?: IPaiementlots;

  constructor(
    protected paiementlotsService: PaiementlotsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paiementlotsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paiementlotsListModification');
      this.activeModal.close();
    });
  }
}
