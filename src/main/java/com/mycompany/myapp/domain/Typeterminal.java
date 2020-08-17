package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Typeterminal.
 */
@Entity
@Table(name = "typeterminal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Typeterminal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idtypeterminal", nullable = false)
    private Integer idtypeterminal;

    @Size(max = 254)
    @Column(name = "codetypeterminal", length = 254)
    private String codetypeterminal;

    @Size(max = 254)
    @Column(name = "libelle", length = 254)
    private String libelle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdtypeterminal() {
        return idtypeterminal;
    }

    public Typeterminal idtypeterminal(Integer idtypeterminal) {
        this.idtypeterminal = idtypeterminal;
        return this;
    }

    public void setIdtypeterminal(Integer idtypeterminal) {
        this.idtypeterminal = idtypeterminal;
    }

    public String getCodetypeterminal() {
        return codetypeterminal;
    }

    public Typeterminal codetypeterminal(String codetypeterminal) {
        this.codetypeterminal = codetypeterminal;
        return this;
    }

    public void setCodetypeterminal(String codetypeterminal) {
        this.codetypeterminal = codetypeterminal;
    }

    public String getLibelle() {
        return libelle;
    }

    public Typeterminal libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Typeterminal)) {
            return false;
        }
        return id != null && id.equals(((Typeterminal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Typeterminal{" +
            "id=" + getId() +
            ", idtypeterminal=" + getIdtypeterminal() +
            ", codetypeterminal='" + getCodetypeterminal() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
