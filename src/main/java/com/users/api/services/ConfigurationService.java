package com.users.api.services;

import com.users.api.models.entities.Configuration;
import com.users.api.repositories.ConfigurationRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository repository;

    public String getProperty(String name){
        Optional<Configuration> conf = repository.findByPropertyName(name);
        if(conf.isPresent())
            return conf.get().getPropertyValue();

        return Strings.EMPTY;
    }
}
