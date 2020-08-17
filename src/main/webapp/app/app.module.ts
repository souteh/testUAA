import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TestUaaSharedModule } from 'app/shared/shared.module';
import { TestUaaCoreModule } from 'app/core/core.module';
import { TestUaaAppRoutingModule } from './app-routing.module';
import { TestUaaHomeModule } from './home/home.module';
import { TestUaaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    TestUaaSharedModule,
    TestUaaCoreModule,
    TestUaaHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TestUaaEntityModule,
    TestUaaAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class TestUaaAppModule {}
