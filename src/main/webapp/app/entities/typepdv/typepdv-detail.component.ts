import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypepdv } from 'app/shared/model/typepdv.model';

@Component({
  selector: 'jhi-typepdv-detail',
  templateUrl: './typepdv-detail.component.html',
})
export class TypepdvDetailComponent implements OnInit {
  typepdv: ITypepdv | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typepdv }) => (this.typepdv = typepdv));
  }

  previousState(): void {
    window.history.back();
  }
}
