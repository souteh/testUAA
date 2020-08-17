package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TypeterminalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Typeterminal.class);
        Typeterminal typeterminal1 = new Typeterminal();
        typeterminal1.setId(1L);
        Typeterminal typeterminal2 = new Typeterminal();
        typeterminal2.setId(typeterminal1.getId());
        assertThat(typeterminal1).isEqualTo(typeterminal2);
        typeterminal2.setId(2L);
        assertThat(typeterminal1).isNotEqualTo(typeterminal2);
        typeterminal1.setId(null);
        assertThat(typeterminal1).isNotEqualTo(typeterminal2);
    }
}
