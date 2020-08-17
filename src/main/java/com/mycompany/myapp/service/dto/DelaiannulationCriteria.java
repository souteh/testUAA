package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Delaiannulation} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.DelaiannulationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /delaiannulations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DelaiannulationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter iddelaiannulation;

    private StringFilter codedelaiannulation;

    private StringFilter designationdelaiannulation;

    private IntegerFilter valeurdelaiannulation;

    private StringFilter statut;

    public DelaiannulationCriteria() {
    }

    public DelaiannulationCriteria(DelaiannulationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.iddelaiannulation = other.iddelaiannulation == null ? null : other.iddelaiannulation.copy();
        this.codedelaiannulation = other.codedelaiannulation == null ? null : other.codedelaiannulation.copy();
        this.designationdelaiannulation = other.designationdelaiannulation == null ? null : other.designationdelaiannulation.copy();
        this.valeurdelaiannulation = other.valeurdelaiannulation == null ? null : other.valeurdelaiannulation.copy();
        this.statut = other.statut == null ? null : other.statut.copy();
    }

    @Override
    public DelaiannulationCriteria copy() {
        return new DelaiannulationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIddelaiannulation() {
        return iddelaiannulation;
    }

    public void setIddelaiannulation(IntegerFilter iddelaiannulation) {
        this.iddelaiannulation = iddelaiannulation;
    }

    public StringFilter getCodedelaiannulation() {
        return codedelaiannulation;
    }

    public void setCodedelaiannulation(StringFilter codedelaiannulation) {
        this.codedelaiannulation = codedelaiannulation;
    }

    public StringFilter getDesignationdelaiannulation() {
        return designationdelaiannulation;
    }

    public void setDesignationdelaiannulation(StringFilter designationdelaiannulation) {
        this.designationdelaiannulation = designationdelaiannulation;
    }

    public IntegerFilter getValeurdelaiannulation() {
        return valeurdelaiannulation;
    }

    public void setValeurdelaiannulation(IntegerFilter valeurdelaiannulation) {
        this.valeurdelaiannulation = valeurdelaiannulation;
    }

    public StringFilter getStatut() {
        return statut;
    }

    public void setStatut(StringFilter statut) {
        this.statut = statut;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DelaiannulationCriteria that = (DelaiannulationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(iddelaiannulation, that.iddelaiannulation) &&
            Objects.equals(codedelaiannulation, that.codedelaiannulation) &&
            Objects.equals(designationdelaiannulation, that.designationdelaiannulation) &&
            Objects.equals(valeurdelaiannulation, that.valeurdelaiannulation) &&
            Objects.equals(statut, that.statut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        iddelaiannulation,
        codedelaiannulation,
        designationdelaiannulation,
        valeurdelaiannulation,
        statut
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DelaiannulationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (iddelaiannulation != null ? "iddelaiannulation=" + iddelaiannulation + ", " : "") +
                (codedelaiannulation != null ? "codedelaiannulation=" + codedelaiannulation + ", " : "") +
                (designationdelaiannulation != null ? "designationdelaiannulation=" + designationdelaiannulation + ", " : "") +
                (valeurdelaiannulation != null ? "valeurdelaiannulation=" + valeurdelaiannulation + ", " : "") +
                (statut != null ? "statut=" + statut + ", " : "") +
            "}";
    }

}
