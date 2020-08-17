import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { CommandesensibleDetailComponent } from 'app/entities/commandesensible/commandesensible-detail.component';
import { Commandesensible } from 'app/shared/model/commandesensible.model';

describe('Component Tests', () => {
  describe('Commandesensible Management Detail Component', () => {
    let comp: CommandesensibleDetailComponent;
    let fixture: ComponentFixture<CommandesensibleDetailComponent>;
    const route = ({ data: of({ commandesensible: new Commandesensible(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [CommandesensibleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommandesensibleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommandesensibleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load commandesensible on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commandesensible).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
