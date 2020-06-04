package de.ginisolutions.trader.learning.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CalibrationContainerMapperTest {

    private CalibrationContainerMapper calibrationContainerMapper;

    @BeforeEach
    public void setUp() {
        calibrationContainerMapper = new CalibrationContainerMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(calibrationContainerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(calibrationContainerMapper.fromId(null)).isNull();
    }
}
