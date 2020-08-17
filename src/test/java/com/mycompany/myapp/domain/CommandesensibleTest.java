package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommandesensibleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commandesensible.class);
        Commandesensible commandesensible1 = new Commandesensible();
        commandesensible1.setId(1L);
        Commandesensible commandesensible2 = new Commandesensible();
        commandesensible2.setId(commandesensible1.getId());
        assertThat(commandesensible1).isEqualTo(commandesensible2);
        commandesensible2.setId(2L);
        assertThat(commandesensible1).isNotEqualTo(commandesensible2);
        commandesensible1.setId(null);
        assertThat(commandesensible1).isNotEqualTo(commandesensible2);
    }
}
