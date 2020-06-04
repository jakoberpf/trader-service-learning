package de.ginisolutions.trader.learning.web.rest;

import de.ginisolutions.trader.learning.service.CalibrationContainerService;
import de.ginisolutions.trader.learning.web.rest.errors.BadRequestAlertException;
import de.ginisolutions.trader.learning.service.dto.CalibrationContainerDTO;

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
 * REST controller for managing {@link de.ginisolutions.trader.learning.domain.CalibrationContainer}.
 */
@RestController
@RequestMapping("/api")
public class CalibrationContainerResource {

    private final Logger log = LoggerFactory.getLogger(CalibrationContainerResource.class);

    private static final String ENTITY_NAME = "traderServiceLearningCalibrationContainer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalibrationContainerService calibrationContainerService;

    public CalibrationContainerResource(CalibrationContainerService calibrationContainerService) {
        this.calibrationContainerService = calibrationContainerService;
    }

    /**
     * {@code POST  /calibration-containers} : Create a new calibrationContainer.
     *
     * @param calibrationContainerDTO the calibrationContainerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calibrationContainerDTO, or with status {@code 400 (Bad Request)} if the calibrationContainer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calibration-containers")
    public ResponseEntity<CalibrationContainerDTO> createCalibrationContainer(@RequestBody CalibrationContainerDTO calibrationContainerDTO) throws URISyntaxException {
        log.debug("REST request to save CalibrationContainer : {}", calibrationContainerDTO);
        if (calibrationContainerDTO.getId() != null) {
            throw new BadRequestAlertException("A new calibrationContainer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalibrationContainerDTO result = calibrationContainerService.save(calibrationContainerDTO);
        return ResponseEntity.created(new URI("/api/calibration-containers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /calibration-containers} : Updates an existing calibrationContainer.
     *
     * @param calibrationContainerDTO the calibrationContainerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calibrationContainerDTO,
     * or with status {@code 400 (Bad Request)} if the calibrationContainerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calibrationContainerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calibration-containers")
    public ResponseEntity<CalibrationContainerDTO> updateCalibrationContainer(@RequestBody CalibrationContainerDTO calibrationContainerDTO) throws URISyntaxException {
        log.debug("REST request to update CalibrationContainer : {}", calibrationContainerDTO);
        if (calibrationContainerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CalibrationContainerDTO result = calibrationContainerService.save(calibrationContainerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calibrationContainerDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /calibration-containers} : get all the calibrationContainers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calibrationContainers in body.
     */
    @GetMapping("/calibration-containers")
    public List<CalibrationContainerDTO> getAllCalibrationContainers() {
        log.debug("REST request to get all CalibrationContainers");
        return calibrationContainerService.findAll();
    }

    /**
     * {@code GET  /calibration-containers/:id} : get the "id" calibrationContainer.
     *
     * @param id the id of the calibrationContainerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calibrationContainerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calibration-containers/{id}")
    public ResponseEntity<CalibrationContainerDTO> getCalibrationContainer(@PathVariable String id) {
        log.debug("REST request to get CalibrationContainer : {}", id);
        Optional<CalibrationContainerDTO> calibrationContainerDTO = calibrationContainerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calibrationContainerDTO);
    }

    /**
     * {@code DELETE  /calibration-containers/:id} : delete the "id" calibrationContainer.
     *
     * @param id the id of the calibrationContainerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calibration-containers/{id}")
    public ResponseEntity<Void> deleteCalibrationContainer(@PathVariable String id) {
        log.debug("REST request to delete CalibrationContainer : {}", id);

        calibrationContainerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
