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
 * Criteria class for the {@link com.mycompany.myapp.domain.Alertemail} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AlertemailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alertemails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AlertemailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idalertemail;

    private StringFilter codealertemail;

    private StringFilter typealertemail;

    private StringFilter objetalertemail;

    private StringFilter adressemaildiffusion;

    public AlertemailCriteria() {
    }

    public AlertemailCriteria(AlertemailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idalertemail = other.idalertemail == null ? null : other.idalertemail.copy();
        this.codealertemail = other.codealertemail == null ? null : other.codealertemail.copy();
        this.typealertemail = other.typealertemail == null ? null : other.typealertemail.copy();
        this.objetalertemail = other.objetalertemail == null ? null : other.objetalertemail.copy();
        this.adressemaildiffusion = other.adressemaildiffusion == null ? null : other.adressemaildiffusion.copy();
    }

    @Override
    public AlertemailCriteria copy() {
        return new AlertemailCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdalertemail() {
        return idalertemail;
    }

    public void setIdalertemail(IntegerFilter idalertemail) {
        this.idalertemail = idalertemail;
    }

    public StringFilter getCodealertemail() {
        return codealertemail;
    }

    public void setCodealertemail(StringFilter codealertemail) {
        this.codealertemail = codealertemail;
    }

    public StringFilter getTypealertemail() {
        return typealertemail;
    }

    public void setTypealertemail(StringFilter typealertemail) {
        this.typealertemail = typealertemail;
    }

    public StringFilter getObjetalertemail() {
        return objetalertemail;
    }

    public void setObjetalertemail(StringFilter objetalertemail) {
        this.objetalertemail = objetalertemail;
    }

    public StringFilter getAdressemaildiffusion() {
        return adressemaildiffusion;
    }

    public void setAdressemaildiffusion(StringFilter adressemaildiffusion) {
        this.adressemaildiffusion = adressemaildiffusion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlertemailCriteria that = (AlertemailCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idalertemail, that.idalertemail) &&
            Objects.equals(codealertemail, that.codealertemail) &&
            Objects.equals(typealertemail, that.typealertemail) &&
            Objects.equals(objetalertemail, that.objetalertemail) &&
            Objects.equals(adressemaildiffusion, that.adressemaildiffusion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idalertemail,
        codealertemail,
        typealertemail,
        objetalertemail,
        adressemaildiffusion
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlertemailCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idalertemail != null ? "idalertemail=" + idalertemail + ", " : "") +
                (codealertemail != null ? "codealertemail=" + codealertemail + ", " : "") +
                (typealertemail != null ? "typealertemail=" + typealertemail + ", " : "") +
                (objetalertemail != null ? "objetalertemail=" + objetalertemail + ", " : "") +
                (adressemaildiffusion != null ? "adressemaildiffusion=" + adressemaildiffusion + ", " : "") +
            "}";
    }

}
