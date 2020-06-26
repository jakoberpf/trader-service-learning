package de.ginisolutions.trader.learning.web.rest;

import de.ginisolutions.trader.learning.TraderServiceLearningApp;
import de.ginisolutions.trader.learning.config.TestSecurityConfiguration;
import de.ginisolutions.trader.learning.domain.CalibrationContainer;
import de.ginisolutions.trader.learning.repository.CalibrationContainerRepository;
import de.ginisolutions.trader.learning.service.CalibrationContainerService;
import de.ginisolutions.trader.learning.service.dto.CalibrationContainerDTO;
import de.ginisolutions.trader.learning.service.mapper.CalibrationContainerMapper;

import de.ginisolutions.trader.common.enumeration.STRATEGY;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CalibrationResource} REST controller.
 */
@SpringBootTest(classes = { TraderServiceLearningApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CalibrationResourceIT {

    private static final STRATEGY DEFAULT_STRATEGY = STRATEGY.MM;
    private static final STRATEGY UPDATED_STRATEGY = STRATEGY.RSI;

    @Autowired
    private CalibrationContainerRepository calibrationContainerRepository;

    @Autowired
    private CalibrationContainerMapper calibrationContainerMapper;

    @Autowired
    private CalibrationContainerService calibrationContainerService;

    @Autowired
    private MockMvc restCalibrationContainerMockMvc;

    private CalibrationContainer calibrationContainer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalibrationContainer createEntity() {
        CalibrationContainer calibrationContainer = new CalibrationContainer()
            .strategy(DEFAULT_STRATEGY);
        return calibrationContainer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CalibrationContainer createUpdatedEntity() {
        CalibrationContainer calibrationContainer = new CalibrationContainer()
            .strategy(UPDATED_STRATEGY);
        return calibrationContainer;
    }

    @BeforeEach
    public void initTest() {
        calibrationContainerRepository.deleteAll();
        calibrationContainer = createEntity();
    }

    @Test
    public void createCalibrationContainer() throws Exception {
        int databaseSizeBeforeCreate = calibrationContainerRepository.findAll().size();
        // Create the CalibrationContainer
        CalibrationContainerDTO calibrationContainerDTO = calibrationContainerMapper.toDto(calibrationContainer);
        restCalibrationContainerMockMvc.perform(post("/api/calibration-containers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calibrationContainerDTO)))
            .andExpect(status().isCreated());

        // Validate the CalibrationContainer in the database
        List<CalibrationContainer> calibrationContainerList = calibrationContainerRepository.findAll();
        assertThat(calibrationContainerList).hasSize(databaseSizeBeforeCreate + 1);
        CalibrationContainer testCalibrationContainer = calibrationContainerList.get(calibrationContainerList.size() - 1);
        assertThat(testCalibrationContainer.getStrategy()).isEqualTo(DEFAULT_STRATEGY);
    }

    @Test
    public void createCalibrationContainerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calibrationContainerRepository.findAll().size();

        // Create the CalibrationContainer with an existing ID
        calibrationContainer.setId("existing_id");
        CalibrationContainerDTO calibrationContainerDTO = calibrationContainerMapper.toDto(calibrationContainer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalibrationContainerMockMvc.perform(post("/api/calibration-containers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calibrationContainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalibrationContainer in the database
        List<CalibrationContainer> calibrationContainerList = calibrationContainerRepository.findAll();
        assertThat(calibrationContainerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCalibrationContainers() throws Exception {
        // Initialize the database
        calibrationContainerRepository.save(calibrationContainer);

        // Get all the calibrationContainerList
        restCalibrationContainerMockMvc.perform(get("/api/calibration-containers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calibrationContainer.getId())))
            .andExpect(jsonPath("$.[*].strategy").value(hasItem(DEFAULT_STRATEGY.toString())));
    }

    @Test
    public void getCalibrationContainer() throws Exception {
        // Initialize the database
        calibrationContainerRepository.save(calibrationContainer);

        // Get the calibrationContainer
        restCalibrationContainerMockMvc.perform(get("/api/calibration-containers/{id}", calibrationContainer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calibrationContainer.getId()))
            .andExpect(jsonPath("$.strategy").value(DEFAULT_STRATEGY.toString()));
    }
    @Test
    public void getNonExistingCalibrationContainer() throws Exception {
        // Get the calibrationContainer
        restCalibrationContainerMockMvc.perform(get("/api/calibration-containers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCalibrationContainer() throws Exception {
        // Initialize the database
        calibrationContainerRepository.save(calibrationContainer);

        int databaseSizeBeforeUpdate = calibrationContainerRepository.findAll().size();

        // Update the calibrationContainer
        CalibrationContainer updatedCalibrationContainer = calibrationContainerRepository.findById(calibrationContainer.getId()).get();
        updatedCalibrationContainer
            .strategy(UPDATED_STRATEGY);
        CalibrationContainerDTO calibrationContainerDTO = calibrationContainerMapper.toDto(updatedCalibrationContainer);

        restCalibrationContainerMockMvc.perform(put("/api/calibration-containers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calibrationContainerDTO)))
            .andExpect(status().isOk());

        // Validate the CalibrationContainer in the database
        List<CalibrationContainer> calibrationContainerList = calibrationContainerRepository.findAll();
        assertThat(calibrationContainerList).hasSize(databaseSizeBeforeUpdate);
        CalibrationContainer testCalibrationContainer = calibrationContainerList.get(calibrationContainerList.size() - 1);
        assertThat(testCalibrationContainer.getStrategy()).isEqualTo(UPDATED_STRATEGY);
    }

    @Test
    public void updateNonExistingCalibrationContainer() throws Exception {
        int databaseSizeBeforeUpdate = calibrationContainerRepository.findAll().size();

        // Create the CalibrationContainer
        CalibrationContainerDTO calibrationContainerDTO = calibrationContainerMapper.toDto(calibrationContainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalibrationContainerMockMvc.perform(put("/api/calibration-containers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calibrationContainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CalibrationContainer in the database
        List<CalibrationContainer> calibrationContainerList = calibrationContainerRepository.findAll();
        assertThat(calibrationContainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCalibrationContainer() throws Exception {
        // Initialize the database
        calibrationContainerRepository.save(calibrationContainer);

        int databaseSizeBeforeDelete = calibrationContainerRepository.findAll().size();

        // Delete the calibrationContainer
        restCalibrationContainerMockMvc.perform(delete("/api/calibration-containers/{id}", calibrationContainer.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CalibrationContainer> calibrationContainerList = calibrationContainerRepository.findAll();
        assertThat(calibrationContainerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
