import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypeterminal, Typeterminal } from 'app/shared/model/typeterminal.model';
import { TypeterminalService } from './typeterminal.service';
import { TypeterminalComponent } from './typeterminal.component';
import { TypeterminalDetailComponent } from './typeterminal-detail.component';
import { TypeterminalUpdateComponent } from './typeterminal-update.component';

@Injectable({ providedIn: 'root' })
export class TypeterminalResolve implements Resolve<ITypeterminal> {
  constructor(private service: TypeterminalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeterminal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typeterminal: HttpResponse<Typeterminal>) => {
          if (typeterminal.body) {
            return of(typeterminal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Typeterminal());
  }
}

export const typeterminalRoute: Routes = [
  {
    path: '',
    component: TypeterminalComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.typeterminal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeterminalDetailComponent,
    resolve: {
      typeterminal: TypeterminalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.typeterminal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeterminalUpdateComponent,
    resolve: {
      typeterminal: TypeterminalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.typeterminal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeterminalUpdateComponent,
    resolve: {
      typeterminal: TypeterminalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.typeterminal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
