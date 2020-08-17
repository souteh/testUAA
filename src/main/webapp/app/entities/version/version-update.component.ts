import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVersion, Version } from 'app/shared/model/version.model';
import { VersionService } from './version.service';
import { ITypeterminal } from 'app/shared/model/typeterminal.model';
import { TypeterminalService } from 'app/entities/typeterminal/typeterminal.service';

@Component({
  selector: 'jhi-version-update',
  templateUrl: './version-update.component.html',
})
export class VersionUpdateComponent implements OnInit {
  isSaving = false;
  typeterminals: ITypeterminal[] = [];

  editForm = this.fb.group({
    id: [],
    idversion: [null, [Validators.required]],
    refversion: [null, [Validators.maxLength(254)]],
    libelle: [null, [Validators.maxLength(254)]],
    date: [],
    fichier: [null, [Validators.maxLength(254)]],
    idtypeterminal: [null, Validators.required],
  });

  constructor(
    protected versionService: VersionService,
    protected typeterminalService: TypeterminalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ version }) => {
      if (!version.id) {
        const today = moment().startOf('day');
        version.date = today;
      }

      this.updateForm(version);

      this.typeterminalService.query().subscribe((res: HttpResponse<ITypeterminal[]>) => (this.typeterminals = res.body || []));
    });
  }

  updateForm(version: IVersion): void {
    this.editForm.patchValue({
      id: version.id,
      idversion: version.idversion,
      refversion: version.refversion,
      libelle: version.libelle,
      date: version.date ? version.date.format(DATE_TIME_FORMAT) : null,
      fichier: version.fichier,
      idtypeterminal: version.idtypeterminal,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const version = this.createFromForm();
    if (version.id !== undefined) {
      this.subscribeToSaveResponse(this.versionService.update(version));
    } else {
      this.subscribeToSaveResponse(this.versionService.create(version));
    }
  }

  private createFromForm(): IVersion {
    return {
      ...new Version(),
      id: this.editForm.get(['id'])!.value,
      idversion: this.editForm.get(['idversion'])!.value,
      refversion: this.editForm.get(['refversion'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      fichier: this.editForm.get(['fichier'])!.value,
      idtypeterminal: this.editForm.get(['idtypeterminal'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVersion>>): void {
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

  trackById(index: number, item: ITypeterminal): any {
    return item.id;
  }
}
