import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDelaiannulation } from 'app/shared/model/delaiannulation.model';
import { DelaiannulationService } from './delaiannulation.service';

@Component({
  templateUrl: './delaiannulation-delete-dialog.component.html',
})
export class DelaiannulationDeleteDialogComponent {
  delaiannulation?: IDelaiannulation;

  constructor(
    protected delaiannulationService: DelaiannulationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.delaiannulationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('delaiannulationListModification');
      this.activeModal.close();
    });
  }
}
