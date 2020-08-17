package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Pays.
 */
@Entity
@Table(name = "pays")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idpays", nullable = false)
    private Integer idpays;

    @Size(max = 254)
    @Column(name = "codepays", length = 254)
    private String codepays;

    @Size(max = 254)
    @Column(name = "designation", length = 254)
    private String designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdpays() {
        return idpays;
    }

    public Pays idpays(Integer idpays) {
        this.idpays = idpays;
        return this;
    }

    public void setIdpays(Integer idpays) {
        this.idpays = idpays;
    }

    public String getCodepays() {
        return codepays;
    }

    public Pays codepays(String codepays) {
        this.codepays = codepays;
        return this;
    }

    public void setCodepays(String codepays) {
        this.codepays = codepays;
    }

    public String getDesignation() {
        return designation;
    }

    public Pays designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pays)) {
            return false;
        }
        return id != null && id.equals(((Pays) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pays{" +
            "id=" + getId() +
            ", idpays=" + getIdpays() +
            ", codepays='" + getCodepays() + "'" +
            ", designation='" + getDesignation() + "'" +
            "}";
    }
}
