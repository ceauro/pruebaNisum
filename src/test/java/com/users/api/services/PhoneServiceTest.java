package com.users.api.services;

import com.users.api.models.entities.Phone;
import com.users.api.models.entities.User;
import com.users.api.repositories.PhoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhoneServiceTest {

    @InjectMocks
    private PhoneService service;

    @Mock
    private PhoneRepository repository;

    @Test
    void testPhonesSaved(){
        // Arrange
        User userMocked = new User();
        Phone phoneMocked = new Phone();
        phoneMocked.setNumber("1");
        phoneMocked.setCountryCode("1");
        phoneMocked.setCityCode("1");
        when(repository.save(any(Phone.class))).thenReturn(phoneMocked);

        // Act
        service.savePhones(userMocked, List.of(phoneMocked));

        // Assert
        verify(repository, times(1)).save(phoneMocked);
    }

}
