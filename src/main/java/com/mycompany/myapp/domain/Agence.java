package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Agence.
 */
@Entity
@Table(name = "agence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idagence", nullable = false)
    private Integer idagence;

    @Size(max = 254)
    @Column(name = "codeagence", length = 254)
    private String codeagence;

    @Column(name = "numero")
    private Integer numero;

    @Size(max = 254)
    @Column(name = "nom", length = 254)
    private String nom;

    @Size(max = 254)
    @Column(name = "adresse", length = 254)
    private String adresse;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdagence() {
        return idagence;
    }

    public Agence idagence(Integer idagence) {
        this.idagence = idagence;
        return this;
    }

    public void setIdagence(Integer idagence) {
        this.idagence = idagence;
    }

    public String getCodeagence() {
        return codeagence;
    }

    public Agence codeagence(String codeagence) {
        this.codeagence = codeagence;
        return this;
    }

    public void setCodeagence(String codeagence) {
        this.codeagence = codeagence;
    }

    public Integer getNumero() {
        return numero;
    }

    public Agence numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public Agence nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Agence adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agence)) {
            return false;
        }
        return id != null && id.equals(((Agence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agence{" +
            "id=" + getId() +
            ", idagence=" + getIdagence() +
            ", codeagence='" + getCodeagence() + "'" +
            ", numero=" + getNumero() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
