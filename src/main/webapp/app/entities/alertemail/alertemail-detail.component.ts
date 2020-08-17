import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlertemail } from 'app/shared/model/alertemail.model';

@Component({
  selector: 'jhi-alertemail-detail',
  templateUrl: './alertemail-detail.component.html',
})
export class AlertemailDetailComponent implements OnInit {
  alertemail: IAlertemail | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alertemail }) => (this.alertemail = alertemail));
  }

  previousState(): void {
    window.history.back();
  }
}
