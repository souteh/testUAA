import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPdv } from 'app/shared/model/pdv.model';

@Component({
  selector: 'jhi-pdv-detail',
  templateUrl: './pdv-detail.component.html',
})
export class PdvDetailComponent implements OnInit {
  pdv: IPdv | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pdv }) => (this.pdv = pdv));
  }

  previousState(): void {
    window.history.back();
  }
}
