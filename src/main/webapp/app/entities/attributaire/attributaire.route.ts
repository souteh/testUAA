import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAttributaire, Attributaire } from 'app/shared/model/attributaire.model';
import { AttributaireService } from './attributaire.service';
import { AttributaireComponent } from './attributaire.component';
import { AttributaireDetailComponent } from './attributaire-detail.component';
import { AttributaireUpdateComponent } from './attributaire-update.component';

@Injectable({ providedIn: 'root' })
export class AttributaireResolve implements Resolve<IAttributaire> {
  constructor(private service: AttributaireService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttributaire> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attributaire: HttpResponse<Attributaire>) => {
          if (attributaire.body) {
            return of(attributaire.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Attributaire());
  }
}

export const attributaireRoute: Routes = [
  {
    path: '',
    component: AttributaireComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.attributaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttributaireDetailComponent,
    resolve: {
      attributaire: AttributaireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.attributaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttributaireUpdateComponent,
    resolve: {
      attributaire: AttributaireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.attributaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttributaireUpdateComponent,
    resolve: {
      attributaire: AttributaireResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.attributaire.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
