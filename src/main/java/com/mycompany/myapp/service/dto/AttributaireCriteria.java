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
 * Criteria class for the {@link com.mycompany.myapp.domain.Attributaire} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AttributaireResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attributaires?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttributaireCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idattributaire;

    private StringFilter libelle;

    private StringFilter codeattributaire;

    public AttributaireCriteria() {
    }

    public AttributaireCriteria(AttributaireCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idattributaire = other.idattributaire == null ? null : other.idattributaire.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.codeattributaire = other.codeattributaire == null ? null : other.codeattributaire.copy();
    }

    @Override
    public AttributaireCriteria copy() {
        return new AttributaireCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdattributaire() {
        return idattributaire;
    }

    public void setIdattributaire(IntegerFilter idattributaire) {
        this.idattributaire = idattributaire;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public StringFilter getCodeattributaire() {
        return codeattributaire;
    }

    public void setCodeattributaire(StringFilter codeattributaire) {
        this.codeattributaire = codeattributaire;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttributaireCriteria that = (AttributaireCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idattributaire, that.idattributaire) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(codeattributaire, that.codeattributaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idattributaire,
        libelle,
        codeattributaire
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttributaireCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idattributaire != null ? "idattributaire=" + idattributaire + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (codeattributaire != null ? "codeattributaire=" + codeattributaire + ", " : "") +
            "}";
    }

}
