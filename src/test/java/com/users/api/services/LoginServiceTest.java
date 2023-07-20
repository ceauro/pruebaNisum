package com.users.api.services;

import com.users.api.models.AuthUser;
import com.users.api.models.entities.User;
import com.users.api.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @InjectMocks
    private LoginService service;

    @Mock
    private UserRepository repository;

    @Test
    void testWhenExistsUser(){
        // Arrange
        String emailMocked = "emailMocked";
        String passMocked = "anyPassword";
        String nameMocked = "anyName";
        User userMocked = new User();
        userMocked.setEmail(emailMocked);
        userMocked.setPassword(passMocked);
        userMocked.setName(nameMocked);
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(userMocked));

        // Act
        AuthUser userDetails = (AuthUser) service.loadUserByUsername(emailMocked);

        // Assert
        assertThat(userDetails.getPassword()).isNotBlank().isEqualTo(passMocked);
        assertThat(userDetails.getUsername()).isNotBlank().isEqualTo(emailMocked);
        assertThat(userDetails.getName()).isNotBlank().isEqualTo(nameMocked);
    }

    @Test
    void testWhenNotExistsUser(){
        // Arrange
        String emailMocked = "emailMocked";
        String errorMessage = "El usuario " + emailMocked +" no existe.";
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act
        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(emailMocked);
        });

        // Assert
        assertThat(ex.getMessage()).isNotBlank().isEqualTo(errorMessage);
    }
}
