import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommandesensible } from 'app/shared/model/commandesensible.model';

@Component({
  selector: 'jhi-commandesensible-detail',
  templateUrl: './commandesensible-detail.component.html',
})
export class CommandesensibleDetailComponent implements OnInit {
  commandesensible: ICommandesensible | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commandesensible }) => (this.commandesensible = commandesensible));
  }

  previousState(): void {
    window.history.back();
  }
}
