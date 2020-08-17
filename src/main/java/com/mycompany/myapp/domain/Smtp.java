package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Smtp.
 */
@Entity
@Table(name = "smtp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Smtp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idsmtp", nullable = false)
    private Integer idsmtp;

    @Size(max = 254)
    @Column(name = "codesmtp", length = 254)
    private String codesmtp;

    @Size(max = 254)
    @Column(name = "adresseipsmtp", length = 254)
    private String adresseipsmtp;

    @Size(max = 254)
    @Column(name = "authentification", length = 254)
    private String authentification;

    @Size(max = 254)
    @Column(name = "statut", length = 254)
    private String statut;

    @Size(max = 254)
    @Column(name = "login", length = 254)
    private String login;

    @Size(max = 254)
    @Column(name = "password", length = 254)
    private String password;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdsmtp() {
        return idsmtp;
    }

    public Smtp idsmtp(Integer idsmtp) {
        this.idsmtp = idsmtp;
        return this;
    }

    public void setIdsmtp(Integer idsmtp) {
        this.idsmtp = idsmtp;
    }

    public String getCodesmtp() {
        return codesmtp;
    }

    public Smtp codesmtp(String codesmtp) {
        this.codesmtp = codesmtp;
        return this;
    }

    public void setCodesmtp(String codesmtp) {
        this.codesmtp = codesmtp;
    }

    public String getAdresseipsmtp() {
        return adresseipsmtp;
    }

    public Smtp adresseipsmtp(String adresseipsmtp) {
        this.adresseipsmtp = adresseipsmtp;
        return this;
    }

    public void setAdresseipsmtp(String adresseipsmtp) {
        this.adresseipsmtp = adresseipsmtp;
    }

    public String getAuthentification() {
        return authentification;
    }

    public Smtp authentification(String authentification) {
        this.authentification = authentification;
        return this;
    }

    public void setAuthentification(String authentification) {
        this.authentification = authentification;
    }

    public String getStatut() {
        return statut;
    }

    public Smtp statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getLogin() {
        return login;
    }

    public Smtp login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Smtp password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Smtp)) {
            return false;
        }
        return id != null && id.equals(((Smtp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Smtp{" +
            "id=" + getId() +
            ", idsmtp=" + getIdsmtp() +
            ", codesmtp='" + getCodesmtp() + "'" +
            ", adresseipsmtp='" + getAdresseipsmtp() + "'" +
            ", authentification='" + getAuthentification() + "'" +
            ", statut='" + getStatut() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
