package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Ville.
 */
@Entity
@Table(name = "ville")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ville implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idville", nullable = false)
    private Integer idville;

    @Column(name = "designation")
    private Integer designation;

    @Size(max = 254)
    @Column(name = "codeville", length = 254)
    private String codeville;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdville() {
        return idville;
    }

    public Ville idville(Integer idville) {
        this.idville = idville;
        return this;
    }

    public void setIdville(Integer idville) {
        this.idville = idville;
    }

    public Integer getDesignation() {
        return designation;
    }

    public Ville designation(Integer designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Integer designation) {
        this.designation = designation;
    }

    public String getCodeville() {
        return codeville;
    }

    public Ville codeville(String codeville) {
        this.codeville = codeville;
        return this;
    }

    public void setCodeville(String codeville) {
        this.codeville = codeville;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ville)) {
            return false;
        }
        return id != null && id.equals(((Ville) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ville{" +
            "id=" + getId() +
            ", idville=" + getIdville() +
            ", designation=" + getDesignation() +
            ", codeville='" + getCodeville() + "'" +
            "}";
    }
}
