import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { II18nTextTranslation, I18nTextTranslation } from 'app/shared/model/i-18-n-text-translation.model';
import { I18nTextTranslationService } from './i-18-n-text-translation.service';

@Component({
  selector: 'jhi-i-18-n-text-translation-update',
  templateUrl: './i-18-n-text-translation-update.component.html',
})
export class I18nTextTranslationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    lang: [null, [Validators.required, Validators.maxLength(45)]],
    translation: [null, [Validators.required, Validators.maxLength(512)]],
    i18nTextId: [null, [Validators.required]],
  });

  constructor(
    protected i18nTextTranslationService: I18nTextTranslationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ i18nTextTranslation }) => {
      this.updateForm(i18nTextTranslation);
    });
  }

  updateForm(i18nTextTranslation: II18nTextTranslation): void {
    this.editForm.patchValue({
      id: i18nTextTranslation.id,
      lang: i18nTextTranslation.lang,
      translation: i18nTextTranslation.translation,
      i18nTextId: i18nTextTranslation.i18nTextId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const i18nTextTranslation = this.createFromForm();
    if (i18nTextTranslation.id !== undefined) {
      this.subscribeToSaveResponse(this.i18nTextTranslationService.update(i18nTextTranslation));
    } else {
      this.subscribeToSaveResponse(this.i18nTextTranslationService.create(i18nTextTranslation));
    }
  }

  private createFromForm(): II18nTextTranslation {
    return {
      ...new I18nTextTranslation(),
      id: this.editForm.get(['id'])!.value,
      lang: this.editForm.get(['lang'])!.value,
      translation: this.editForm.get(['translation'])!.value,
      i18nTextId: this.editForm.get(['i18nTextId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<II18nTextTranslation>>): void {
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
