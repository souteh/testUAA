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
 * Criteria class for the {@link com.mycompany.myapp.domain.Paiementlots} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PaiementlotsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /paiementlots?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaiementlotsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idlotpaiement;

    private StringFilter codepaiement;

    private StringFilter type;

    private IntegerFilter seuil;

    private IntegerFilter montantavance;

    private IntegerFilter delaipurge;

    private StringFilter lieuautorise;

    private StringFilter lieuannulation;

    public PaiementlotsCriteria() {
    }

    public PaiementlotsCriteria(PaiementlotsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idlotpaiement = other.idlotpaiement == null ? null : other.idlotpaiement.copy();
        this.codepaiement = other.codepaiement == null ? null : other.codepaiement.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.seuil = other.seuil == null ? null : other.seuil.copy();
        this.montantavance = other.montantavance == null ? null : other.montantavance.copy();
        this.delaipurge = other.delaipurge == null ? null : other.delaipurge.copy();
        this.lieuautorise = other.lieuautorise == null ? null : other.lieuautorise.copy();
        this.lieuannulation = other.lieuannulation == null ? null : other.lieuannulation.copy();
    }

    @Override
    public PaiementlotsCriteria copy() {
        return new PaiementlotsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdlotpaiement() {
        return idlotpaiement;
    }

    public void setIdlotpaiement(IntegerFilter idlotpaiement) {
        this.idlotpaiement = idlotpaiement;
    }

    public StringFilter getCodepaiement() {
        return codepaiement;
    }

    public void setCodepaiement(StringFilter codepaiement) {
        this.codepaiement = codepaiement;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public IntegerFilter getSeuil() {
        return seuil;
    }

    public void setSeuil(IntegerFilter seuil) {
        this.seuil = seuil;
    }

    public IntegerFilter getMontantavance() {
        return montantavance;
    }

    public void setMontantavance(IntegerFilter montantavance) {
        this.montantavance = montantavance;
    }

    public IntegerFilter getDelaipurge() {
        return delaipurge;
    }

    public void setDelaipurge(IntegerFilter delaipurge) {
        this.delaipurge = delaipurge;
    }

    public StringFilter getLieuautorise() {
        return lieuautorise;
    }

    public void setLieuautorise(StringFilter lieuautorise) {
        this.lieuautorise = lieuautorise;
    }

    public StringFilter getLieuannulation() {
        return lieuannulation;
    }

    public void setLieuannulation(StringFilter lieuannulation) {
        this.lieuannulation = lieuannulation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaiementlotsCriteria that = (PaiementlotsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idlotpaiement, that.idlotpaiement) &&
            Objects.equals(codepaiement, that.codepaiement) &&
            Objects.equals(type, that.type) &&
            Objects.equals(seuil, that.seuil) &&
            Objects.equals(montantavance, that.montantavance) &&
            Objects.equals(delaipurge, that.delaipurge) &&
            Objects.equals(lieuautorise, that.lieuautorise) &&
            Objects.equals(lieuannulation, that.lieuannulation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idlotpaiement,
        codepaiement,
        type,
        seuil,
        montantavance,
        delaipurge,
        lieuautorise,
        lieuannulation
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementlotsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idlotpaiement != null ? "idlotpaiement=" + idlotpaiement + ", " : "") +
                (codepaiement != null ? "codepaiement=" + codepaiement + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (seuil != null ? "seuil=" + seuil + ", " : "") +
                (montantavance != null ? "montantavance=" + montantavance + ", " : "") +
                (delaipurge != null ? "delaipurge=" + delaipurge + ", " : "") +
                (lieuautorise != null ? "lieuautorise=" + lieuautorise + ", " : "") +
                (lieuannulation != null ? "lieuannulation=" + lieuannulation + ", " : "") +
            "}";
    }

}
