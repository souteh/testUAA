import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttributaire } from 'app/shared/model/attributaire.model';
import { AttributaireService } from './attributaire.service';

@Component({
  templateUrl: './attributaire-delete-dialog.component.html',
})
export class AttributaireDeleteDialogComponent {
  attributaire?: IAttributaire;

  constructor(
    protected attributaireService: AttributaireService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attributaireService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attributaireListModification');
      this.activeModal.close();
    });
  }
}
