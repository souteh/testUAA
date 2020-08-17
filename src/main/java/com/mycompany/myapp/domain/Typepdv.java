package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Typepdv.
 */
@Entity
@Table(name = "typepdv")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Typepdv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idtypepdv", nullable = false)
    private Integer idtypepdv;

    @Size(max = 254)
    @Column(name = "reftypepdv", length = 254)
    private String reftypepdv;

    @Size(max = 254)
    @Column(name = "type", length = 254)
    private String type;

    @Column(name = "nbremaxterminaux")
    private Integer nbremaxterminaux;

    @Column(name = "plafondpostpaye")
    private Integer plafondpostpaye;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdtypepdv() {
        return idtypepdv;
    }

    public Typepdv idtypepdv(Integer idtypepdv) {
        this.idtypepdv = idtypepdv;
        return this;
    }

    public void setIdtypepdv(Integer idtypepdv) {
        this.idtypepdv = idtypepdv;
    }

    public String getReftypepdv() {
        return reftypepdv;
    }

    public Typepdv reftypepdv(String reftypepdv) {
        this.reftypepdv = reftypepdv;
        return this;
    }

    public void setReftypepdv(String reftypepdv) {
        this.reftypepdv = reftypepdv;
    }

    public String getType() {
        return type;
    }

    public Typepdv type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNbremaxterminaux() {
        return nbremaxterminaux;
    }

    public Typepdv nbremaxterminaux(Integer nbremaxterminaux) {
        this.nbremaxterminaux = nbremaxterminaux;
        return this;
    }

    public void setNbremaxterminaux(Integer nbremaxterminaux) {
        this.nbremaxterminaux = nbremaxterminaux;
    }

    public Integer getPlafondpostpaye() {
        return plafondpostpaye;
    }

    public Typepdv plafondpostpaye(Integer plafondpostpaye) {
        this.plafondpostpaye = plafondpostpaye;
        return this;
    }

    public void setPlafondpostpaye(Integer plafondpostpaye) {
        this.plafondpostpaye = plafondpostpaye;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Typepdv)) {
            return false;
        }
        return id != null && id.equals(((Typepdv) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Typepdv{" +
            "id=" + getId() +
            ", idtypepdv=" + getIdtypepdv() +
            ", reftypepdv='" + getReftypepdv() + "'" +
            ", type='" + getType() + "'" +
            ", nbremaxterminaux=" + getNbremaxterminaux() +
            ", plafondpostpaye=" + getPlafondpostpaye() +
            "}";
    }
}
