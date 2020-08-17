import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChoisit, Choisit } from 'app/shared/model/choisit.model';
import { ChoisitService } from './choisit.service';
import { IAttributaire } from 'app/shared/model/attributaire.model';
import { AttributaireService } from 'app/entities/attributaire/attributaire.service';

@Component({
  selector: 'jhi-choisit-update',
  templateUrl: './choisit-update.component.html',
})
export class ChoisitUpdateComponent implements OnInit {
  isSaving = false;
  attributaires: IAttributaire[] = [];

  editForm = this.fb.group({
    id: [],
    idproduit: [null, [Validators.required]],
    idattributaire: [null, Validators.required],
  });

  constructor(
    protected choisitService: ChoisitService,
    protected attributaireService: AttributaireService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ choisit }) => {
      this.updateForm(choisit);

      this.attributaireService.query().subscribe((res: HttpResponse<IAttributaire[]>) => (this.attributaires = res.body || []));
    });
  }

  updateForm(choisit: IChoisit): void {
    this.editForm.patchValue({
      id: choisit.id,
      idproduit: choisit.idproduit,
      idattributaire: choisit.idattributaire,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const choisit = this.createFromForm();
    if (choisit.id !== undefined) {
      this.subscribeToSaveResponse(this.choisitService.update(choisit));
    } else {
      this.subscribeToSaveResponse(this.choisitService.create(choisit));
    }
  }

  private createFromForm(): IChoisit {
    return {
      ...new Choisit(),
      id: this.editForm.get(['id'])!.value,
      idproduit: this.editForm.get(['idproduit'])!.value,
      idattributaire: this.editForm.get(['idattributaire'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChoisit>>): void {
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

  trackById(index: number, item: IAttributaire): any {
    return item.id;
  }
}
