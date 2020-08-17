import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduit, Produit } from 'app/shared/model/produit.model';
import { ProduitService } from './produit.service';

@Component({
  selector: 'jhi-produit-update',
  templateUrl: './produit-update.component.html',
})
export class ProduitUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    idproduit: [null, [Validators.required]],
    designation: [null, [Validators.maxLength(254)]],
    libelle: [null, [Validators.maxLength(254)]],
    codeproduit: [null, [Validators.maxLength(254)]],
    enjeumin: [],
    ordre: [null, [Validators.maxLength(254)]],
    nbrechevauxbase: [],
    nbreminpartant: [],
    champx: [],
    express: [],
    appartenance: [null, [Validators.maxLength(254)]],
    enjeumax: [],
    statut: [null, [Validators.maxLength(254)]],
  });

  constructor(protected produitService: ProduitService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.updateForm(produit);
    });
  }

  updateForm(produit: IProduit): void {
    this.editForm.patchValue({
      id: produit.id,
      idproduit: produit.idproduit,
      designation: produit.designation,
      libelle: produit.libelle,
      codeproduit: produit.codeproduit,
      enjeumin: produit.enjeumin,
      ordre: produit.ordre,
      nbrechevauxbase: produit.nbrechevauxbase,
      nbreminpartant: produit.nbreminpartant,
      champx: produit.champx,
      express: produit.express,
      appartenance: produit.appartenance,
      enjeumax: produit.enjeumax,
      statut: produit.statut,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produit = this.createFromForm();
    if (produit.id !== undefined) {
      this.subscribeToSaveResponse(this.produitService.update(produit));
    } else {
      this.subscribeToSaveResponse(this.produitService.create(produit));
    }
  }

  private createFromForm(): IProduit {
    return {
      ...new Produit(),
      id: this.editForm.get(['id'])!.value,
      idproduit: this.editForm.get(['idproduit'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      codeproduit: this.editForm.get(['codeproduit'])!.value,
      enjeumin: this.editForm.get(['enjeumin'])!.value,
      ordre: this.editForm.get(['ordre'])!.value,
      nbrechevauxbase: this.editForm.get(['nbrechevauxbase'])!.value,
      nbreminpartant: this.editForm.get(['nbreminpartant'])!.value,
      champx: this.editForm.get(['champx'])!.value,
      express: this.editForm.get(['express'])!.value,
      appartenance: this.editForm.get(['appartenance'])!.value,
      enjeumax: this.editForm.get(['enjeumax'])!.value,
      statut: this.editForm.get(['statut'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduit>>): void {
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
