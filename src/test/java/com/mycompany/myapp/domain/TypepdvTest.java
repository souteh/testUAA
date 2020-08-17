package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TypepdvTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Typepdv.class);
        Typepdv typepdv1 = new Typepdv();
        typepdv1.setId(1L);
        Typepdv typepdv2 = new Typepdv();
        typepdv2.setId(typepdv1.getId());
        assertThat(typepdv1).isEqualTo(typepdv2);
        typepdv2.setId(2L);
        assertThat(typepdv1).isNotEqualTo(typepdv2);
        typepdv1.setId(null);
        assertThat(typepdv1).isNotEqualTo(typepdv2);
    }
}
