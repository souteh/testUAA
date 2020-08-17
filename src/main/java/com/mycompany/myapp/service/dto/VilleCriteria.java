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
 * Criteria class for the {@link com.mycompany.myapp.domain.Ville} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.VilleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /villes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VilleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idville;

    private IntegerFilter designation;

    private StringFilter codeville;

    public VilleCriteria() {
    }

    public VilleCriteria(VilleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idville = other.idville == null ? null : other.idville.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.codeville = other.codeville == null ? null : other.codeville.copy();
    }

    @Override
    public VilleCriteria copy() {
        return new VilleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdville() {
        return idville;
    }

    public void setIdville(IntegerFilter idville) {
        this.idville = idville;
    }

    public IntegerFilter getDesignation() {
        return designation;
    }

    public void setDesignation(IntegerFilter designation) {
        this.designation = designation;
    }

    public StringFilter getCodeville() {
        return codeville;
    }

    public void setCodeville(StringFilter codeville) {
        this.codeville = codeville;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VilleCriteria that = (VilleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idville, that.idville) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(codeville, that.codeville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idville,
        designation,
        codeville
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VilleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idville != null ? "idville=" + idville + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (codeville != null ? "codeville=" + codeville + ", " : "") +
            "}";
    }

}
