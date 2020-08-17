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
 * Criteria class for the {@link com.mycompany.myapp.domain.Produit} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProduitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProduitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idproduit;

    private StringFilter designation;

    private StringFilter libelle;

    private StringFilter codeproduit;

    private IntegerFilter enjeumin;

    private StringFilter ordre;

    private IntegerFilter nbrechevauxbase;

    private IntegerFilter nbreminpartant;

    private IntegerFilter champx;

    private IntegerFilter express;

    private StringFilter appartenance;

    private IntegerFilter enjeumax;

    private StringFilter statut;

    public ProduitCriteria() {
    }

    public ProduitCriteria(ProduitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idproduit = other.idproduit == null ? null : other.idproduit.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.codeproduit = other.codeproduit == null ? null : other.codeproduit.copy();
        this.enjeumin = other.enjeumin == null ? null : other.enjeumin.copy();
        this.ordre = other.ordre == null ? null : other.ordre.copy();
        this.nbrechevauxbase = other.nbrechevauxbase == null ? null : other.nbrechevauxbase.copy();
        this.nbreminpartant = other.nbreminpartant == null ? null : other.nbreminpartant.copy();
        this.champx = other.champx == null ? null : other.champx.copy();
        this.express = other.express == null ? null : other.express.copy();
        this.appartenance = other.appartenance == null ? null : other.appartenance.copy();
        this.enjeumax = other.enjeumax == null ? null : other.enjeumax.copy();
        this.statut = other.statut == null ? null : other.statut.copy();
    }

    @Override
    public ProduitCriteria copy() {
        return new ProduitCriteria(this);
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

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public StringFilter getCodeproduit() {
        return codeproduit;
    }

    public void setCodeproduit(StringFilter codeproduit) {
        this.codeproduit = codeproduit;
    }

    public IntegerFilter getEnjeumin() {
        return enjeumin;
    }

    public void setEnjeumin(IntegerFilter enjeumin) {
        this.enjeumin = enjeumin;
    }

    public StringFilter getOrdre() {
        return ordre;
    }

    public void setOrdre(StringFilter ordre) {
        this.ordre = ordre;
    }

    public IntegerFilter getNbrechevauxbase() {
        return nbrechevauxbase;
    }

    public void setNbrechevauxbase(IntegerFilter nbrechevauxbase) {
        this.nbrechevauxbase = nbrechevauxbase;
    }

    public IntegerFilter getNbreminpartant() {
        return nbreminpartant;
    }

    public void setNbreminpartant(IntegerFilter nbreminpartant) {
        this.nbreminpartant = nbreminpartant;
    }

    public IntegerFilter getChampx() {
        return champx;
    }

    public void setChampx(IntegerFilter champx) {
        this.champx = champx;
    }

    public IntegerFilter getExpress() {
        return express;
    }

    public void setExpress(IntegerFilter express) {
        this.express = express;
    }

    public StringFilter getAppartenance() {
        return appartenance;
    }

    public void setAppartenance(StringFilter appartenance) {
        this.appartenance = appartenance;
    }

    public IntegerFilter getEnjeumax() {
        return enjeumax;
    }

    public void setEnjeumax(IntegerFilter enjeumax) {
        this.enjeumax = enjeumax;
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
        final ProduitCriteria that = (ProduitCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idproduit, that.idproduit) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(codeproduit, that.codeproduit) &&
            Objects.equals(enjeumin, that.enjeumin) &&
            Objects.equals(ordre, that.ordre) &&
            Objects.equals(nbrechevauxbase, that.nbrechevauxbase) &&
            Objects.equals(nbreminpartant, that.nbreminpartant) &&
            Objects.equals(champx, that.champx) &&
            Objects.equals(express, that.express) &&
            Objects.equals(appartenance, that.appartenance) &&
            Objects.equals(enjeumax, that.enjeumax) &&
            Objects.equals(statut, that.statut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idproduit,
        designation,
        libelle,
        codeproduit,
        enjeumin,
        ordre,
        nbrechevauxbase,
        nbreminpartant,
        champx,
        express,
        appartenance,
        enjeumax,
        statut
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idproduit != null ? "idproduit=" + idproduit + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (codeproduit != null ? "codeproduit=" + codeproduit + ", " : "") +
                (enjeumin != null ? "enjeumin=" + enjeumin + ", " : "") +
                (ordre != null ? "ordre=" + ordre + ", " : "") +
                (nbrechevauxbase != null ? "nbrechevauxbase=" + nbrechevauxbase + ", " : "") +
                (nbreminpartant != null ? "nbreminpartant=" + nbreminpartant + ", " : "") +
                (champx != null ? "champx=" + champx + ", " : "") +
                (express != null ? "express=" + express + ", " : "") +
                (appartenance != null ? "appartenance=" + appartenance + ", " : "") +
                (enjeumax != null ? "enjeumax=" + enjeumax + ", " : "") +
                (statut != null ? "statut=" + statut + ", " : "") +
            "}";
    }

}
