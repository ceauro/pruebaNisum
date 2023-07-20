package com.users.api.controller;

import com.users.api.controllers.UserApiController;
import com.users.api.exceptions.DuplicatedDataException;
import com.users.api.models.requests.PhoneRequest;
import com.users.api.models.requests.UserRequest;
import com.users.api.models.responses.ErrorResponse;
import com.users.api.models.responses.GeneralResponse;
import com.users.api.models.responses.UserResponse;
import com.users.api.services.UserService;
import com.users.api.utils.Ctes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserApiControllerTest {
    private UserApiController controller;
    private UserService userServiceMock;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setup(){
        userServiceMock = Mockito.mock(UserService.class);
        controller = new UserApiController(userServiceMock);

        PhoneRequest phoneRequest = PhoneRequest.builder()
                .number("1234")
                .countryCode("1")
                .cityCode("1").build();
        userRequest = UserRequest.builder()
                .email("mock email")
                .name("mock name")
                .phones(List.of(phoneRequest))
                .password("1234").build();
        userResponse = UserResponse.builder()
                .id("mock id")
                .token("token id")
                .created(new Date())
                .isActive(true)
                .lastLogin(new Date())
                .modified(new Date()).build();
    }

    @Test
    void testWhenItIsAnRequestOk() throws DuplicatedDataException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(userServiceMock.createUser(any(UserRequest.class))).thenReturn(userResponse);

        // Act
        ResponseEntity<GeneralResponse> response = controller.postUser(userRequest);

        // Asserts
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(userResponse);
    }

    @Test
    void testWhenTheEmailExists() throws DuplicatedDataException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(userServiceMock.createUser(any(UserRequest.class))).thenThrow(new DuplicatedDataException(Ctes.Services.EXISTS_EMAIL_ERROR));

        // Act
        ResponseEntity<GeneralResponse> response = controller.postUser(userRequest);

        // Asserts
        assertThat(response.getStatusCode().value()).isEqualTo(409);
        assertThat(((ErrorResponse)response.getBody()).getMessage()).isEqualTo(Ctes.Services.EXISTS_EMAIL_ERROR);
    }
}
