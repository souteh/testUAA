import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPdv, Pdv } from 'app/shared/model/pdv.model';
import { PdvService } from './pdv.service';

@Component({
  selector: 'jhi-pdv-update',
  templateUrl: './pdv-update.component.html',
})
export class PdvUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numero: [null, [Validators.required]],
    agence: [null, [Validators.maxLength(64)]],
    designation: [null, [Validators.maxLength(64)]],
    ville: [null, [Validators.maxLength(64)]],
    statut: [null, [Validators.maxLength(64)]],
  });

  constructor(protected pdvService: PdvService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pdv }) => {
      this.updateForm(pdv);
    });
  }

  updateForm(pdv: IPdv): void {
    this.editForm.patchValue({
      id: pdv.id,
      numero: pdv.numero,
      agence: pdv.agence,
      designation: pdv.designation,
      ville: pdv.ville,
      statut: pdv.statut,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pdv = this.createFromForm();
    if (pdv.id !== undefined) {
      this.subscribeToSaveResponse(this.pdvService.update(pdv));
    } else {
      this.subscribeToSaveResponse(this.pdvService.create(pdv));
    }
  }

  private createFromForm(): IPdv {
    return {
      ...new Pdv(),
      id: this.editForm.get(['id'])!.value,
      numero: this.editForm.get(['numero'])!.value,
      agence: this.editForm.get(['agence'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      statut: this.editForm.get(['statut'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPdv>>): void {
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
