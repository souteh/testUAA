import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHypodrome } from 'app/shared/model/hypodrome.model';
import { HypodromeService } from './hypodrome.service';

@Component({
  templateUrl: './hypodrome-delete-dialog.component.html',
})
export class HypodromeDeleteDialogComponent {
  hypodrome?: IHypodrome;

  constructor(protected hypodromeService: HypodromeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hypodromeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('hypodromeListModification');
      this.activeModal.close();
    });
  }
}
