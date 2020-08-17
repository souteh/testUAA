package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Choisit.
 */
@Entity
@Table(name = "choisit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Choisit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idproduit", nullable = false)
    private Integer idproduit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "choisits", allowSetters = true)
    private Attributaire idattributaire;

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

    public Choisit idproduit(Integer idproduit) {
        this.idproduit = idproduit;
        return this;
    }

    public void setIdproduit(Integer idproduit) {
        this.idproduit = idproduit;
    }

    public Attributaire getIdattributaire() {
        return idattributaire;
    }

    public Choisit idattributaire(Attributaire attributaire) {
        this.idattributaire = attributaire;
        return this;
    }

    public void setIdattributaire(Attributaire attributaire) {
        this.idattributaire = attributaire;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Choisit)) {
            return false;
        }
        return id != null && id.equals(((Choisit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Choisit{" +
            "id=" + getId() +
            ", idproduit=" + getIdproduit() +
            "}";
    }
}
