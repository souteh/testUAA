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
 * Criteria class for the {@link com.mycompany.myapp.domain.Voucher} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.VoucherResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vouchers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VoucherCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idvoucher;

    private StringFilter codevoucher;

    private StringFilter statut;

    private StringFilter lieu;

    private IntegerFilter seuil;

    private IntegerFilter delaipurge;

    private IntegerFilter plafond;

    public VoucherCriteria() {
    }

    public VoucherCriteria(VoucherCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idvoucher = other.idvoucher == null ? null : other.idvoucher.copy();
        this.codevoucher = other.codevoucher == null ? null : other.codevoucher.copy();
        this.statut = other.statut == null ? null : other.statut.copy();
        this.lieu = other.lieu == null ? null : other.lieu.copy();
        this.seuil = other.seuil == null ? null : other.seuil.copy();
        this.delaipurge = other.delaipurge == null ? null : other.delaipurge.copy();
        this.plafond = other.plafond == null ? null : other.plafond.copy();
    }

    @Override
    public VoucherCriteria copy() {
        return new VoucherCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdvoucher() {
        return idvoucher;
    }

    public void setIdvoucher(IntegerFilter idvoucher) {
        this.idvoucher = idvoucher;
    }

    public StringFilter getCodevoucher() {
        return codevoucher;
    }

    public void setCodevoucher(StringFilter codevoucher) {
        this.codevoucher = codevoucher;
    }

    public StringFilter getStatut() {
        return statut;
    }

    public void setStatut(StringFilter statut) {
        this.statut = statut;
    }

    public StringFilter getLieu() {
        return lieu;
    }

    public void setLieu(StringFilter lieu) {
        this.lieu = lieu;
    }

    public IntegerFilter getSeuil() {
        return seuil;
    }

    public void setSeuil(IntegerFilter seuil) {
        this.seuil = seuil;
    }

    public IntegerFilter getDelaipurge() {
        return delaipurge;
    }

    public void setDelaipurge(IntegerFilter delaipurge) {
        this.delaipurge = delaipurge;
    }

    public IntegerFilter getPlafond() {
        return plafond;
    }

    public void setPlafond(IntegerFilter plafond) {
        this.plafond = plafond;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VoucherCriteria that = (VoucherCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idvoucher, that.idvoucher) &&
            Objects.equals(codevoucher, that.codevoucher) &&
            Objects.equals(statut, that.statut) &&
            Objects.equals(lieu, that.lieu) &&
            Objects.equals(seuil, that.seuil) &&
            Objects.equals(delaipurge, that.delaipurge) &&
            Objects.equals(plafond, that.plafond);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idvoucher,
        codevoucher,
        statut,
        lieu,
        seuil,
        delaipurge,
        plafond
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idvoucher != null ? "idvoucher=" + idvoucher + ", " : "") +
                (codevoucher != null ? "codevoucher=" + codevoucher + ", " : "") +
                (statut != null ? "statut=" + statut + ", " : "") +
                (lieu != null ? "lieu=" + lieu + ", " : "") +
                (seuil != null ? "seuil=" + seuil + ", " : "") +
                (delaipurge != null ? "delaipurge=" + delaipurge + ", " : "") +
                (plafond != null ? "plafond=" + plafond + ", " : "") +
            "}";
    }

}
