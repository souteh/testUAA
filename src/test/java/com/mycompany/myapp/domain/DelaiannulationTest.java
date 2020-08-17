package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DelaiannulationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Delaiannulation.class);
        Delaiannulation delaiannulation1 = new Delaiannulation();
        delaiannulation1.setId(1L);
        Delaiannulation delaiannulation2 = new Delaiannulation();
        delaiannulation2.setId(delaiannulation1.getId());
        assertThat(delaiannulation1).isEqualTo(delaiannulation2);
        delaiannulation2.setId(2L);
        assertThat(delaiannulation1).isNotEqualTo(delaiannulation2);
        delaiannulation1.setId(null);
        assertThat(delaiannulation1).isNotEqualTo(delaiannulation2);
    }
}
