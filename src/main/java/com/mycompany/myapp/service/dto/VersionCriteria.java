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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Version} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.VersionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /versions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VersionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idversion;

    private StringFilter refversion;

    private StringFilter libelle;

    private InstantFilter date;

    private StringFilter fichier;

    private LongFilter idtypeterminalId;

    public VersionCriteria() {
    }

    public VersionCriteria(VersionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idversion = other.idversion == null ? null : other.idversion.copy();
        this.refversion = other.refversion == null ? null : other.refversion.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.fichier = other.fichier == null ? null : other.fichier.copy();
        this.idtypeterminalId = other.idtypeterminalId == null ? null : other.idtypeterminalId.copy();
    }

    @Override
    public VersionCriteria copy() {
        return new VersionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdversion() {
        return idversion;
    }

    public void setIdversion(IntegerFilter idversion) {
        this.idversion = idversion;
    }

    public StringFilter getRefversion() {
        return refversion;
    }

    public void setRefversion(StringFilter refversion) {
        this.refversion = refversion;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public StringFilter getFichier() {
        return fichier;
    }

    public void setFichier(StringFilter fichier) {
        this.fichier = fichier;
    }

    public LongFilter getIdtypeterminalId() {
        return idtypeterminalId;
    }

    public void setIdtypeterminalId(LongFilter idtypeterminalId) {
        this.idtypeterminalId = idtypeterminalId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VersionCriteria that = (VersionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idversion, that.idversion) &&
            Objects.equals(refversion, that.refversion) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(date, that.date) &&
            Objects.equals(fichier, that.fichier) &&
            Objects.equals(idtypeterminalId, that.idtypeterminalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idversion,
        refversion,
        libelle,
        date,
        fichier,
        idtypeterminalId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VersionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idversion != null ? "idversion=" + idversion + ", " : "") +
                (refversion != null ? "refversion=" + refversion + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (fichier != null ? "fichier=" + fichier + ", " : "") +
                (idtypeterminalId != null ? "idtypeterminalId=" + idtypeterminalId + ", " : "") +
            "}";
    }

}
