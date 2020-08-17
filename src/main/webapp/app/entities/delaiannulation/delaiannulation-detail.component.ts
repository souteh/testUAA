import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDelaiannulation } from 'app/shared/model/delaiannulation.model';

@Component({
  selector: 'jhi-delaiannulation-detail',
  templateUrl: './delaiannulation-detail.component.html',
})
export class DelaiannulationDetailComponent implements OnInit {
  delaiannulation: IDelaiannulation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ delaiannulation }) => (this.delaiannulation = delaiannulation));
  }

  previousState(): void {
    window.history.back();
  }
}
