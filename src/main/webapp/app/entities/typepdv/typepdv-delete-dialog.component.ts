import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypepdv } from 'app/shared/model/typepdv.model';
import { TypepdvService } from './typepdv.service';

@Component({
  templateUrl: './typepdv-delete-dialog.component.html',
})
export class TypepdvDeleteDialogComponent {
  typepdv?: ITypepdv;

  constructor(protected typepdvService: TypepdvService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typepdvService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typepdvListModification');
      this.activeModal.close();
    });
  }
}
