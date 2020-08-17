import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPdv, Pdv } from 'app/shared/model/pdv.model';
import { PdvService } from './pdv.service';
import { PdvComponent } from './pdv.component';
import { PdvDetailComponent } from './pdv-detail.component';
import { PdvUpdateComponent } from './pdv-update.component';

@Injectable({ providedIn: 'root' })
export class PdvResolve implements Resolve<IPdv> {
  constructor(private service: PdvService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPdv> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pdv: HttpResponse<Pdv>) => {
          if (pdv.body) {
            return of(pdv.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pdv());
  }
}

export const pdvRoute: Routes = [
  {
    path: '',
    component: PdvComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.pdv.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PdvDetailComponent,
    resolve: {
      pdv: PdvResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.pdv.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PdvUpdateComponent,
    resolve: {
      pdv: PdvResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.pdv.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PdvUpdateComponent,
    resolve: {
      pdv: PdvResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.pdv.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
