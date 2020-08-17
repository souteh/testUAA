package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Pdv.
 */
@Entity
@Table(name = "pdv")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pdv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Size(max = 64)
    @Column(name = "agence", length = 64)
    private String agence;

    @Size(max = 64)
    @Column(name = "designation", length = 64)
    private String designation;

    @Size(max = 64)
    @Column(name = "ville", length = 64)
    private String ville;

    @Size(max = 64)
    @Column(name = "statut", length = 64)
    private String statut;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Pdv numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getAgence() {
        return agence;
    }

    public Pdv agence(String agence) {
        this.agence = agence;
        return this;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getDesignation() {
        return designation;
    }

    public Pdv designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getVille() {
        return ville;
    }

    public Pdv ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getStatut() {
        return statut;
    }

    public Pdv statut(String statut) {
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
        if (!(o instanceof Pdv)) {
            return false;
        }
        return id != null && id.equals(((Pdv) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pdv{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", agence='" + getAgence() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", ville='" + getVille() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
