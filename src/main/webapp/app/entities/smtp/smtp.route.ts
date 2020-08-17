import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISmtp, Smtp } from 'app/shared/model/smtp.model';
import { SmtpService } from './smtp.service';
import { SmtpComponent } from './smtp.component';
import { SmtpDetailComponent } from './smtp-detail.component';
import { SmtpUpdateComponent } from './smtp-update.component';

@Injectable({ providedIn: 'root' })
export class SmtpResolve implements Resolve<ISmtp> {
  constructor(private service: SmtpService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISmtp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((smtp: HttpResponse<Smtp>) => {
          if (smtp.body) {
            return of(smtp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Smtp());
  }
}

export const smtpRoute: Routes = [
  {
    path: '',
    component: SmtpComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.smtp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SmtpDetailComponent,
    resolve: {
      smtp: SmtpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.smtp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SmtpUpdateComponent,
    resolve: {
      smtp: SmtpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.smtp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SmtpUpdateComponent,
    resolve: {
      smtp: SmtpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.smtp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
