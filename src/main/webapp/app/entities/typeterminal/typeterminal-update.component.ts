import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypeterminal, Typeterminal } from 'app/shared/model/typeterminal.model';
import { TypeterminalService } from './typeterminal.service';

@Component({
  selector: 'jhi-typeterminal-update',
  templateUrl: './typeterminal-update.component.html',
})
export class TypeterminalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idtypeterminal: [null, [Validators.required]],
    codetypeterminal: [null, [Validators.maxLength(254)]],
    libelle: [null, [Validators.maxLength(254)]],
  });

  constructor(protected typeterminalService: TypeterminalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeterminal }) => {
      this.updateForm(typeterminal);
    });
  }

  updateForm(typeterminal: ITypeterminal): void {
    this.editForm.patchValue({
      id: typeterminal.id,
      idtypeterminal: typeterminal.idtypeterminal,
      codetypeterminal: typeterminal.codetypeterminal,
      libelle: typeterminal.libelle,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeterminal = this.createFromForm();
    if (typeterminal.id !== undefined) {
      this.subscribeToSaveResponse(this.typeterminalService.update(typeterminal));
    } else {
      this.subscribeToSaveResponse(this.typeterminalService.create(typeterminal));
    }
  }

  private createFromForm(): ITypeterminal {
    return {
      ...new Typeterminal(),
      id: this.editForm.get(['id'])!.value,
      idtypeterminal: this.editForm.get(['idtypeterminal'])!.value,
      codetypeterminal: this.editForm.get(['codetypeterminal'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeterminal>>): void {
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
