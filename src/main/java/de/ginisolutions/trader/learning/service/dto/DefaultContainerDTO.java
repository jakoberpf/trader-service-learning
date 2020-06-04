package de.ginisolutions.trader.learning.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import de.ginisolutions.trader.learning.domain.enumeration.STRATEGY;

/**
 * A DTO for the {@link de.ginisolutions.trader.learning.domain.DefaultContainer} entity.
 */
@ApiModel(description = "The DefaultContainer entity.\n@author A true hipster")
public class DefaultContainerDTO implements Serializable {
    
    private String id;

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

    public void setStrategy(STRATEGY strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultContainerDTO)) {
            return false;
        }

        return id != null && id.equals(((DefaultContainerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DefaultContainerDTO{" +
            "id=" + getId() +
            ", strategy='" + getStrategy() + "'" +
            "}";
    }
}
