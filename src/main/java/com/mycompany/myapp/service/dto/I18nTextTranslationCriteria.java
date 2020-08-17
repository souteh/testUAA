package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.I18nTextTranslation} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.I18nTextTranslationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /i-18-n-text-translations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class I18nTextTranslationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter lang;

    private StringFilter translation;

    private IntegerFilter i18nTextId;

    public I18nTextTranslationCriteria() {
    }

    public I18nTextTranslationCriteria(I18nTextTranslationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.lang = other.lang == null ? null : other.lang.copy();
        this.translation = other.translation == null ? null : other.translation.copy();
        this.i18nTextId = other.i18nTextId == null ? null : other.i18nTextId.copy();
    }

    @Override
    public I18nTextTranslationCriteria copy() {
        return new I18nTextTranslationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLang() {
        return lang;
    }

    public void setLang(StringFilter lang) {
        this.lang = lang;
    }

    public StringFilter getTranslation() {
        return translation;
    }

    public void setTranslation(StringFilter translation) {
        this.translation = translation;
    }

    public IntegerFilter geti18nTextId() {
        return i18nTextId;
    }

    public void seti18nTextId(IntegerFilter i18nTextId) {
        this.i18nTextId = i18nTextId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final I18nTextTranslationCriteria that = (I18nTextTranslationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(lang, that.lang) &&
            Objects.equals(translation, that.translation) &&
            Objects.equals(i18nTextId, that.i18nTextId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        lang,
        translation,
        i18nTextId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "I18nTextTranslationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lang != null ? "lang=" + lang + ", " : "") +
                (translation != null ? "translation=" + translation + ", " : "") +
                (i18nTextId != null ? "i18nTextId=" + i18nTextId + ", " : "") +
            "}";
    }

}
