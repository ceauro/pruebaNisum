package com.users.api.services;

import com.users.api.exceptions.DuplicatedDataException;
import com.users.api.models.entities.User;
import com.users.api.models.requests.PhoneRequest;
import com.users.api.models.requests.UserRequest;
import com.users.api.models.responses.ErrorResponse;
import com.users.api.models.responses.UserResponse;
import com.users.api.repositories.UserRepository;
import com.users.api.utils.Ctes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService service;
    private UserRepository userRepository;
    private PhoneService phoneService;
    private ModelMapper modelMapper = new ModelMapper();
    private UserRequest userRequest;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        phoneService = Mockito.mock(PhoneService.class);
        service = new UserService(userRepository, phoneService, modelMapper, new BCryptPasswordEncoder());
        modelMapper = new ModelMapper();
        PhoneRequest phoneRequest = PhoneRequest.builder()
                .number("1234")
                .countryCode("1")
                .cityCode("1").build();
        userRequest = UserRequest.builder()
                .email("mock email")
                .name("mock name")
                .phones(List.of(phoneRequest))
                .password("1234").build();

    }

    @Test
    void testWhenCreateAnUserItIsOk() throws DuplicatedDataException {
        // Arrange
        String uuid = "550e8400-e29b-41d4-a716-446655440000";
        String mockedToken = "mocked token";
        User userMocked = getUserEntity(userRequest, uuid, mockedToken);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userMocked);
        doNothing().when(phoneService).savePhones(isA(User.class), isA(List.class));

        // Act
        UserResponse actualUser = (UserResponse) service.createUser(userRequest);

        // Assert
        assertThat(actualUser.getId()).isNotBlank().isEqualTo(uuid);
        assertThat(actualUser.getToken()).isNotBlank().isEqualTo(mockedToken);
        assertThat(actualUser.isActive()).isTrue();
        verify(phoneService, times(1)).savePhones(userMocked, userMocked.getPhones());
    }

    @Test
    void testWhenCreateAnTheUserExists() throws DuplicatedDataException {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // Act
        DuplicatedDataException error = assertThrows(DuplicatedDataException.class, () ->{
            service.createUser(userRequest);
        });

        // Assert
        assertThat(error.getMessage()).isNotBlank().isEqualTo(Ctes.Services.EXISTS_EMAIL_ERROR);
    }

    private User getUserEntity(UserRequest user, String uuid, String mockedToken) {
        User userEntity = modelMapper.map(user, User.class);
        userEntity.setId(UUID.fromString(uuid));
        userEntity.setCreatedDate(new Date());
        userEntity.setLastLoginDate(new Date());
        userEntity.setActive(true);
        userEntity.setPassword(userEntity.getPassword());
        userEntity.setToken(mockedToken);
        return userEntity;
    }
}