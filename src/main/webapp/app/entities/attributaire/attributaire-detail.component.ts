import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttributaire } from 'app/shared/model/attributaire.model';

@Component({
  selector: 'jhi-attributaire-detail',
  templateUrl: './attributaire-detail.component.html',
})
export class AttributaireDetailComponent implements OnInit {
  attributaire: IAttributaire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attributaire }) => (this.attributaire = attributaire));
  }

  previousState(): void {
    window.history.back();
  }
}
