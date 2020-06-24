package de.ginisolutions.trader.learning.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.ginisolutions.trader.trading.domain.enumeration.STRATEGY;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;


/**
 * The ValueContainer entity.\n@author A true hipster
 */
@Document(collection = "calibration_container")
public class CalibrationContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("strategy")
    private STRATEGY strategy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public STRATEGY getStrategy() {
        return strategy;
    }

    public CalibrationContainer strategy(STRATEGY strategy) {
        this.strategy = strategy;
        return this;
    }

    public void setStrategy(STRATEGY strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalibrationContainer)) {
            return false;
        }
        return id != null && id.equals(((CalibrationContainer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CalibrationContainer{" +
            "id=" + getId() +
            ", strategy='" + getStrategy() + "'" +
            "}";
    }
}
