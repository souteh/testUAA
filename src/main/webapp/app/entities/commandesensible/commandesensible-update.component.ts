import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICommandesensible, Commandesensible } from 'app/shared/model/commandesensible.model';
import { CommandesensibleService } from './commandesensible.service';

@Component({
  selector: 'jhi-commandesensible-update',
  templateUrl: './commandesensible-update.component.html',
})
export class CommandesensibleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idcommandesensible: [null, [Validators.required]],
    codecommandesensible: [null, [Validators.maxLength(254)]],
    libellecommandesensible: [null, [Validators.maxLength(254)]],
  });

  constructor(
    protected commandesensibleService: CommandesensibleService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandesensible }) => {
      this.updateForm(commandesensible);
    });
  }

  updateForm(commandesensible: ICommandesensible): void {
    this.editForm.patchValue({
      id: commandesensible.id,
      idcommandesensible: commandesensible.idcommandesensible,
      codecommandesensible: commandesensible.codecommandesensible,
      libellecommandesensible: commandesensible.libellecommandesensible,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commandesensible = this.createFromForm();
    if (commandesensible.id !== undefined) {
      this.subscribeToSaveResponse(this.commandesensibleService.update(commandesensible));
    } else {
      this.subscribeToSaveResponse(this.commandesensibleService.create(commandesensible));
    }
  }

  private createFromForm(): ICommandesensible {
    return {
      ...new Commandesensible(),
      id: this.editForm.get(['id'])!.value,
      idcommandesensible: this.editForm.get(['idcommandesensible'])!.value,
      codecommandesensible: this.editForm.get(['codecommandesensible'])!.value,
      libellecommandesensible: this.editForm.get(['libellecommandesensible'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommandesensible>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
