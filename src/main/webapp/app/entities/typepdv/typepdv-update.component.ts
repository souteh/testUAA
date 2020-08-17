import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypepdv, Typepdv } from 'app/shared/model/typepdv.model';
import { TypepdvService } from './typepdv.service';

@Component({
  selector: 'jhi-typepdv-update',
  templateUrl: './typepdv-update.component.html',
})
export class TypepdvUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idtypepdv: [null, [Validators.required]],
    reftypepdv: [null, [Validators.maxLength(254)]],
    type: [null, [Validators.maxLength(254)]],
    nbremaxterminaux: [],
    plafondpostpaye: [],
  });

  constructor(protected typepdvService: TypepdvService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typepdv }) => {
      this.updateForm(typepdv);
    });
  }

  updateForm(typepdv: ITypepdv): void {
    this.editForm.patchValue({
      id: typepdv.id,
      idtypepdv: typepdv.idtypepdv,
      reftypepdv: typepdv.reftypepdv,
      type: typepdv.type,
      nbremaxterminaux: typepdv.nbremaxterminaux,
      plafondpostpaye: typepdv.plafondpostpaye,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typepdv = this.createFromForm();
    if (typepdv.id !== undefined) {
      this.subscribeToSaveResponse(this.typepdvService.update(typepdv));
    } else {
      this.subscribeToSaveResponse(this.typepdvService.create(typepdv));
    }
  }

  private createFromForm(): ITypepdv {
    return {
      ...new Typepdv(),
      id: this.editForm.get(['id'])!.value,
      idtypepdv: this.editForm.get(['idtypepdv'])!.value,
      reftypepdv: this.editForm.get(['reftypepdv'])!.value,
      type: this.editForm.get(['type'])!.value,
      nbremaxterminaux: this.editForm.get(['nbremaxterminaux'])!.value,
      plafondpostpaye: this.editForm.get(['plafondpostpaye'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypepdv>>): void {
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
