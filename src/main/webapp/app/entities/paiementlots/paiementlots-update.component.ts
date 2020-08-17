import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaiementlots, Paiementlots } from 'app/shared/model/paiementlots.model';
import { PaiementlotsService } from './paiementlots.service';

@Component({
  selector: 'jhi-paiementlots-update',
  templateUrl: './paiementlots-update.component.html',
})
export class PaiementlotsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idlotpaiement: [null, [Validators.required]],
    codepaiement: [null, [Validators.maxLength(254)]],
    type: [null, [Validators.maxLength(254)]],
    seuil: [],
    montantavance: [],
    delaipurge: [],
    lieuautorise: [null, [Validators.maxLength(254)]],
    lieuannulation: [null, [Validators.maxLength(254)]],
  });

  constructor(protected paiementlotsService: PaiementlotsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiementlots }) => {
      this.updateForm(paiementlots);
    });
  }

  updateForm(paiementlots: IPaiementlots): void {
    this.editForm.patchValue({
      id: paiementlots.id,
      idlotpaiement: paiementlots.idlotpaiement,
      codepaiement: paiementlots.codepaiement,
      type: paiementlots.type,
      seuil: paiementlots.seuil,
      montantavance: paiementlots.montantavance,
      delaipurge: paiementlots.delaipurge,
      lieuautorise: paiementlots.lieuautorise,
      lieuannulation: paiementlots.lieuannulation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paiementlots = this.createFromForm();
    if (paiementlots.id !== undefined) {
      this.subscribeToSaveResponse(this.paiementlotsService.update(paiementlots));
    } else {
      this.subscribeToSaveResponse(this.paiementlotsService.create(paiementlots));
    }
  }

  private createFromForm(): IPaiementlots {
    return {
      ...new Paiementlots(),
      id: this.editForm.get(['id'])!.value,
      idlotpaiement: this.editForm.get(['idlotpaiement'])!.value,
      codepaiement: this.editForm.get(['codepaiement'])!.value,
      type: this.editForm.get(['type'])!.value,
      seuil: this.editForm.get(['seuil'])!.value,
      montantavance: this.editForm.get(['montantavance'])!.value,
      delaipurge: this.editForm.get(['delaipurge'])!.value,
      lieuautorise: this.editForm.get(['lieuautorise'])!.value,
      lieuannulation: this.editForm.get(['lieuannulation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaiementlots>>): void {
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
