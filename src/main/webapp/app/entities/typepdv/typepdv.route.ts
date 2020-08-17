import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypepdv, Typepdv } from 'app/shared/model/typepdv.model';
import { TypepdvService } from './typepdv.service';
import { TypepdvComponent } from './typepdv.component';
import { TypepdvDetailComponent } from './typepdv-detail.component';
import { TypepdvUpdateComponent } from './typepdv-update.component';

@Injectable({ providedIn: 'root' })
export class TypepdvResolve implements Resolve<ITypepdv> {
  constructor(private service: TypepdvService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypepdv> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typepdv: HttpResponse<Typepdv>) => {
          if (typepdv.body) {
            return of(typepdv.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Typepdv());
  }
}

export const typepdvRoute: Routes = [
  {
    path: '',
    component: TypepdvComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.typepdv.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypepdvDetailComponent,
    resolve: {
      typepdv: TypepdvResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.typepdv.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypepdvUpdateComponent,
    resolve: {
      typepdv: TypepdvResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.typepdv.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypepdvUpdateComponent,
    resolve: {
      typepdv: TypepdvResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.typepdv.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
