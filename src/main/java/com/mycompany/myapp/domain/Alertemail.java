package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Alertemail.
 */
@Entity
@Table(name = "alertemail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Alertemail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idalertemail", nullable = false)
    private Integer idalertemail;

    @Size(max = 254)
    @Column(name = "codealertemail", length = 254)
    private String codealertemail;

    @Size(max = 254)
    @Column(name = "typealertemail", length = 254)
    private String typealertemail;

    @Size(max = 254)
    @Column(name = "objetalertemail", length = 254)
    private String objetalertemail;

    @Size(max = 254)
    @Column(name = "adressemaildiffusion", length = 254)
    private String adressemaildiffusion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdalertemail() {
        return idalertemail;
    }

    public Alertemail idalertemail(Integer idalertemail) {
        this.idalertemail = idalertemail;
        return this;
    }

    public void setIdalertemail(Integer idalertemail) {
        this.idalertemail = idalertemail;
    }

    public String getCodealertemail() {
        return codealertemail;
    }

    public Alertemail codealertemail(String codealertemail) {
        this.codealertemail = codealertemail;
        return this;
    }

    public void setCodealertemail(String codealertemail) {
        this.codealertemail = codealertemail;
    }

    public String getTypealertemail() {
        return typealertemail;
    }

    public Alertemail typealertemail(String typealertemail) {
        this.typealertemail = typealertemail;
        return this;
    }

    public void setTypealertemail(String typealertemail) {
        this.typealertemail = typealertemail;
    }

    public String getObjetalertemail() {
        return objetalertemail;
    }

    public Alertemail objetalertemail(String objetalertemail) {
        this.objetalertemail = objetalertemail;
        return this;
    }

    public void setObjetalertemail(String objetalertemail) {
        this.objetalertemail = objetalertemail;
    }

    public String getAdressemaildiffusion() {
        return adressemaildiffusion;
    }

    public Alertemail adressemaildiffusion(String adressemaildiffusion) {
        this.adressemaildiffusion = adressemaildiffusion;
        return this;
    }

    public void setAdressemaildiffusion(String adressemaildiffusion) {
        this.adressemaildiffusion = adressemaildiffusion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alertemail)) {
            return false;
        }
        return id != null && id.equals(((Alertemail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alertemail{" +
            "id=" + getId() +
            ", idalertemail=" + getIdalertemail() +
            ", codealertemail='" + getCodealertemail() + "'" +
            ", typealertemail='" + getTypealertemail() + "'" +
            ", objetalertemail='" + getObjetalertemail() + "'" +
            ", adressemaildiffusion='" + getAdressemaildiffusion() + "'" +
            "}";
    }
}
