package de.ginisolutions.trader.learning.repository;

import de.ginisolutions.trader.learning.domain.DefaultContainer;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the DefaultContainer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefaultContainerRepository extends MongoRepository<DefaultContainer, String> {
}
