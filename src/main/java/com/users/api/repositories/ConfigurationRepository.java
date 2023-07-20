package com.users.api.repositories;

import com.users.api.models.entities.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    Optional<Configuration> findByPropertyName(String propertyName);
}
