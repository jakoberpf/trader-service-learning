package de.ginisolutions.trader.learning.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.ginisolutions.trader.learning.web.rest.TestUtil;

public class DefaultContainerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefaultContainerDTO.class);
        DefaultContainerDTO defaultContainerDTO1 = new DefaultContainerDTO();
        defaultContainerDTO1.setId("id1");
        DefaultContainerDTO defaultContainerDTO2 = new DefaultContainerDTO();
        assertThat(defaultContainerDTO1).isNotEqualTo(defaultContainerDTO2);
        defaultContainerDTO2.setId(defaultContainerDTO1.getId());
        assertThat(defaultContainerDTO1).isEqualTo(defaultContainerDTO2);
        defaultContainerDTO2.setId("id2");
        assertThat(defaultContainerDTO1).isNotEqualTo(defaultContainerDTO2);
        defaultContainerDTO1.setId(null);
        assertThat(defaultContainerDTO1).isNotEqualTo(defaultContainerDTO2);
    }
}
