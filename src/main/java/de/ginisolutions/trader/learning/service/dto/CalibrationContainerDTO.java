package de.ginisolutions.trader.learning.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import de.ginisolutions.trader.learning.domain.enumeration.STRATEGY;

/**
 * A DTO for the {@link de.ginisolutions.trader.learning.domain.CalibrationContainer} entity.
 */
@ApiModel(description = "The ValueContainer entity.\n@author A true hipster")
public class CalibrationContainerDTO implements Serializable {
    
    private String id;

    private STRATEGY strategy;


    private String defaultContainerId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public STRATEGY getStrategy() {
        return strategy;
    }

    public void setStrategy(STRATEGY strategy) {
        this.strategy = strategy;
    }

    public String getDefaultContainerId() {
        return defaultContainerId;
    }

    public void setDefaultContainerId(String defaultContainerId) {
        this.defaultContainerId = defaultContainerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalibrationContainerDTO)) {
            return false;
        }

        return id != null && id.equals(((CalibrationContainerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalibrationContainerDTO{" +
            "id=" + getId() +
            ", strategy='" + getStrategy() + "'" +
            ", defaultContainerId='" + getDefaultContainerId() + "'" +
            "}";
    }
}
