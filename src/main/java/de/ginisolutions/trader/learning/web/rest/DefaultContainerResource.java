package de.ginisolutions.trader.learning.web.rest;

import de.ginisolutions.trader.learning.service.DefaultContainerService;
import de.ginisolutions.trader.learning.web.rest.errors.BadRequestAlertException;
import de.ginisolutions.trader.learning.service.dto.DefaultContainerDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.ginisolutions.trader.learning.domain.DefaultContainer}.
 */
@RestController
@RequestMapping("/api")
public class DefaultContainerResource {

    private final Logger log = LoggerFactory.getLogger(DefaultContainerResource.class);

    private static final String ENTITY_NAME = "traderServiceLearningDefaultContainer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DefaultContainerService defaultContainerService;

    public DefaultContainerResource(DefaultContainerService defaultContainerService) {
        this.defaultContainerService = defaultContainerService;
    }

    /**
     * {@code POST  /default-containers} : Create a new defaultContainer.
     *
     * @param defaultContainerDTO the defaultContainerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new defaultContainerDTO, or with status {@code 400 (Bad Request)} if the defaultContainer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/default-containers")
    public ResponseEntity<DefaultContainerDTO> createDefaultContainer(@RequestBody DefaultContainerDTO defaultContainerDTO) throws URISyntaxException {
        log.debug("REST request to save DefaultContainer : {}", defaultContainerDTO);
        if (defaultContainerDTO.getId() != null) {
            throw new BadRequestAlertException("A new defaultContainer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DefaultContainerDTO result = defaultContainerService.save(defaultContainerDTO);
        return ResponseEntity.created(new URI("/api/default-containers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /default-containers} : Updates an existing defaultContainer.
     *
     * @param defaultContainerDTO the defaultContainerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated defaultContainerDTO,
     * or with status {@code 400 (Bad Request)} if the defaultContainerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the defaultContainerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/default-containers")
    public ResponseEntity<DefaultContainerDTO> updateDefaultContainer(@RequestBody DefaultContainerDTO defaultContainerDTO) throws URISyntaxException {
        log.debug("REST request to update DefaultContainer : {}", defaultContainerDTO);
        if (defaultContainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DefaultContainerDTO result = defaultContainerService.save(defaultContainerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, defaultContainerDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /default-containers} : get all the defaultContainers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of defaultContainers in body.
     */
    @GetMapping("/default-containers")
    public List<DefaultContainerDTO> getAllDefaultContainers() {
        log.debug("REST request to get all DefaultContainers");
        return defaultContainerService.findAll();
    }

    /**
     * {@code GET  /default-containers/:id} : get the "id" defaultContainer.
     *
     * @param id the id of the defaultContainerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the defaultContainerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/default-containers/{id}")
    public ResponseEntity<DefaultContainerDTO> getDefaultContainer(@PathVariable String id) {
        log.debug("REST request to get DefaultContainer : {}", id);
        Optional<DefaultContainerDTO> defaultContainerDTO = defaultContainerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(defaultContainerDTO);
    }

    /**
     * {@code DELETE  /default-containers/:id} : delete the "id" defaultContainer.
     *
     * @param id the id of the defaultContainerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/default-containers/{id}")
    public ResponseEntity<Void> deleteDefaultContainer(@PathVariable String id) {
        log.debug("REST request to delete DefaultContainer : {}", id);

        defaultContainerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
