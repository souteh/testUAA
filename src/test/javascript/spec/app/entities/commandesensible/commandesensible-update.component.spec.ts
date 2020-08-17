import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestUaaTestModule } from '../../../test.module';
import { CommandesensibleUpdateComponent } from 'app/entities/commandesensible/commandesensible-update.component';
import { CommandesensibleService } from 'app/entities/commandesensible/commandesensible.service';
import { Commandesensible } from 'app/shared/model/commandesensible.model';

describe('Component Tests', () => {
  describe('Commandesensible Management Update Component', () => {
    let comp: CommandesensibleUpdateComponent;
    let fixture: ComponentFixture<CommandesensibleUpdateComponent>;
    let service: CommandesensibleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestUaaTestModule],
        declarations: [CommandesensibleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommandesensibleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommandesensibleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommandesensibleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Commandesensible(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Commandesensible();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
