import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeterminal } from 'app/shared/model/typeterminal.model';
import { TypeterminalService } from './typeterminal.service';

@Component({
  templateUrl: './typeterminal-delete-dialog.component.html',
})
export class TypeterminalDeleteDialogComponent {
  typeterminal?: ITypeterminal;

  constructor(
    protected typeterminalService: TypeterminalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeterminalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typeterminalListModification');
      this.activeModal.close();
    });
  }
}
