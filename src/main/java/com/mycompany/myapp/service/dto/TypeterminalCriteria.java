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
 * Criteria class for the {@link com.mycompany.myapp.domain.Typeterminal} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TypeterminalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /typeterminals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TypeterminalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idtypeterminal;

    private StringFilter codetypeterminal;

    private StringFilter libelle;

    public TypeterminalCriteria() {
    }

    public TypeterminalCriteria(TypeterminalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idtypeterminal = other.idtypeterminal == null ? null : other.idtypeterminal.copy();
        this.codetypeterminal = other.codetypeterminal == null ? null : other.codetypeterminal.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
    }

    @Override
    public TypeterminalCriteria copy() {
        return new TypeterminalCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdtypeterminal() {
        return idtypeterminal;
    }

    public void setIdtypeterminal(IntegerFilter idtypeterminal) {
        this.idtypeterminal = idtypeterminal;
    }

    public StringFilter getCodetypeterminal() {
        return codetypeterminal;
    }

    public void setCodetypeterminal(StringFilter codetypeterminal) {
        this.codetypeterminal = codetypeterminal;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TypeterminalCriteria that = (TypeterminalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idtypeterminal, that.idtypeterminal) &&
            Objects.equals(codetypeterminal, that.codetypeterminal) &&
            Objects.equals(libelle, that.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idtypeterminal,
        codetypeterminal,
        libelle
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeterminalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idtypeterminal != null ? "idtypeterminal=" + idtypeterminal + ", " : "") +
                (codetypeterminal != null ? "codetypeterminal=" + codetypeterminal + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
            "}";
    }

}
