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
public class CalibrationResource {

    private final Logger log = LoggerFactory.getLogger(CalibrationResource.class);

    private static final String ENTITY_NAME = "traderServiceLearningCalibrationContainer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalibrationContainerService calibrationContainerService;

    public CalibrationResource(CalibrationContainerService calibrationContainerService) {
        this.calibrationContainerService = calibrationContainerService;
    }

    /**
     * {@code GET  /calibration-containers} : get all the calibrationContainers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calibrationContainers in body.
     */
    @GetMapping("/calibration")
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
    @GetMapping("/calibration/{id}")
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
    @DeleteMapping("/calibration/{id}")
    public ResponseEntity<Void> deleteCalibrationContainer(@PathVariable String id) {
        log.debug("REST request to delete CalibrationContainer : {}", id);

        calibrationContainerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
