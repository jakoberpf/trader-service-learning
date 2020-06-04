package de.ginisolutions.trader.learning.service.mapper;


import de.ginisolutions.trader.learning.domain.*;
import de.ginisolutions.trader.learning.service.dto.DefaultContainerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DefaultContainer} and its DTO {@link DefaultContainerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DefaultContainerMapper extends EntityMapper<DefaultContainerDTO, DefaultContainer> {


    @Mapping(target = "calibrationContainers", ignore = true)
    @Mapping(target = "removeCalibrationContainer", ignore = true)
    DefaultContainer toEntity(DefaultContainerDTO defaultContainerDTO);

    default DefaultContainer fromId(String id) {
        if (id == null) {
            return null;
        }
        DefaultContainer defaultContainer = new DefaultContainer();
        defaultContainer.setId(id);
        return defaultContainer;
    }
}
