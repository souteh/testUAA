package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idproduit", nullable = false)
    private Integer idproduit;

    @Size(max = 254)
    @Column(name = "designation", length = 254)
    private String designation;

    @Size(max = 254)
    @Column(name = "libelle", length = 254)
    private String libelle;

    @Size(max = 254)
    @Column(name = "codeproduit", length = 254)
    private String codeproduit;

    @Column(name = "enjeumin")
    private Integer enjeumin;

    @Size(max = 254)
    @Column(name = "ordre", length = 254)
    private String ordre;

    @Column(name = "nbrechevauxbase")
    private Integer nbrechevauxbase;

    @Column(name = "nbreminpartant")
    private Integer nbreminpartant;

    @Column(name = "champx")
    private Integer champx;

    @Column(name = "express")
    private Integer express;

    @Size(max = 254)
    @Column(name = "appartenance", length = 254)
    private String appartenance;

    @Column(name = "enjeumax")
    private Integer enjeumax;

    @Size(max = 254)
    @Column(name = "statut", length = 254)
    private String statut;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdproduit() {
        return idproduit;
    }

    public Produit idproduit(Integer idproduit) {
        this.idproduit = idproduit;
        return this;
    }

    public void setIdproduit(Integer idproduit) {
        this.idproduit = idproduit;
    }

    public String getDesignation() {
        return designation;
    }

    public Produit designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLibelle() {
        return libelle;
    }

    public Produit libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCodeproduit() {
        return codeproduit;
    }

    public Produit codeproduit(String codeproduit) {
        this.codeproduit = codeproduit;
        return this;
    }

    public void setCodeproduit(String codeproduit) {
        this.codeproduit = codeproduit;
    }

    public Integer getEnjeumin() {
        return enjeumin;
    }

    public Produit enjeumin(Integer enjeumin) {
        this.enjeumin = enjeumin;
        return this;
    }

    public void setEnjeumin(Integer enjeumin) {
        this.enjeumin = enjeumin;
    }

    public String getOrdre() {
        return ordre;
    }

    public Produit ordre(String ordre) {
        this.ordre = ordre;
        return this;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }

    public Integer getNbrechevauxbase() {
        return nbrechevauxbase;
    }

    public Produit nbrechevauxbase(Integer nbrechevauxbase) {
        this.nbrechevauxbase = nbrechevauxbase;
        return this;
    }

    public void setNbrechevauxbase(Integer nbrechevauxbase) {
        this.nbrechevauxbase = nbrechevauxbase;
    }

    public Integer getNbreminpartant() {
        return nbreminpartant;
    }

    public Produit nbreminpartant(Integer nbreminpartant) {
        this.nbreminpartant = nbreminpartant;
        return this;
    }

    public void setNbreminpartant(Integer nbreminpartant) {
        this.nbreminpartant = nbreminpartant;
    }

    public Integer getChampx() {
        return champx;
    }

    public Produit champx(Integer champx) {
        this.champx = champx;
        return this;
    }

    public void setChampx(Integer champx) {
        this.champx = champx;
    }

    public Integer getExpress() {
        return express;
    }

    public Produit express(Integer express) {
        this.express = express;
        return this;
    }

    public void setExpress(Integer express) {
        this.express = express;
    }

    public String getAppartenance() {
        return appartenance;
    }

    public Produit appartenance(String appartenance) {
        this.appartenance = appartenance;
        return this;
    }

    public void setAppartenance(String appartenance) {
        this.appartenance = appartenance;
    }

    public Integer getEnjeumax() {
        return enjeumax;
    }

    public Produit enjeumax(Integer enjeumax) {
        this.enjeumax = enjeumax;
        return this;
    }

    public void setEnjeumax(Integer enjeumax) {
        this.enjeumax = enjeumax;
    }

    public String getStatut() {
        return statut;
    }

    public Produit statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", idproduit=" + getIdproduit() +
            ", designation='" + getDesignation() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", codeproduit='" + getCodeproduit() + "'" +
            ", enjeumin=" + getEnjeumin() +
            ", ordre='" + getOrdre() + "'" +
            ", nbrechevauxbase=" + getNbrechevauxbase() +
            ", nbreminpartant=" + getNbreminpartant() +
            ", champx=" + getChampx() +
            ", express=" + getExpress() +
            ", appartenance='" + getAppartenance() + "'" +
            ", enjeumax=" + getEnjeumax() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
