import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agence',
        loadChildren: () => import('./agence/agence.module').then(m => m.TestUaaAgenceModule),
      },
      {
        path: 'alertemail',
        loadChildren: () => import('./alertemail/alertemail.module').then(m => m.TestUaaAlertemailModule),
      },
      {
        path: 'attributaire',
        loadChildren: () => import('./attributaire/attributaire.module').then(m => m.TestUaaAttributaireModule),
      },
      {
        path: 'choisit',
        loadChildren: () => import('./choisit/choisit.module').then(m => m.TestUaaChoisitModule),
      },
      {
        path: 'commandesensible',
        loadChildren: () => import('./commandesensible/commandesensible.module').then(m => m.TestUaaCommandesensibleModule),
      },
      {
        path: 'delaiannulation',
        loadChildren: () => import('./delaiannulation/delaiannulation.module').then(m => m.TestUaaDelaiannulationModule),
      },
      {
        path: 'hypodrome',
        loadChildren: () => import('./hypodrome/hypodrome.module').then(m => m.TestUaaHypodromeModule),
      },
      {
        path: 'i-18-n-text-translation',
        loadChildren: () =>
          import('./i-18-n-text-translation/i-18-n-text-translation.module').then(m => m.TestUaaI18nTextTranslationModule),
      },
      {
        path: 'paiementlots',
        loadChildren: () => import('./paiementlots/paiementlots.module').then(m => m.TestUaaPaiementlotsModule),
      },
      {
        path: 'pays',
        loadChildren: () => import('./pays/pays.module').then(m => m.TestUaaPaysModule),
      },
      {
        path: 'pdv',
        loadChildren: () => import('./pdv/pdv.module').then(m => m.TestUaaPdvModule),
      },
      {
        path: 'produit',
        loadChildren: () => import('./produit/produit.module').then(m => m.TestUaaProduitModule),
      },
      {
        path: 'smtp',
        loadChildren: () => import('./smtp/smtp.module').then(m => m.TestUaaSmtpModule),
      },
      {
        path: 'typepdv',
        loadChildren: () => import('./typepdv/typepdv.module').then(m => m.TestUaaTypepdvModule),
      },
      {
        path: 'typeterminal',
        loadChildren: () => import('./typeterminal/typeterminal.module').then(m => m.TestUaaTypeterminalModule),
      },
      {
        path: 'version',
        loadChildren: () => import('./version/version.module').then(m => m.TestUaaVersionModule),
      },
      {
        path: 'ville',
        loadChildren: () => import('./ville/ville.module').then(m => m.TestUaaVilleModule),
      },
      {
        path: 'voucher',
        loadChildren: () => import('./voucher/voucher.module').then(m => m.TestUaaVoucherModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TestUaaEntityModule {}
