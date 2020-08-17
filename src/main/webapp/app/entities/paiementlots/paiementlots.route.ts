import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaiementlots, Paiementlots } from 'app/shared/model/paiementlots.model';
import { PaiementlotsService } from './paiementlots.service';
import { PaiementlotsComponent } from './paiementlots.component';
import { PaiementlotsDetailComponent } from './paiementlots-detail.component';
import { PaiementlotsUpdateComponent } from './paiementlots-update.component';

@Injectable({ providedIn: 'root' })
export class PaiementlotsResolve implements Resolve<IPaiementlots> {
  constructor(private service: PaiementlotsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaiementlots> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paiementlots: HttpResponse<Paiementlots>) => {
          if (paiementlots.body) {
            return of(paiementlots.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Paiementlots());
  }
}

export const paiementlotsRoute: Routes = [
  {
    path: '',
    component: PaiementlotsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.paiementlots.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaiementlotsDetailComponent,
    resolve: {
      paiementlots: PaiementlotsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.paiementlots.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaiementlotsUpdateComponent,
    resolve: {
      paiementlots: PaiementlotsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.paiementlots.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaiementlotsUpdateComponent,
    resolve: {
      paiementlots: PaiementlotsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.paiementlots.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
