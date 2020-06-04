package de.ginisolutions.trader.learning.web.rest;

import de.ginisolutions.trader.learning.TraderServiceLearningApp;
import de.ginisolutions.trader.learning.config.TestSecurityConfiguration;
import de.ginisolutions.trader.learning.domain.DefaultContainer;
import de.ginisolutions.trader.learning.repository.DefaultContainerRepository;
import de.ginisolutions.trader.learning.service.DefaultContainerService;
import de.ginisolutions.trader.learning.service.dto.DefaultContainerDTO;
import de.ginisolutions.trader.learning.service.mapper.DefaultContainerMapper;

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

import de.ginisolutions.trader.learning.domain.enumeration.STRATEGY;
/**
 * Integration tests for the {@link DefaultContainerResource} REST controller.
 */
@SpringBootTest(classes = { TraderServiceLearningApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DefaultContainerResourceIT {

    private static final STRATEGY DEFAULT_STRATEGY = STRATEGY.SAMPLE_ENUM;
    private static final STRATEGY UPDATED_STRATEGY = STRATEGY.SAMPLE_ENUM;

    @Autowired
    private DefaultContainerRepository defaultContainerRepository;

    @Autowired
    private DefaultContainerMapper defaultContainerMapper;

    @Autowired
    private DefaultContainerService defaultContainerService;

    @Autowired
    private MockMvc restDefaultContainerMockMvc;

    private DefaultContainer defaultContainer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DefaultContainer createEntity() {
        DefaultContainer defaultContainer = new DefaultContainer()
            .strategy(DEFAULT_STRATEGY);
        return defaultContainer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DefaultContainer createUpdatedEntity() {
        DefaultContainer defaultContainer = new DefaultContainer()
            .strategy(UPDATED_STRATEGY);
        return defaultContainer;
    }

    @BeforeEach
    public void initTest() {
        defaultContainerRepository.deleteAll();
        defaultContainer = createEntity();
    }

    @Test
    public void createDefaultContainer() throws Exception {
        int databaseSizeBeforeCreate = defaultContainerRepository.findAll().size();
        // Create the DefaultContainer
        DefaultContainerDTO defaultContainerDTO = defaultContainerMapper.toDto(defaultContainer);
        restDefaultContainerMockMvc.perform(post("/api/default-containers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(defaultContainerDTO)))
            .andExpect(status().isCreated());

        // Validate the DefaultContainer in the database
        List<DefaultContainer> defaultContainerList = defaultContainerRepository.findAll();
        assertThat(defaultContainerList).hasSize(databaseSizeBeforeCreate + 1);
        DefaultContainer testDefaultContainer = defaultContainerList.get(defaultContainerList.size() - 1);
        assertThat(testDefaultContainer.getStrategy()).isEqualTo(DEFAULT_STRATEGY);
    }

    @Test
    public void createDefaultContainerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = defaultContainerRepository.findAll().size();

        // Create the DefaultContainer with an existing ID
        defaultContainer.setId("existing_id");
        DefaultContainerDTO defaultContainerDTO = defaultContainerMapper.toDto(defaultContainer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDefaultContainerMockMvc.perform(post("/api/default-containers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(defaultContainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DefaultContainer in the database
        List<DefaultContainer> defaultContainerList = defaultContainerRepository.findAll();
        assertThat(defaultContainerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllDefaultContainers() throws Exception {
        // Initialize the database
        defaultContainerRepository.save(defaultContainer);

        // Get all the defaultContainerList
        restDefaultContainerMockMvc.perform(get("/api/default-containers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(defaultContainer.getId())))
            .andExpect(jsonPath("$.[*].strategy").value(hasItem(DEFAULT_STRATEGY.toString())));
    }
    
    @Test
    public void getDefaultContainer() throws Exception {
        // Initialize the database
        defaultContainerRepository.save(defaultContainer);

        // Get the defaultContainer
        restDefaultContainerMockMvc.perform(get("/api/default-containers/{id}", defaultContainer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(defaultContainer.getId()))
            .andExpect(jsonPath("$.strategy").value(DEFAULT_STRATEGY.toString()));
    }
    @Test
    public void getNonExistingDefaultContainer() throws Exception {
        // Get the defaultContainer
        restDefaultContainerMockMvc.perform(get("/api/default-containers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDefaultContainer() throws Exception {
        // Initialize the database
        defaultContainerRepository.save(defaultContainer);

        int databaseSizeBeforeUpdate = defaultContainerRepository.findAll().size();

        // Update the defaultContainer
        DefaultContainer updatedDefaultContainer = defaultContainerRepository.findById(defaultContainer.getId()).get();
        updatedDefaultContainer
            .strategy(UPDATED_STRATEGY);
        DefaultContainerDTO defaultContainerDTO = defaultContainerMapper.toDto(updatedDefaultContainer);

        restDefaultContainerMockMvc.perform(put("/api/default-containers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(defaultContainerDTO)))
            .andExpect(status().isOk());

        // Validate the DefaultContainer in the database
        List<DefaultContainer> defaultContainerList = defaultContainerRepository.findAll();
        assertThat(defaultContainerList).hasSize(databaseSizeBeforeUpdate);
        DefaultContainer testDefaultContainer = defaultContainerList.get(defaultContainerList.size() - 1);
        assertThat(testDefaultContainer.getStrategy()).isEqualTo(UPDATED_STRATEGY);
    }

    @Test
    public void updateNonExistingDefaultContainer() throws Exception {
        int databaseSizeBeforeUpdate = defaultContainerRepository.findAll().size();

        // Create the DefaultContainer
        DefaultContainerDTO defaultContainerDTO = defaultContainerMapper.toDto(defaultContainer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDefaultContainerMockMvc.perform(put("/api/default-containers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(defaultContainerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DefaultContainer in the database
        List<DefaultContainer> defaultContainerList = defaultContainerRepository.findAll();
        assertThat(defaultContainerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDefaultContainer() throws Exception {
        // Initialize the database
        defaultContainerRepository.save(defaultContainer);

        int databaseSizeBeforeDelete = defaultContainerRepository.findAll().size();

        // Delete the defaultContainer
        restDefaultContainerMockMvc.perform(delete("/api/default-containers/{id}", defaultContainer.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DefaultContainer> defaultContainerList = defaultContainerRepository.findAll();
        assertThat(defaultContainerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
