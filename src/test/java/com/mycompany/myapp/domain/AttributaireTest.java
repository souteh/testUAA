package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AttributaireTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attributaire.class);
        Attributaire attributaire1 = new Attributaire();
        attributaire1.setId(1L);
        Attributaire attributaire2 = new Attributaire();
        attributaire2.setId(attributaire1.getId());
        assertThat(attributaire1).isEqualTo(attributaire2);
        attributaire2.setId(2L);
        assertThat(attributaire1).isNotEqualTo(attributaire2);
        attributaire1.setId(null);
        assertThat(attributaire1).isNotEqualTo(attributaire2);
    }
}
