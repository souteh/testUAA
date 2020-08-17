import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeterminal } from 'app/shared/model/typeterminal.model';

@Component({
  selector: 'jhi-typeterminal-detail',
  templateUrl: './typeterminal-detail.component.html',
})
export class TypeterminalDetailComponent implements OnInit {
  typeterminal: ITypeterminal | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeterminal }) => (this.typeterminal = typeterminal));
  }

  previousState(): void {
    window.history.back();
  }
}
