package de.ginisolutions.trader.learning.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DefaultContainerMapperTest {

    private DefaultContainerMapper defaultContainerMapper;

    @BeforeEach
    public void setUp() {
        defaultContainerMapper = new DefaultContainerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(defaultContainerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(defaultContainerMapper.fromId(null)).isNull();
    }
}
