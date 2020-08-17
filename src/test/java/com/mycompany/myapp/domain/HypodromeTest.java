package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class HypodromeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hypodrome.class);
        Hypodrome hypodrome1 = new Hypodrome();
        hypodrome1.setId(1L);
        Hypodrome hypodrome2 = new Hypodrome();
        hypodrome2.setId(hypodrome1.getId());
        assertThat(hypodrome1).isEqualTo(hypodrome2);
        hypodrome2.setId(2L);
        assertThat(hypodrome1).isNotEqualTo(hypodrome2);
        hypodrome1.setId(null);
        assertThat(hypodrome1).isNotEqualTo(hypodrome2);
    }
}
