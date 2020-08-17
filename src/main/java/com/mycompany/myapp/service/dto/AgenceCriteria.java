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
 * Criteria class for the {@link com.mycompany.myapp.domain.Agence} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AgenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /agences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AgenceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idagence;

    private StringFilter codeagence;

    private IntegerFilter numero;

    private StringFilter nom;

    private StringFilter adresse;

    public AgenceCriteria() {
    }

    public AgenceCriteria(AgenceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idagence = other.idagence == null ? null : other.idagence.copy();
        this.codeagence = other.codeagence == null ? null : other.codeagence.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
    }

    @Override
    public AgenceCriteria copy() {
        return new AgenceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdagence() {
        return idagence;
    }

    public void setIdagence(IntegerFilter idagence) {
        this.idagence = idagence;
    }

    public StringFilter getCodeagence() {
        return codeagence;
    }

    public void setCodeagence(StringFilter codeagence) {
        this.codeagence = codeagence;
    }

    public IntegerFilter getNumero() {
        return numero;
    }

    public void setNumero(IntegerFilter numero) {
        this.numero = numero;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AgenceCriteria that = (AgenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idagence, that.idagence) &&
            Objects.equals(codeagence, that.codeagence) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(adresse, that.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idagence,
        codeagence,
        numero,
        nom,
        adresse
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idagence != null ? "idagence=" + idagence + ", " : "") +
                (codeagence != null ? "codeagence=" + codeagence + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
            "}";
    }

}
