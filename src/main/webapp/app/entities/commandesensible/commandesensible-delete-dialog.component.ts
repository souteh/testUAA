import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommandesensible } from 'app/shared/model/commandesensible.model';
import { CommandesensibleService } from './commandesensible.service';

@Component({
  templateUrl: './commandesensible-delete-dialog.component.html',
})
export class CommandesensibleDeleteDialogComponent {
  commandesensible?: ICommandesensible;

  constructor(
    protected commandesensibleService: CommandesensibleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commandesensibleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commandesensibleListModification');
      this.activeModal.close();
    });
  }
}
