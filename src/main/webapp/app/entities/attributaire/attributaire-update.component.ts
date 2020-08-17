import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttributaire, Attributaire } from 'app/shared/model/attributaire.model';
import { AttributaireService } from './attributaire.service';

@Component({
  selector: 'jhi-attributaire-update',
  templateUrl: './attributaire-update.component.html',
})
export class AttributaireUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idattributaire: [null, [Validators.required]],
    libelle: [null, [Validators.maxLength(254)]],
    codeattributaire: [null, [Validators.maxLength(254)]],
  });

  constructor(protected attributaireService: AttributaireService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attributaire }) => {
      this.updateForm(attributaire);
    });
  }

  updateForm(attributaire: IAttributaire): void {
    this.editForm.patchValue({
      id: attributaire.id,
      idattributaire: attributaire.idattributaire,
      libelle: attributaire.libelle,
      codeattributaire: attributaire.codeattributaire,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attributaire = this.createFromForm();
    if (attributaire.id !== undefined) {
      this.subscribeToSaveResponse(this.attributaireService.update(attributaire));
    } else {
      this.subscribeToSaveResponse(this.attributaireService.create(attributaire));
    }
  }

  private createFromForm(): IAttributaire {
    return {
      ...new Attributaire(),
      id: this.editForm.get(['id'])!.value,
      idattributaire: this.editForm.get(['idattributaire'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      codeattributaire: this.editForm.get(['codeattributaire'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttributaire>>): void {
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
