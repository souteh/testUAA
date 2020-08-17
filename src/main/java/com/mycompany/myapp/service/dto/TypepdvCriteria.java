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
 * Criteria class for the {@link com.mycompany.myapp.domain.Typepdv} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TypepdvResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /typepdvs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypepdvCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idtypepdv;

    private StringFilter reftypepdv;

    private StringFilter type;

    private IntegerFilter nbremaxterminaux;

    private IntegerFilter plafondpostpaye;

    public TypepdvCriteria() {
    }

    public TypepdvCriteria(TypepdvCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idtypepdv = other.idtypepdv == null ? null : other.idtypepdv.copy();
        this.reftypepdv = other.reftypepdv == null ? null : other.reftypepdv.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.nbremaxterminaux = other.nbremaxterminaux == null ? null : other.nbremaxterminaux.copy();
        this.plafondpostpaye = other.plafondpostpaye == null ? null : other.plafondpostpaye.copy();
    }

    @Override
    public TypepdvCriteria copy() {
        return new TypepdvCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdtypepdv() {
        return idtypepdv;
    }

    public void setIdtypepdv(IntegerFilter idtypepdv) {
        this.idtypepdv = idtypepdv;
    }

    public StringFilter getReftypepdv() {
        return reftypepdv;
    }

    public void setReftypepdv(StringFilter reftypepdv) {
        this.reftypepdv = reftypepdv;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public IntegerFilter getNbremaxterminaux() {
        return nbremaxterminaux;
    }

    public void setNbremaxterminaux(IntegerFilter nbremaxterminaux) {
        this.nbremaxterminaux = nbremaxterminaux;
    }

    public IntegerFilter getPlafondpostpaye() {
        return plafondpostpaye;
    }

    public void setPlafondpostpaye(IntegerFilter plafondpostpaye) {
        this.plafondpostpaye = plafondpostpaye;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypepdvCriteria that = (TypepdvCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idtypepdv, that.idtypepdv) &&
            Objects.equals(reftypepdv, that.reftypepdv) &&
            Objects.equals(type, that.type) &&
            Objects.equals(nbremaxterminaux, that.nbremaxterminaux) &&
            Objects.equals(plafondpostpaye, that.plafondpostpaye);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idtypepdv,
        reftypepdv,
        type,
        nbremaxterminaux,
        plafondpostpaye
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypepdvCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idtypepdv != null ? "idtypepdv=" + idtypepdv + ", " : "") +
                (reftypepdv != null ? "reftypepdv=" + reftypepdv + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (nbremaxterminaux != null ? "nbremaxterminaux=" + nbremaxterminaux + ", " : "") +
                (plafondpostpaye != null ? "plafondpostpaye=" + plafondpostpaye + ", " : "") +
            "}";
    }

}
