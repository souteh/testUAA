import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISmtp, Smtp } from 'app/shared/model/smtp.model';
import { SmtpService } from './smtp.service';

@Component({
  selector: 'jhi-smtp-update',
  templateUrl: './smtp-update.component.html',
})
export class SmtpUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idsmtp: [null, [Validators.required]],
    codesmtp: [null, [Validators.maxLength(254)]],
    adresseipsmtp: [null, [Validators.maxLength(254)]],
    authentification: [null, [Validators.maxLength(254)]],
    statut: [null, [Validators.maxLength(254)]],
    login: [null, [Validators.maxLength(254)]],
    password: [null, [Validators.maxLength(254)]],
  });

  constructor(protected smtpService: SmtpService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ smtp }) => {
      this.updateForm(smtp);
    });
  }

  updateForm(smtp: ISmtp): void {
    this.editForm.patchValue({
      id: smtp.id,
      idsmtp: smtp.idsmtp,
      codesmtp: smtp.codesmtp,
      adresseipsmtp: smtp.adresseipsmtp,
      authentification: smtp.authentification,
      statut: smtp.statut,
      login: smtp.login,
      password: smtp.password,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const smtp = this.createFromForm();
    if (smtp.id !== undefined) {
      this.subscribeToSaveResponse(this.smtpService.update(smtp));
    } else {
      this.subscribeToSaveResponse(this.smtpService.create(smtp));
    }
  }

  private createFromForm(): ISmtp {
    return {
      ...new Smtp(),
      id: this.editForm.get(['id'])!.value,
      idsmtp: this.editForm.get(['idsmtp'])!.value,
      codesmtp: this.editForm.get(['codesmtp'])!.value,
      adresseipsmtp: this.editForm.get(['adresseipsmtp'])!.value,
      authentification: this.editForm.get(['authentification'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      login: this.editForm.get(['login'])!.value,
      password: this.editForm.get(['password'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISmtp>>): void {
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
