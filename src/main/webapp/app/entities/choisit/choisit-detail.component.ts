import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChoisit } from 'app/shared/model/choisit.model';

@Component({
  selector: 'jhi-choisit-detail',
  templateUrl: './choisit-detail.component.html',
})
export class ChoisitDetailComponent implements OnInit {
  choisit: IChoisit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ choisit }) => (this.choisit = choisit));
  }

  previousState(): void {
    window.history.back();
  }
}
