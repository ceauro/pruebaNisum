package com.users.api.services;

import com.users.api.models.entities.Configuration;
import com.users.api.repositories.ConfigurationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigurationServiceTest {

    @InjectMocks
    private ConfigurationService service;

    @Mock
    private ConfigurationRepository repository;

    @Test
    void testWhenExistsProperty(){
        // Act
        String propNameMocked = "propNameMocked";
        String propValueMocked = "propValueMocked";
        Optional<Configuration> mockConfiguration = Optional.of(new Configuration(1L,propNameMocked, propValueMocked));
        when(repository.findByPropertyName(anyString())).thenReturn(mockConfiguration);

        // Arrange
        String propertyName = service.getProperty(propNameMocked);

        // Assert
        assertThat(propertyName)
                .isNotBlank()
                .isEqualTo(propValueMocked);
    }

    @Test
    void testWhenNotExistsProperty(){
        // Act
        String propNameMocked = "propNameMocked";
        Optional<Configuration> mockConfiguration = Optional.empty();
        when(repository.findByPropertyName(anyString())).thenReturn(mockConfiguration);

        // Arrange
        String propertyName = service.getProperty(propNameMocked);

        // Assert
        assertThat(propertyName).isBlank();
    }
}
