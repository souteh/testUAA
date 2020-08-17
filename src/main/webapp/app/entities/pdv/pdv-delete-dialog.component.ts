import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPdv } from 'app/shared/model/pdv.model';
import { PdvService } from './pdv.service';

@Component({
  templateUrl: './pdv-delete-dialog.component.html',
})
export class PdvDeleteDialogComponent {
  pdv?: IPdv;

  constructor(protected pdvService: PdvService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pdvService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pdvListModification');
      this.activeModal.close();
    });
  }
}
