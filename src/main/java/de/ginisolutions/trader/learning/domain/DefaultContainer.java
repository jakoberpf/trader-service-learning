package de.ginisolutions.trader.learning.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import de.ginisolutions.trader.learning.domain.enumeration.STRATEGY;

/**
 * The DefaultContainer entity.\n@author A true hipster
 */
@Document(collection = "default_container")
public class DefaultContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("strategy")
    private STRATEGY strategy;

    @DBRef
    @Field("calibrationContainer")
    private Set<CalibrationContainer> calibrationContainers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public STRATEGY getStrategy() {
        return strategy;
    }

    public DefaultContainer strategy(STRATEGY strategy) {
        this.strategy = strategy;
        return this;
    }

    public void setStrategy(STRATEGY strategy) {
        this.strategy = strategy;
    }

    public Set<CalibrationContainer> getCalibrationContainers() {
        return calibrationContainers;
    }

    public DefaultContainer calibrationContainers(Set<CalibrationContainer> calibrationContainers) {
        this.calibrationContainers = calibrationContainers;
        return this;
    }

    public DefaultContainer addCalibrationContainer(CalibrationContainer calibrationContainer) {
        this.calibrationContainers.add(calibrationContainer);
        calibrationContainer.setDefaultContainer(this);
        return this;
    }

    public DefaultContainer removeCalibrationContainer(CalibrationContainer calibrationContainer) {
        this.calibrationContainers.remove(calibrationContainer);
        calibrationContainer.setDefaultContainer(null);
        return this;
    }

    public void setCalibrationContainers(Set<CalibrationContainer> calibrationContainers) {
        this.calibrationContainers = calibrationContainers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultContainer)) {
            return false;
        }
        return id != null && id.equals(((DefaultContainer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DefaultContainer{" +
            "id=" + getId() +
            ", strategy='" + getStrategy() + "'" +
            "}";
    }
}
