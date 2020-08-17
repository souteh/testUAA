package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class I18nTextTranslationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(I18nTextTranslation.class);
        I18nTextTranslation i18nTextTranslation1 = new I18nTextTranslation();
        i18nTextTranslation1.setId(1L);
        I18nTextTranslation i18nTextTranslation2 = new I18nTextTranslation();
        i18nTextTranslation2.setId(i18nTextTranslation1.getId());
        assertThat(i18nTextTranslation1).isEqualTo(i18nTextTranslation2);
        i18nTextTranslation2.setId(2L);
        assertThat(i18nTextTranslation1).isNotEqualTo(i18nTextTranslation2);
        i18nTextTranslation1.setId(null);
        assertThat(i18nTextTranslation1).isNotEqualTo(i18nTextTranslation2);
    }
}
