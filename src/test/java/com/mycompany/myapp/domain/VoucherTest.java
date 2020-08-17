package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class VoucherTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voucher.class);
        Voucher voucher1 = new Voucher();
        voucher1.setId(1L);
        Voucher voucher2 = new Voucher();
        voucher2.setId(voucher1.getId());
        assertThat(voucher1).isEqualTo(voucher2);
        voucher2.setId(2L);
        assertThat(voucher1).isNotEqualTo(voucher2);
        voucher1.setId(null);
        assertThat(voucher1).isNotEqualTo(voucher2);
    }
}
