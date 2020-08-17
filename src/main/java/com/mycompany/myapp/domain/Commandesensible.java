package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Commandesensible.
 */
@Entity
@Table(name = "commandesensible")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Commandesensible implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idcommandesensible", nullable = false)
    private Integer idcommandesensible;

    @Size(max = 254)
    @Column(name = "codecommandesensible", length = 254)
    private String codecommandesensible;

    @Size(max = 254)
    @Column(name = "libellecommandesensible", length = 254)
    private String libellecommandesensible;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdcommandesensible() {
        return idcommandesensible;
    }

    public Commandesensible idcommandesensible(Integer idcommandesensible) {
        this.idcommandesensible = idcommandesensible;
        return this;
    }

    public void setIdcommandesensible(Integer idcommandesensible) {
        this.idcommandesensible = idcommandesensible;
    }

    public String getCodecommandesensible() {
        return codecommandesensible;
    }

    public Commandesensible codecommandesensible(String codecommandesensible) {
        this.codecommandesensible = codecommandesensible;
        return this;
    }

    public void setCodecommandesensible(String codecommandesensible) {
        this.codecommandesensible = codecommandesensible;
    }

    public String getLibellecommandesensible() {
        return libellecommandesensible;
    }

    public Commandesensible libellecommandesensible(String libellecommandesensible) {
        this.libellecommandesensible = libellecommandesensible;
        return this;
    }

    public void setLibellecommandesensible(String libellecommandesensible) {
        this.libellecommandesensible = libellecommandesensible;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commandesensible)) {
            return false;
        }
        return id != null && id.equals(((Commandesensible) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commandesensible{" +
            "id=" + getId() +
            ", idcommandesensible=" + getIdcommandesensible() +
            ", codecommandesensible='" + getCodecommandesensible() + "'" +
            ", libellecommandesensible='" + getLibellecommandesensible() + "'" +
            "}";
    }
}
