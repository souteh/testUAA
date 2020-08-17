package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ChoisitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Choisit.class);
        Choisit choisit1 = new Choisit();
        choisit1.setId(1L);
        Choisit choisit2 = new Choisit();
        choisit2.setId(choisit1.getId());
        assertThat(choisit1).isEqualTo(choisit2);
        choisit2.setId(2L);
        assertThat(choisit1).isNotEqualTo(choisit2);
        choisit1.setId(null);
        assertThat(choisit1).isNotEqualTo(choisit2);
    }
}
