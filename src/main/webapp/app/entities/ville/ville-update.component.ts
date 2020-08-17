import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVille, Ville } from 'app/shared/model/ville.model';
import { VilleService } from './ville.service';

@Component({
  selector: 'jhi-ville-update',
  templateUrl: './ville-update.component.html',
})
export class VilleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idville: [null, [Validators.required]],
    designation: [],
    codeville: [null, [Validators.maxLength(254)]],
  });

  constructor(protected villeService: VilleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ville }) => {
      this.updateForm(ville);
    });
  }

  updateForm(ville: IVille): void {
    this.editForm.patchValue({
      id: ville.id,
      idville: ville.idville,
      designation: ville.designation,
      codeville: ville.codeville,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ville = this.createFromForm();
    if (ville.id !== undefined) {
      this.subscribeToSaveResponse(this.villeService.update(ville));
    } else {
      this.subscribeToSaveResponse(this.villeService.create(ville));
    }
  }

  private createFromForm(): IVille {
    return {
      ...new Ville(),
      id: this.editForm.get(['id'])!.value,
      idville: this.editForm.get(['idville'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      codeville: this.editForm.get(['codeville'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVille>>): void {
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
