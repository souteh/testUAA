import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISmtp } from 'app/shared/model/smtp.model';

@Component({
  selector: 'jhi-smtp-detail',
  templateUrl: './smtp-detail.component.html',
})
export class SmtpDetailComponent implements OnInit {
  smtp: ISmtp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ smtp }) => (this.smtp = smtp));
  }

  previousState(): void {
    window.history.back();
  }
}
