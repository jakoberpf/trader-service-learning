package de.ginisolutions.trader.learning.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.ginisolutions.trader.learning.web.rest.TestUtil;

public class CalibrationContainerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalibrationContainerDTO.class);
        CalibrationContainerDTO calibrationContainerDTO1 = new CalibrationContainerDTO();
        calibrationContainerDTO1.setId("id1");
        CalibrationContainerDTO calibrationContainerDTO2 = new CalibrationContainerDTO();
        assertThat(calibrationContainerDTO1).isNotEqualTo(calibrationContainerDTO2);
        calibrationContainerDTO2.setId(calibrationContainerDTO1.getId());
        assertThat(calibrationContainerDTO1).isEqualTo(calibrationContainerDTO2);
        calibrationContainerDTO2.setId("id2");
        assertThat(calibrationContainerDTO1).isNotEqualTo(calibrationContainerDTO2);
        calibrationContainerDTO1.setId(null);
        assertThat(calibrationContainerDTO1).isNotEqualTo(calibrationContainerDTO2);
    }
}
