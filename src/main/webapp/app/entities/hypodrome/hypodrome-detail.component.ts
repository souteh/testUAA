import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHypodrome } from 'app/shared/model/hypodrome.model';

@Component({
  selector: 'jhi-hypodrome-detail',
  templateUrl: './hypodrome-detail.component.html',
})
export class HypodromeDetailComponent implements OnInit {
  hypodrome: IHypodrome | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hypodrome }) => (this.hypodrome = hypodrome));
  }

  previousState(): void {
    window.history.back();
  }
}
