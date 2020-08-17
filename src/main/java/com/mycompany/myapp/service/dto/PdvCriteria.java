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
 * Criteria class for the {@link com.mycompany.myapp.domain.Pdv} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PdvResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pdvs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PdvCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter numero;

    private StringFilter agence;

    private StringFilter designation;

    private StringFilter ville;

    private StringFilter statut;

    public PdvCriteria() {
    }

    public PdvCriteria(PdvCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.agence = other.agence == null ? null : other.agence.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.ville = other.ville == null ? null : other.ville.copy();
        this.statut = other.statut == null ? null : other.statut.copy();
    }

    @Override
    public PdvCriteria copy() {
        return new PdvCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNumero() {
        return numero;
    }

    public void setNumero(IntegerFilter numero) {
        this.numero = numero;
    }

    public StringFilter getAgence() {
        return agence;
    }

    public void setAgence(StringFilter agence) {
        this.agence = agence;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public StringFilter getVille() {
        return ville;
    }

    public void setVille(StringFilter ville) {
        this.ville = ville;
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
        final PdvCriteria that = (PdvCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(agence, that.agence) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(ville, that.ville) &&
            Objects.equals(statut, that.statut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numero,
        agence,
        designation,
        ville,
        statut
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PdvCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (agence != null ? "agence=" + agence + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (ville != null ? "ville=" + ville + ", " : "") +
                (statut != null ? "statut=" + statut + ", " : "") +
            "}";
    }

}
