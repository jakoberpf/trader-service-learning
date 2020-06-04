package de.ginisolutions.trader.learning.repository;

import de.ginisolutions.trader.learning.domain.CalibrationContainer;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CalibrationContainer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalibrationContainerRepository extends MongoRepository<CalibrationContainer, String> {
}
