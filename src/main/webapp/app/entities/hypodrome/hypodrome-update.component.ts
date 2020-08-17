import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHypodrome, Hypodrome } from 'app/shared/model/hypodrome.model';
import { HypodromeService } from './hypodrome.service';

@Component({
  selector: 'jhi-hypodrome-update',
  templateUrl: './hypodrome-update.component.html',
})
export class HypodromeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idhypodrome: [null, [Validators.required]],
    codehypodrome: [],
    abreviation: [null, [Validators.maxLength(254)]],
    pays: [null, [Validators.maxLength(254)]],
  });

  constructor(protected hypodromeService: HypodromeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hypodrome }) => {
      this.updateForm(hypodrome);
    });
  }

  updateForm(hypodrome: IHypodrome): void {
    this.editForm.patchValue({
      id: hypodrome.id,
      idhypodrome: hypodrome.idhypodrome,
      codehypodrome: hypodrome.codehypodrome,
      abreviation: hypodrome.abreviation,
      pays: hypodrome.pays,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hypodrome = this.createFromForm();
    if (hypodrome.id !== undefined) {
      this.subscribeToSaveResponse(this.hypodromeService.update(hypodrome));
    } else {
      this.subscribeToSaveResponse(this.hypodromeService.create(hypodrome));
    }
  }

  private createFromForm(): IHypodrome {
    return {
      ...new Hypodrome(),
      id: this.editForm.get(['id'])!.value,
      idhypodrome: this.editForm.get(['idhypodrome'])!.value,
      codehypodrome: this.editForm.get(['codehypodrome'])!.value,
      abreviation: this.editForm.get(['abreviation'])!.value,
      pays: this.editForm.get(['pays'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHypodrome>>): void {
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
