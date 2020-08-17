import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlertemail, Alertemail } from 'app/shared/model/alertemail.model';
import { AlertemailService } from './alertemail.service';
import { AlertemailComponent } from './alertemail.component';
import { AlertemailDetailComponent } from './alertemail-detail.component';
import { AlertemailUpdateComponent } from './alertemail-update.component';

@Injectable({ providedIn: 'root' })
export class AlertemailResolve implements Resolve<IAlertemail> {
  constructor(private service: AlertemailService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlertemail> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alertemail: HttpResponse<Alertemail>) => {
          if (alertemail.body) {
            return of(alertemail.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Alertemail());
  }
}

export const alertemailRoute: Routes = [
  {
    path: '',
    component: AlertemailComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.alertemail.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AlertemailDetailComponent,
    resolve: {
      alertemail: AlertemailResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.alertemail.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AlertemailUpdateComponent,
    resolve: {
      alertemail: AlertemailResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.alertemail.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AlertemailUpdateComponent,
    resolve: {
      alertemail: AlertemailResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.alertemail.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
