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
 * Criteria class for the {@link com.mycompany.myapp.domain.Pays} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PaysResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaysCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idpays;

    private StringFilter codepays;

    private StringFilter designation;

    public PaysCriteria() {
    }

    public PaysCriteria(PaysCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idpays = other.idpays == null ? null : other.idpays.copy();
        this.codepays = other.codepays == null ? null : other.codepays.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
    }

    @Override
    public PaysCriteria copy() {
        return new PaysCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdpays() {
        return idpays;
    }

    public void setIdpays(IntegerFilter idpays) {
        this.idpays = idpays;
    }

    public StringFilter getCodepays() {
        return codepays;
    }

    public void setCodepays(StringFilter codepays) {
        this.codepays = codepays;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaysCriteria that = (PaysCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idpays, that.idpays) &&
            Objects.equals(codepays, that.codepays) &&
            Objects.equals(designation, that.designation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idpays,
        codepays,
        designation
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaysCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idpays != null ? "idpays=" + idpays + ", " : "") +
                (codepays != null ? "codepays=" + codepays + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
            "}";
    }

}
