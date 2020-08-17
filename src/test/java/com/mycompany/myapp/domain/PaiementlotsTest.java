package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PaiementlotsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paiementlots.class);
        Paiementlots paiementlots1 = new Paiementlots();
        paiementlots1.setId(1L);
        Paiementlots paiementlots2 = new Paiementlots();
        paiementlots2.setId(paiementlots1.getId());
        assertThat(paiementlots1).isEqualTo(paiementlots2);
        paiementlots2.setId(2L);
        assertThat(paiementlots1).isNotEqualTo(paiementlots2);
        paiementlots1.setId(null);
        assertThat(paiementlots1).isNotEqualTo(paiementlots2);
    }
}
