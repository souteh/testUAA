package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Delaiannulation.
 */
@Entity
@Table(name = "delaiannulation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Delaiannulation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "iddelaiannulation", nullable = false)
    private Integer iddelaiannulation;

    @Size(max = 254)
    @Column(name = "codedelaiannulation", length = 254)
    private String codedelaiannulation;

    @Size(max = 254)
    @Column(name = "designationdelaiannulation", length = 254)
    private String designationdelaiannulation;

    @Column(name = "valeurdelaiannulation")
    private Integer valeurdelaiannulation;

    @Size(max = 254)
    @Column(name = "statut", length = 254)
    private String statut;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIddelaiannulation() {
        return iddelaiannulation;
    }

    public Delaiannulation iddelaiannulation(Integer iddelaiannulation) {
        this.iddelaiannulation = iddelaiannulation;
        return this;
    }

    public void setIddelaiannulation(Integer iddelaiannulation) {
        this.iddelaiannulation = iddelaiannulation;
    }

    public String getCodedelaiannulation() {
        return codedelaiannulation;
    }

    public Delaiannulation codedelaiannulation(String codedelaiannulation) {
        this.codedelaiannulation = codedelaiannulation;
        return this;
    }

    public void setCodedelaiannulation(String codedelaiannulation) {
        this.codedelaiannulation = codedelaiannulation;
    }

    public String getDesignationdelaiannulation() {
        return designationdelaiannulation;
    }

    public Delaiannulation designationdelaiannulation(String designationdelaiannulation) {
        this.designationdelaiannulation = designationdelaiannulation;
        return this;
    }

    public void setDesignationdelaiannulation(String designationdelaiannulation) {
        this.designationdelaiannulation = designationdelaiannulation;
    }

    public Integer getValeurdelaiannulation() {
        return valeurdelaiannulation;
    }

    public Delaiannulation valeurdelaiannulation(Integer valeurdelaiannulation) {
        this.valeurdelaiannulation = valeurdelaiannulation;
        return this;
    }

    public void setValeurdelaiannulation(Integer valeurdelaiannulation) {
        this.valeurdelaiannulation = valeurdelaiannulation;
    }

    public String getStatut() {
        return statut;
    }

    public Delaiannulation statut(String statut) {
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
        if (!(o instanceof Delaiannulation)) {
            return false;
        }
        return id != null && id.equals(((Delaiannulation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Delaiannulation{" +
            "id=" + getId() +
            ", iddelaiannulation=" + getIddelaiannulation() +
            ", codedelaiannulation='" + getCodedelaiannulation() + "'" +
            ", designationdelaiannulation='" + getDesignationdelaiannulation() + "'" +
            ", valeurdelaiannulation=" + getValeurdelaiannulation() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
