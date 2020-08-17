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
 * Criteria class for the {@link com.mycompany.myapp.domain.Choisit} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ChoisitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /choisits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChoisitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idproduit;

    private LongFilter idattributaireId;

    public ChoisitCriteria() {
    }

    public ChoisitCriteria(ChoisitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idproduit = other.idproduit == null ? null : other.idproduit.copy();
        this.idattributaireId = other.idattributaireId == null ? null : other.idattributaireId.copy();
    }

    @Override
    public ChoisitCriteria copy() {
        return new ChoisitCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(IntegerFilter idproduit) {
        this.idproduit = idproduit;
    }

    public LongFilter getIdattributaireId() {
        return idattributaireId;
    }

    public void setIdattributaireId(LongFilter idattributaireId) {
        this.idattributaireId = idattributaireId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChoisitCriteria that = (ChoisitCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idproduit, that.idproduit) &&
            Objects.equals(idattributaireId, that.idattributaireId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idproduit,
        idattributaireId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChoisitCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idproduit != null ? "idproduit=" + idproduit + ", " : "") +
                (idattributaireId != null ? "idattributaireId=" + idattributaireId + ", " : "") +
            "}";
    }

}
