import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAgence, Agence } from 'app/shared/model/agence.model';
import { AgenceService } from './agence.service';
import { AgenceComponent } from './agence.component';
import { AgenceDetailComponent } from './agence-detail.component';
import { AgenceUpdateComponent } from './agence-update.component';

@Injectable({ providedIn: 'root' })
export class AgenceResolve implements Resolve<IAgence> {
  constructor(private service: AgenceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAgence> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((agence: HttpResponse<Agence>) => {
          if (agence.body) {
            return of(agence.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Agence());
  }
}

export const agenceRoute: Routes = [
  {
    path: '',
    component: AgenceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.agence.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgenceDetailComponent,
    resolve: {
      agence: AgenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.agence.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgenceUpdateComponent,
    resolve: {
      agence: AgenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.agence.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgenceUpdateComponent,
    resolve: {
      agence: AgenceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.agence.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
