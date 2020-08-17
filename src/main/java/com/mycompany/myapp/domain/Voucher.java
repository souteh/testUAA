package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Voucher.
 */
@Entity
@Table(name = "voucher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idvoucher", nullable = false)
    private Integer idvoucher;

    @Size(max = 254)
    @Column(name = "codevoucher", length = 254)
    private String codevoucher;

    @Size(max = 254)
    @Column(name = "statut", length = 254)
    private String statut;

    @Size(max = 254)
    @Column(name = "lieu", length = 254)
    private String lieu;

    @Column(name = "seuil")
    private Integer seuil;

    @Column(name = "delaipurge")
    private Integer delaipurge;

    @Column(name = "plafond")
    private Integer plafond;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdvoucher() {
        return idvoucher;
    }

    public Voucher idvoucher(Integer idvoucher) {
        this.idvoucher = idvoucher;
        return this;
    }

    public void setIdvoucher(Integer idvoucher) {
        this.idvoucher = idvoucher;
    }

    public String getCodevoucher() {
        return codevoucher;
    }

    public Voucher codevoucher(String codevoucher) {
        this.codevoucher = codevoucher;
        return this;
    }

    public void setCodevoucher(String codevoucher) {
        this.codevoucher = codevoucher;
    }

    public String getStatut() {
        return statut;
    }

    public Voucher statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getLieu() {
        return lieu;
    }

    public Voucher lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Integer getSeuil() {
        return seuil;
    }

    public Voucher seuil(Integer seuil) {
        this.seuil = seuil;
        return this;
    }

    public void setSeuil(Integer seuil) {
        this.seuil = seuil;
    }

    public Integer getDelaipurge() {
        return delaipurge;
    }

    public Voucher delaipurge(Integer delaipurge) {
        this.delaipurge = delaipurge;
        return this;
    }

    public void setDelaipurge(Integer delaipurge) {
        this.delaipurge = delaipurge;
    }

    public Integer getPlafond() {
        return plafond;
    }

    public Voucher plafond(Integer plafond) {
        this.plafond = plafond;
        return this;
    }

    public void setPlafond(Integer plafond) {
        this.plafond = plafond;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voucher)) {
            return false;
        }
        return id != null && id.equals(((Voucher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voucher{" +
            "id=" + getId() +
            ", idvoucher=" + getIdvoucher() +
            ", codevoucher='" + getCodevoucher() + "'" +
            ", statut='" + getStatut() + "'" +
            ", lieu='" + getLieu() + "'" +
            ", seuil=" + getSeuil() +
            ", delaipurge=" + getDelaipurge() +
            ", plafond=" + getPlafond() +
            "}";
    }
}
