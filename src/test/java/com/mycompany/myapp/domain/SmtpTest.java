package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SmtpTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Smtp.class);
        Smtp smtp1 = new Smtp();
        smtp1.setId(1L);
        Smtp smtp2 = new Smtp();
        smtp2.setId(smtp1.getId());
        assertThat(smtp1).isEqualTo(smtp2);
        smtp2.setId(2L);
        assertThat(smtp1).isNotEqualTo(smtp2);
        smtp1.setId(null);
        assertThat(smtp1).isNotEqualTo(smtp2);
    }
}
