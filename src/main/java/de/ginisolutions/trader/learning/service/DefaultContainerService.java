package de.ginisolutions.trader.learning.service;

import de.ginisolutions.trader.learning.domain.DefaultContainer;
import de.ginisolutions.trader.learning.repository.DefaultContainerRepository;
import de.ginisolutions.trader.learning.service.dto.DefaultContainerDTO;
import de.ginisolutions.trader.learning.service.mapper.DefaultContainerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DefaultContainer}.
 */
@Service
public class DefaultContainerService {

    private final Logger log = LoggerFactory.getLogger(DefaultContainerService.class);

    private final DefaultContainerRepository defaultContainerRepository;

    private final DefaultContainerMapper defaultContainerMapper;

    public DefaultContainerService(DefaultContainerRepository defaultContainerRepository, DefaultContainerMapper defaultContainerMapper) {
        this.defaultContainerRepository = defaultContainerRepository;
        this.defaultContainerMapper = defaultContainerMapper;
    }

    /**
     * Save a defaultContainer.
     *
     * @param defaultContainerDTO the entity to save.
     * @return the persisted entity.
     */
    public DefaultContainerDTO save(DefaultContainerDTO defaultContainerDTO) {
        log.debug("Request to save DefaultContainer : {}", defaultContainerDTO);
        DefaultContainer defaultContainer = defaultContainerMapper.toEntity(defaultContainerDTO);
        defaultContainer = defaultContainerRepository.save(defaultContainer);
        return defaultContainerMapper.toDto(defaultContainer);
    }

    /**
     * Get all the defaultContainers.
     *
     * @return the list of entities.
     */
    public List<DefaultContainerDTO> findAll() {
        log.debug("Request to get all DefaultContainers");
        return defaultContainerRepository.findAll().stream()
            .map(defaultContainerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one defaultContainer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DefaultContainerDTO> findOne(String id) {
        log.debug("Request to get DefaultContainer : {}", id);
        return defaultContainerRepository.findById(id)
            .map(defaultContainerMapper::toDto);
    }

    /**
     * Delete the defaultContainer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete DefaultContainer : {}", id);

        defaultContainerRepository.deleteById(id);
    }
}
