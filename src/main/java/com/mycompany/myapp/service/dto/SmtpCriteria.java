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
 * Criteria class for the {@link com.mycompany.myapp.domain.Smtp} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.SmtpResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /smtps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SmtpCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter idsmtp;

    private StringFilter codesmtp;

    private StringFilter adresseipsmtp;

    private StringFilter authentification;

    private StringFilter statut;

    private StringFilter login;

    private StringFilter password;

    public SmtpCriteria() {
    }

    public SmtpCriteria(SmtpCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idsmtp = other.idsmtp == null ? null : other.idsmtp.copy();
        this.codesmtp = other.codesmtp == null ? null : other.codesmtp.copy();
        this.adresseipsmtp = other.adresseipsmtp == null ? null : other.adresseipsmtp.copy();
        this.authentification = other.authentification == null ? null : other.authentification.copy();
        this.statut = other.statut == null ? null : other.statut.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.password = other.password == null ? null : other.password.copy();
    }

    @Override
    public SmtpCriteria copy() {
        return new SmtpCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getIdsmtp() {
        return idsmtp;
    }

    public void setIdsmtp(IntegerFilter idsmtp) {
        this.idsmtp = idsmtp;
    }

    public StringFilter getCodesmtp() {
        return codesmtp;
    }

    public void setCodesmtp(StringFilter codesmtp) {
        this.codesmtp = codesmtp;
    }

    public StringFilter getAdresseipsmtp() {
        return adresseipsmtp;
    }

    public void setAdresseipsmtp(StringFilter adresseipsmtp) {
        this.adresseipsmtp = adresseipsmtp;
    }

    public StringFilter getAuthentification() {
        return authentification;
    }

    public void setAuthentification(StringFilter authentification) {
        this.authentification = authentification;
    }

    public StringFilter getStatut() {
        return statut;
    }

    public void setStatut(StringFilter statut) {
        this.statut = statut;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SmtpCriteria that = (SmtpCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idsmtp, that.idsmtp) &&
            Objects.equals(codesmtp, that.codesmtp) &&
            Objects.equals(adresseipsmtp, that.adresseipsmtp) &&
            Objects.equals(authentification, that.authentification) &&
            Objects.equals(statut, that.statut) &&
            Objects.equals(login, that.login) &&
            Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idsmtp,
        codesmtp,
        adresseipsmtp,
        authentification,
        statut,
        login,
        password
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SmtpCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idsmtp != null ? "idsmtp=" + idsmtp + ", " : "") +
                (codesmtp != null ? "codesmtp=" + codesmtp + ", " : "") +
                (adresseipsmtp != null ? "adresseipsmtp=" + adresseipsmtp + ", " : "") +
                (authentification != null ? "authentification=" + authentification + ", " : "") +
                (statut != null ? "statut=" + statut + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
            "}";
    }

}
