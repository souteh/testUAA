package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Paiementlots.
 */
@Entity
@Table(name = "paiementlots")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Paiementlots implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idlotpaiement", nullable = false)
    private Integer idlotpaiement;

    @Size(max = 254)
    @Column(name = "codepaiement", length = 254)
    private String codepaiement;

    @Size(max = 254)
    @Column(name = "type", length = 254)
    private String type;

    @Column(name = "seuil")
    private Integer seuil;

    @Column(name = "montantavance")
    private Integer montantavance;

    @Column(name = "delaipurge")
    private Integer delaipurge;

    @Size(max = 254)
    @Column(name = "lieuautorise", length = 254)
    private String lieuautorise;

    @Size(max = 254)
    @Column(name = "lieuannulation", length = 254)
    private String lieuannulation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdlotpaiement() {
        return idlotpaiement;
    }

    public Paiementlots idlotpaiement(Integer idlotpaiement) {
        this.idlotpaiement = idlotpaiement;
        return this;
    }

    public void setIdlotpaiement(Integer idlotpaiement) {
        this.idlotpaiement = idlotpaiement;
    }

    public String getCodepaiement() {
        return codepaiement;
    }

    public Paiementlots codepaiement(String codepaiement) {
        this.codepaiement = codepaiement;
        return this;
    }

    public void setCodepaiement(String codepaiement) {
        this.codepaiement = codepaiement;
    }

    public String getType() {
        return type;
    }

    public Paiementlots type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSeuil() {
        return seuil;
    }

    public Paiementlots seuil(Integer seuil) {
        this.seuil = seuil;
        return this;
    }

    public void setSeuil(Integer seuil) {
        this.seuil = seuil;
    }

    public Integer getMontantavance() {
        return montantavance;
    }

    public Paiementlots montantavance(Integer montantavance) {
        this.montantavance = montantavance;
        return this;
    }

    public void setMontantavance(Integer montantavance) {
        this.montantavance = montantavance;
    }

    public Integer getDelaipurge() {
        return delaipurge;
    }

    public Paiementlots delaipurge(Integer delaipurge) {
        this.delaipurge = delaipurge;
        return this;
    }

    public void setDelaipurge(Integer delaipurge) {
        this.delaipurge = delaipurge;
    }

    public String getLieuautorise() {
        return lieuautorise;
    }

    public Paiementlots lieuautorise(String lieuautorise) {
        this.lieuautorise = lieuautorise;
        return this;
    }

    public void setLieuautorise(String lieuautorise) {
        this.lieuautorise = lieuautorise;
    }

    public String getLieuannulation() {
        return lieuannulation;
    }

    public Paiementlots lieuannulation(String lieuannulation) {
        this.lieuannulation = lieuannulation;
        return this;
    }

    public void setLieuannulation(String lieuannulation) {
        this.lieuannulation = lieuannulation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiementlots)) {
            return false;
        }
        return id != null && id.equals(((Paiementlots) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paiementlots{" +
            "id=" + getId() +
            ", idlotpaiement=" + getIdlotpaiement() +
            ", codepaiement='" + getCodepaiement() + "'" +
            ", type='" + getType() + "'" +
            ", seuil=" + getSeuil() +
            ", montantavance=" + getMontantavance() +
            ", delaipurge=" + getDelaipurge() +
            ", lieuautorise='" + getLieuautorise() + "'" +
            ", lieuannulation='" + getLieuannulation() + "'" +
            "}";
    }
}
