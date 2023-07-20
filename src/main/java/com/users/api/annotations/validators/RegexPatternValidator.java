package com.users.api.annotations.validators;

import com.users.api.annotations.DynamicPattern;
import com.users.api.exceptions.MissingConfigurationException;
import com.users.api.services.ConfigurationService;
import com.users.api.utils.Ctes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.regex.Pattern;

public class RegexPatternValidator implements ConstraintValidator<DynamicPattern, String> {
    private String regexPattern;

    @Autowired
    private ConfigurationService service;

    @SneakyThrows
    @Override
    public void initialize(DynamicPattern constraintAnnotation) {
        regexPattern = service.getProperty(constraintAnnotation.property());

        if(Strings.isBlank(regexPattern))
            throw new MissingConfigurationException(Ctes.Validators.NO_EXISTS_PARAMETRIZATION_ERROR);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(Strings.isNotBlank(value))
            return Pattern.compile(regexPattern).matcher(value).matches();

        return false;
    }
}
