import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommandesensible, Commandesensible } from 'app/shared/model/commandesensible.model';
import { CommandesensibleService } from './commandesensible.service';
import { CommandesensibleComponent } from './commandesensible.component';
import { CommandesensibleDetailComponent } from './commandesensible-detail.component';
import { CommandesensibleUpdateComponent } from './commandesensible-update.component';

@Injectable({ providedIn: 'root' })
export class CommandesensibleResolve implements Resolve<ICommandesensible> {
  constructor(private service: CommandesensibleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommandesensible> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commandesensible: HttpResponse<Commandesensible>) => {
          if (commandesensible.body) {
            return of(commandesensible.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Commandesensible());
  }
}

export const commandesensibleRoute: Routes = [
  {
    path: '',
    component: CommandesensibleComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.commandesensible.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommandesensibleDetailComponent,
    resolve: {
      commandesensible: CommandesensibleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.commandesensible.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommandesensibleUpdateComponent,
    resolve: {
      commandesensible: CommandesensibleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.commandesensible.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommandesensibleUpdateComponent,
    resolve: {
      commandesensible: CommandesensibleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.commandesensible.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
