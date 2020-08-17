import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVoucher, Voucher } from 'app/shared/model/voucher.model';
import { VoucherService } from './voucher.service';

@Component({
  selector: 'jhi-voucher-update',
  templateUrl: './voucher-update.component.html',
})
export class VoucherUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idvoucher: [null, [Validators.required]],
    codevoucher: [null, [Validators.maxLength(254)]],
    statut: [null, [Validators.maxLength(254)]],
    lieu: [null, [Validators.maxLength(254)]],
    seuil: [],
    delaipurge: [],
    plafond: [],
  });

  constructor(protected voucherService: VoucherService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voucher }) => {
      this.updateForm(voucher);
    });
  }

  updateForm(voucher: IVoucher): void {
    this.editForm.patchValue({
      id: voucher.id,
      idvoucher: voucher.idvoucher,
      codevoucher: voucher.codevoucher,
      statut: voucher.statut,
      lieu: voucher.lieu,
      seuil: voucher.seuil,
      delaipurge: voucher.delaipurge,
      plafond: voucher.plafond,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voucher = this.createFromForm();
    if (voucher.id !== undefined) {
      this.subscribeToSaveResponse(this.voucherService.update(voucher));
    } else {
      this.subscribeToSaveResponse(this.voucherService.create(voucher));
    }
  }

  private createFromForm(): IVoucher {
    return {
      ...new Voucher(),
      id: this.editForm.get(['id'])!.value,
      idvoucher: this.editForm.get(['idvoucher'])!.value,
      codevoucher: this.editForm.get(['codevoucher'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      lieu: this.editForm.get(['lieu'])!.value,
      seuil: this.editForm.get(['seuil'])!.value,
      delaipurge: this.editForm.get(['delaipurge'])!.value,
      plafond: this.editForm.get(['plafond'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucher>>): void {
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
