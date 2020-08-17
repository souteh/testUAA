import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHypodrome, Hypodrome } from 'app/shared/model/hypodrome.model';
import { HypodromeService } from './hypodrome.service';
import { HypodromeComponent } from './hypodrome.component';
import { HypodromeDetailComponent } from './hypodrome-detail.component';
import { HypodromeUpdateComponent } from './hypodrome-update.component';

@Injectable({ providedIn: 'root' })
export class HypodromeResolve implements Resolve<IHypodrome> {
  constructor(private service: HypodromeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHypodrome> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((hypodrome: HttpResponse<Hypodrome>) => {
          if (hypodrome.body) {
            return of(hypodrome.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Hypodrome());
  }
}

export const hypodromeRoute: Routes = [
  {
    path: '',
    component: HypodromeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.hypodrome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HypodromeDetailComponent,
    resolve: {
      hypodrome: HypodromeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.hypodrome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HypodromeUpdateComponent,
    resolve: {
      hypodrome: HypodromeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.hypodrome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HypodromeUpdateComponent,
    resolve: {
      hypodrome: HypodromeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.hypodrome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
