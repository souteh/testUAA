package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PdvTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pdv.class);
        Pdv pdv1 = new Pdv();
        pdv1.setId(1L);
        Pdv pdv2 = new Pdv();
        pdv2.setId(pdv1.getId());
        assertThat(pdv1).isEqualTo(pdv2);
        pdv2.setId(2L);
        assertThat(pdv1).isNotEqualTo(pdv2);
        pdv1.setId(null);
        assertThat(pdv1).isNotEqualTo(pdv2);
    }
}
