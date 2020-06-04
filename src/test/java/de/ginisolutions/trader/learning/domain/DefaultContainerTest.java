package de.ginisolutions.trader.learning.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.ginisolutions.trader.learning.web.rest.TestUtil;

public class DefaultContainerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DefaultContainer.class);
        DefaultContainer defaultContainer1 = new DefaultContainer();
        defaultContainer1.setId("id1");
        DefaultContainer defaultContainer2 = new DefaultContainer();
        defaultContainer2.setId(defaultContainer1.getId());
        assertThat(defaultContainer1).isEqualTo(defaultContainer2);
        defaultContainer2.setId("id2");
        assertThat(defaultContainer1).isNotEqualTo(defaultContainer2);
        defaultContainer1.setId(null);
        assertThat(defaultContainer1).isNotEqualTo(defaultContainer2);
    }
}
