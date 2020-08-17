package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Version.
 */
@Entity
@Table(name = "version")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Version implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idversion", nullable = false)
    private Integer idversion;

    @Size(max = 254)
    @Column(name = "refversion", length = 254)
    private String refversion;

    @Size(max = 254)
    @Column(name = "libelle", length = 254)
    private String libelle;

    @Column(name = "date")
    private Instant date;

    @Size(max = 254)
    @Column(name = "fichier", length = 254)
    private String fichier;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "versions", allowSetters = true)
    private Typeterminal idtypeterminal;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdversion() {
        return idversion;
    }

    public Version idversion(Integer idversion) {
        this.idversion = idversion;
        return this;
    }

    public void setIdversion(Integer idversion) {
        this.idversion = idversion;
    }

    public String getRefversion() {
        return refversion;
    }

    public Version refversion(String refversion) {
        this.refversion = refversion;
        return this;
    }

    public void setRefversion(String refversion) {
        this.refversion = refversion;
    }

    public String getLibelle() {
        return libelle;
    }

    public Version libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Instant getDate() {
        return date;
    }

    public Version date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getFichier() {
        return fichier;
    }

    public Version fichier(String fichier) {
        this.fichier = fichier;
        return this;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public Typeterminal getIdtypeterminal() {
        return idtypeterminal;
    }

    public Version idtypeterminal(Typeterminal typeterminal) {
        this.idtypeterminal = typeterminal;
        return this;
    }

    public void setIdtypeterminal(Typeterminal typeterminal) {
        this.idtypeterminal = typeterminal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Version)) {
            return false;
        }
        return id != null && id.equals(((Version) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Version{" +
            "id=" + getId() +
            ", idversion=" + getIdversion() +
            ", refversion='" + getRefversion() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", date='" + getDate() + "'" +
            ", fichier='" + getFichier() + "'" +
            "}";
    }
}
