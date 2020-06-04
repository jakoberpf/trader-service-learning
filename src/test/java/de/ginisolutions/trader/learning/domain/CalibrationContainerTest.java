package de.ginisolutions.trader.learning.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.ginisolutions.trader.learning.web.rest.TestUtil;

public class CalibrationContainerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalibrationContainer.class);
        CalibrationContainer calibrationContainer1 = new CalibrationContainer();
        calibrationContainer1.setId("id1");
        CalibrationContainer calibrationContainer2 = new CalibrationContainer();
        calibrationContainer2.setId(calibrationContainer1.getId());
        assertThat(calibrationContainer1).isEqualTo(calibrationContainer2);
        calibrationContainer2.setId("id2");
        assertThat(calibrationContainer1).isNotEqualTo(calibrationContainer2);
        calibrationContainer1.setId(null);
        assertThat(calibrationContainer1).isNotEqualTo(calibrationContainer2);
    }
}
