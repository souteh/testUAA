import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { II18nTextTranslation, I18nTextTranslation } from 'app/shared/model/i-18-n-text-translation.model';
import { I18nTextTranslationService } from './i-18-n-text-translation.service';
import { I18nTextTranslationComponent } from './i-18-n-text-translation.component';
import { I18nTextTranslationDetailComponent } from './i-18-n-text-translation-detail.component';
import { I18nTextTranslationUpdateComponent } from './i-18-n-text-translation-update.component';

@Injectable({ providedIn: 'root' })
export class I18nTextTranslationResolve implements Resolve<II18nTextTranslation> {
  constructor(private service: I18nTextTranslationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<II18nTextTranslation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((i18nTextTranslation: HttpResponse<I18nTextTranslation>) => {
          if (i18nTextTranslation.body) {
            return of(i18nTextTranslation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new I18nTextTranslation());
  }
}

export const i18nTextTranslationRoute: Routes = [
  {
    path: '',
    component: I18nTextTranslationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'testUaaApp.i18nTextTranslation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: I18nTextTranslationDetailComponent,
    resolve: {
      i18nTextTranslation: I18nTextTranslationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.i18nTextTranslation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: I18nTextTranslationUpdateComponent,
    resolve: {
      i18nTextTranslation: I18nTextTranslationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.i18nTextTranslation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: I18nTextTranslationUpdateComponent,
    resolve: {
      i18nTextTranslation: I18nTextTranslationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testUaaApp.i18nTextTranslation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
