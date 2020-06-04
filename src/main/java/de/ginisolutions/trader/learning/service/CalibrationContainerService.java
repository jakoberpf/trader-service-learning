package de.ginisolutions.trader.learning.service;

import de.ginisolutions.trader.learning.domain.CalibrationContainer;
import de.ginisolutions.trader.learning.repository.CalibrationContainerRepository;
import de.ginisolutions.trader.learning.service.dto.CalibrationContainerDTO;
import de.ginisolutions.trader.learning.service.mapper.CalibrationContainerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CalibrationContainer}.
 */
@Service
public class CalibrationContainerService {

    private final Logger log = LoggerFactory.getLogger(CalibrationContainerService.class);

    private final CalibrationContainerRepository calibrationContainerRepository;

    private final CalibrationContainerMapper calibrationContainerMapper;

    public CalibrationContainerService(CalibrationContainerRepository calibrationContainerRepository, CalibrationContainerMapper calibrationContainerMapper) {
        this.calibrationContainerRepository = calibrationContainerRepository;
        this.calibrationContainerMapper = calibrationContainerMapper;
    }

    /**
     * Save a calibrationContainer.
     *
     * @param calibrationContainerDTO the entity to save.
     * @return the persisted entity.
     */
    public CalibrationContainerDTO save(CalibrationContainerDTO calibrationContainerDTO) {
        log.debug("Request to save CalibrationContainer : {}", calibrationContainerDTO);
        CalibrationContainer calibrationContainer = calibrationContainerMapper.toEntity(calibrationContainerDTO);
        calibrationContainer = calibrationContainerRepository.save(calibrationContainer);
        return calibrationContainerMapper.toDto(calibrationContainer);
    }

    /**
     * Get all the calibrationContainers.
     *
     * @return the list of entities.
     */
    public List<CalibrationContainerDTO> findAll() {
        log.debug("Request to get all CalibrationContainers");
        return calibrationContainerRepository.findAll().stream()
            .map(calibrationContainerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one calibrationContainer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CalibrationContainerDTO> findOne(String id) {
        log.debug("Request to get CalibrationContainer : {}", id);
        return calibrationContainerRepository.findById(id)
            .map(calibrationContainerMapper::toDto);
    }

    /**
     * Delete the calibrationContainer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CalibrationContainer : {}", id);

        calibrationContainerRepository.deleteById(id);
    }
}
