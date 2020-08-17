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
 * Criteria class for the {@link com.mycompany.myapp.domain.Hypodrome} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.HypodromeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hypodromes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HypodromeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idhypodrome;

    private IntegerFilter codehypodrome;

    private StringFilter abreviation;

    private StringFilter pays;

    public HypodromeCriteria() {
    }

    public HypodromeCriteria(HypodromeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idhypodrome = other.idhypodrome == null ? null : other.idhypodrome.copy();
        this.codehypodrome = other.codehypodrome == null ? null : other.codehypodrome.copy();
        this.abreviation = other.abreviation == null ? null : other.abreviation.copy();
        this.pays = other.pays == null ? null : other.pays.copy();
    }

    @Override
    public HypodromeCriteria copy() {
        return new HypodromeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdhypodrome() {
        return idhypodrome;
    }

    public void setIdhypodrome(IntegerFilter idhypodrome) {
        this.idhypodrome = idhypodrome;
    }

    public IntegerFilter getCodehypodrome() {
        return codehypodrome;
    }

    public void setCodehypodrome(IntegerFilter codehypodrome) {
        this.codehypodrome = codehypodrome;
    }

    public StringFilter getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(StringFilter abreviation) {
        this.abreviation = abreviation;
    }

    public StringFilter getPays() {
        return pays;
    }

    public void setPays(StringFilter pays) {
        this.pays = pays;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HypodromeCriteria that = (HypodromeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idhypodrome, that.idhypodrome) &&
            Objects.equals(codehypodrome, that.codehypodrome) &&
            Objects.equals(abreviation, that.abreviation) &&
            Objects.equals(pays, that.pays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idhypodrome,
        codehypodrome,
        abreviation,
        pays
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HypodromeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idhypodrome != null ? "idhypodrome=" + idhypodrome + ", " : "") +
                (codehypodrome != null ? "codehypodrome=" + codehypodrome + ", " : "") +
                (abreviation != null ? "abreviation=" + abreviation + ", " : "") +
                (pays != null ? "pays=" + pays + ", " : "") +
            "}";
    }

}
