import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaiementlots } from 'app/shared/model/paiementlots.model';

@Component({
  selector: 'jhi-paiementlots-detail',
  templateUrl: './paiementlots-detail.component.html',
})
export class PaiementlotsDetailComponent implements OnInit {
  paiementlots: IPaiementlots | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiementlots }) => (this.paiementlots = paiementlots));
  }

  previousState(): void {
    window.history.back();
  }
}
