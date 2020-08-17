package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Hypodrome.
 */
@Entity
@Table(name = "hypodrome")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hypodrome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idhypodrome", nullable = false)
    private Integer idhypodrome;

    @Column(name = "codehypodrome")
    private Integer codehypodrome;

    @Size(max = 254)
    @Column(name = "abreviation", length = 254)
    private String abreviation;

    @Size(max = 254)
    @Column(name = "pays", length = 254)
    private String pays;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdhypodrome() {
        return idhypodrome;
    }

    public Hypodrome idhypodrome(Integer idhypodrome) {
        this.idhypodrome = idhypodrome;
        return this;
    }

    public void setIdhypodrome(Integer idhypodrome) {
        this.idhypodrome = idhypodrome;
    }

    public Integer getCodehypodrome() {
        return codehypodrome;
    }

    public Hypodrome codehypodrome(Integer codehypodrome) {
        this.codehypodrome = codehypodrome;
        return this;
    }

    public void setCodehypodrome(Integer codehypodrome) {
        this.codehypodrome = codehypodrome;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public Hypodrome abreviation(String abreviation) {
        this.abreviation = abreviation;
        return this;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getPays() {
        return pays;
    }

    public Hypodrome pays(String pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hypodrome)) {
            return false;
        }
        return id != null && id.equals(((Hypodrome) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hypodrome{" +
            "id=" + getId() +
            ", idhypodrome=" + getIdhypodrome() +
            ", codehypodrome=" + getCodehypodrome() +
            ", abreviation='" + getAbreviation() + "'" +
            ", pays='" + getPays() + "'" +
            "}";
    }
}
