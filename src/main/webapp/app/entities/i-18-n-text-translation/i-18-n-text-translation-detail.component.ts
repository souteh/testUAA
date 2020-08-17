import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { II18nTextTranslation } from 'app/shared/model/i-18-n-text-translation.model';

@Component({
  selector: 'jhi-i-18-n-text-translation-detail',
  templateUrl: './i-18-n-text-translation-detail.component.html',
})
export class I18nTextTranslationDetailComponent implements OnInit {
  i18nTextTranslation: II18nTextTranslation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ i18nTextTranslation }) => (this.i18nTextTranslation = i18nTextTranslation));
  }

  previousState(): void {
    window.history.back();
  }
}
