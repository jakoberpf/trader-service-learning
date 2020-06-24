package de.ginisolutions.trader.learning.service.mapper;


import de.ginisolutions.trader.learning.domain.*;
import de.ginisolutions.trader.learning.service.dto.CalibrationContainerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CalibrationContainer} and its DTO {@link CalibrationContainerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalibrationContainerMapper extends EntityMapper<CalibrationContainerDTO, CalibrationContainer> {

//    @Mapping(source = "defaultContainer.id", target = "defaultContainerId")
    CalibrationContainerDTO toDto(CalibrationContainer calibrationContainer);

//    @Mapping(source = "defaultContainerId", target = "defaultContainer")
    CalibrationContainer toEntity(CalibrationContainerDTO calibrationContainerDTO);

    default CalibrationContainer fromId(String id) {
        if (id == null) {
            return null;
        }
        CalibrationContainer calibrationContainer = new CalibrationContainer();
        calibrationContainer.setId(id);
        return calibrationContainer;
    }
}
