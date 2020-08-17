import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IChoisit, Choisit } from 'app/shared/model/choisit.model';
import { ChoisitService } from './choisit.service';
import { ChoisitComponent } from './choisit.component';
import { ChoisitDetailComponent } from './choisit-detail.component';
import { ChoisitUpdateComponent } from './choisit-update.component';

@Injectable({ providedIn: 'root' })
export class ChoisitResolve implements Resolve<IChoisit> {
  constructor(private service: ChoisitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IChoisit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((choisit: HttpResponse<Choisit>) => {
          if (choisit.body) {
            return of(choisit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Choisit());
  }
}

export const choisitRoute: Routes = [
  {
    path: '',
    component: ChoisitComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.choisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChoisitDetailComponent,
    resolve: {
      choisit: ChoisitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.choisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChoisitUpdateComponent,
    resolve: {
      choisit: ChoisitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.choisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChoisitUpdateComponent,
    resolve: {
      choisit: ChoisitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.choisit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
