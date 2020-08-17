import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDelaiannulation, Delaiannulation } from 'app/shared/model/delaiannulation.model';
import { DelaiannulationService } from './delaiannulation.service';

@Component({
  selector: 'jhi-delaiannulation-update',
  templateUrl: './delaiannulation-update.component.html',
})
export class DelaiannulationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    iddelaiannulation: [null, [Validators.required]],
    codedelaiannulation: [null, [Validators.maxLength(254)]],
    designationdelaiannulation: [null, [Validators.maxLength(254)]],
    valeurdelaiannulation: [],
    statut: [null, [Validators.maxLength(254)]],
  });

  constructor(
    protected delaiannulationService: DelaiannulationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delaiannulation }) => {
      this.updateForm(delaiannulation);
    });
  }

  updateForm(delaiannulation: IDelaiannulation): void {
    this.editForm.patchValue({
      id: delaiannulation.id,
      iddelaiannulation: delaiannulation.iddelaiannulation,
      codedelaiannulation: delaiannulation.codedelaiannulation,
      designationdelaiannulation: delaiannulation.designationdelaiannulation,
      valeurdelaiannulation: delaiannulation.valeurdelaiannulation,
      statut: delaiannulation.statut,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const delaiannulation = this.createFromForm();
    if (delaiannulation.id !== undefined) {
      this.subscribeToSaveResponse(this.delaiannulationService.update(delaiannulation));
    } else {
      this.subscribeToSaveResponse(this.delaiannulationService.create(delaiannulation));
    }
  }

  private createFromForm(): IDelaiannulation {
    return {
      ...new Delaiannulation(),
      id: this.editForm.get(['id'])!.value,
      iddelaiannulation: this.editForm.get(['iddelaiannulation'])!.value,
      codedelaiannulation: this.editForm.get(['codedelaiannulation'])!.value,
      designationdelaiannulation: this.editForm.get(['designationdelaiannulation'])!.value,
      valeurdelaiannulation: this.editForm.get(['valeurdelaiannulation'])!.value,
      statut: this.editForm.get(['statut'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDelaiannulation>>): void {
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
