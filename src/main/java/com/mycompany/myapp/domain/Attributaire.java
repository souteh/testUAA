package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Attributaire.
 */
@Entity
@Table(name = "attributaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attributaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idattributaire", nullable = false)
    private Integer idattributaire;

    @Size(max = 254)
    @Column(name = "libelle", length = 254)
    private String libelle;

    @Size(max = 254)
    @Column(name = "codeattributaire", length = 254)
    private String codeattributaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdattributaire() {
        return idattributaire;
    }

    public Attributaire idattributaire(Integer idattributaire) {
        this.idattributaire = idattributaire;
        return this;
    }

    public void setIdattributaire(Integer idattributaire) {
        this.idattributaire = idattributaire;
    }

    public String getLibelle() {
        return libelle;
    }

    public Attributaire libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCodeattributaire() {
        return codeattributaire;
    }

    public Attributaire codeattributaire(String codeattributaire) {
        this.codeattributaire = codeattributaire;
        return this;
    }

    public void setCodeattributaire(String codeattributaire) {
        this.codeattributaire = codeattributaire;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attributaire)) {
            return false;
        }
        return id != null && id.equals(((Attributaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attributaire{" +
            "id=" + getId() +
            ", idattributaire=" + getIdattributaire() +
            ", libelle='" + getLibelle() + "'" +
            ", codeattributaire='" + getCodeattributaire() + "'" +
            "}";
    }
}
