package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A I18nTextTranslation.
 */
@Entity
@Table(name = "i_18_n_text_translation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class I18nTextTranslation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45)
    @Column(name = "lang", length = 45, nullable = false)
    private String lang;

    @NotNull
    @Size(max = 512)
    @Column(name = "translation", length = 512, nullable = false)
    private String translation;

    @NotNull
    @Column(name = "i_18_n_text_id", nullable = false)
    private Integer i18nTextId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public I18nTextTranslation lang(String lang) {
        this.lang = lang;
        return this;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTranslation() {
        return translation;
    }

    public I18nTextTranslation translation(String translation) {
        this.translation = translation;
        return this;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Integer geti18nTextId() {
        return i18nTextId;
    }

    public I18nTextTranslation i18nTextId(Integer i18nTextId) {
        this.i18nTextId = i18nTextId;
        return this;
    }

    public void seti18nTextId(Integer i18nTextId) {
        this.i18nTextId = i18nTextId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof I18nTextTranslation)) {
            return false;
        }
        return id != null && id.equals(((I18nTextTranslation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "I18nTextTranslation{" +
            "id=" + getId() +
            ", lang='" + getLang() + "'" +
            ", translation='" + getTranslation() + "'" +
            ", i18nTextId=" + geti18nTextId() +
            "}";
    }
}
