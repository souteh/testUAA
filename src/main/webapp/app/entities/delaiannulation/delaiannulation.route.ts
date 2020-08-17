import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDelaiannulation, Delaiannulation } from 'app/shared/model/delaiannulation.model';
import { DelaiannulationService } from './delaiannulation.service';
import { DelaiannulationComponent } from './delaiannulation.component';
import { DelaiannulationDetailComponent } from './delaiannulation-detail.component';
import { DelaiannulationUpdateComponent } from './delaiannulation-update.component';

@Injectable({ providedIn: 'root' })
export class DelaiannulationResolve implements Resolve<IDelaiannulation> {
  constructor(private service: DelaiannulationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDelaiannulation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((delaiannulation: HttpResponse<Delaiannulation>) => {
          if (delaiannulation.body) {
            return of(delaiannulation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Delaiannulation());
  }
}

export const delaiannulationRoute: Routes = [
  {
    path: '',
    component: DelaiannulationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.delaiannulation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DelaiannulationDetailComponent,
    resolve: {
      delaiannulation: DelaiannulationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.delaiannulation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DelaiannulationUpdateComponent,
    resolve: {
      delaiannulation: DelaiannulationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.delaiannulation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DelaiannulationUpdateComponent,
    resolve: {
      delaiannulation: DelaiannulationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.delaiannulation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
