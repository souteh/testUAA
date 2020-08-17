import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAlertemail, Alertemail } from 'app/shared/model/alertemail.model';
import { AlertemailService } from './alertemail.service';

@Component({
  selector: 'jhi-alertemail-update',
  templateUrl: './alertemail-update.component.html',
})
export class AlertemailUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idalertemail: [null, [Validators.required]],
    codealertemail: [null, [Validators.maxLength(254)]],
    typealertemail: [null, [Validators.maxLength(254)]],
    objetalertemail: [null, [Validators.maxLength(254)]],
    adressemaildiffusion: [null, [Validators.maxLength(254)]],
  });

  constructor(protected alertemailService: AlertemailService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alertemail }) => {
      this.updateForm(alertemail);
    });
  }

  updateForm(alertemail: IAlertemail): void {
    this.editForm.patchValue({
      id: alertemail.id,
      idalertemail: alertemail.idalertemail,
      codealertemail: alertemail.codealertemail,
      typealertemail: alertemail.typealertemail,
      objetalertemail: alertemail.objetalertemail,
      adressemaildiffusion: alertemail.adressemaildiffusion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alertemail = this.createFromForm();
    if (alertemail.id !== undefined) {
      this.subscribeToSaveResponse(this.alertemailService.update(alertemail));
    } else {
      this.subscribeToSaveResponse(this.alertemailService.create(alertemail));
    }
  }

  private createFromForm(): IAlertemail {
    return {
      ...new Alertemail(),
      id: this.editForm.get(['id'])!.value,
      idalertemail: this.editForm.get(['idalertemail'])!.value,
      codealertemail: this.editForm.get(['codealertemail'])!.value,
      typealertemail: this.editForm.get(['typealertemail'])!.value,
      objetalertemail: this.editForm.get(['objetalertemail'])!.value,
      adressemaildiffusion: this.editForm.get(['adressemaildiffusion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlertemail>>): void {
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
