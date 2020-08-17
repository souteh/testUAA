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
 * Criteria class for the {@link com.mycompany.myapp.domain.Commandesensible} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CommandesensibleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /commandesensibles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommandesensibleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idcommandesensible;

    private StringFilter codecommandesensible;

    private StringFilter libellecommandesensible;

    public CommandesensibleCriteria() {
    }

    public CommandesensibleCriteria(CommandesensibleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idcommandesensible = other.idcommandesensible == null ? null : other.idcommandesensible.copy();
        this.codecommandesensible = other.codecommandesensible == null ? null : other.codecommandesensible.copy();
        this.libellecommandesensible = other.libellecommandesensible == null ? null : other.libellecommandesensible.copy();
    }

    @Override
    public CommandesensibleCriteria copy() {
        return new CommandesensibleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdcommandesensible() {
        return idcommandesensible;
    }

    public void setIdcommandesensible(IntegerFilter idcommandesensible) {
        this.idcommandesensible = idcommandesensible;
    }

    public StringFilter getCodecommandesensible() {
        return codecommandesensible;
    }

    public void setCodecommandesensible(StringFilter codecommandesensible) {
        this.codecommandesensible = codecommandesensible;
    }

    public StringFilter getLibellecommandesensible() {
        return libellecommandesensible;
    }

    public void setLibellecommandesensible(StringFilter libellecommandesensible) {
        this.libellecommandesensible = libellecommandesensible;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommandesensibleCriteria that = (CommandesensibleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idcommandesensible, that.idcommandesensible) &&
            Objects.equals(codecommandesensible, that.codecommandesensible) &&
            Objects.equals(libellecommandesensible, that.libellecommandesensible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idcommandesensible,
        codecommandesensible,
        libellecommandesensible
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandesensibleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idcommandesensible != null ? "idcommandesensible=" + idcommandesensible + ", " : "") +
                (codecommandesensible != null ? "codecommandesensible=" + codecommandesensible + ", " : "") +
                (libellecommandesensible != null ? "libellecommandesensible=" + libellecommandesensible + ", " : "") +
            "}";
    }

}
