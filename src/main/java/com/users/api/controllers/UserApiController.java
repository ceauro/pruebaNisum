package com.users.api.controllers;

import com.users.api.exceptions.DuplicatedDataException;
import com.users.api.models.requests.UserRequest;
import com.users.api.models.responses.ErrorResponse;
import com.users.api.models.responses.ErrorsResponse;
import com.users.api.models.responses.GeneralResponse;
import com.users.api.models.responses.UserResponse;
import com.users.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Aquí encontraras los endpoints de la API de Usuarios")
@RestController
@RequestMapping(path = "/api/user")
@AllArgsConstructor
@Validated
public class UserApiController {

    private final UserService userService;

    @Operation(
            summary = "Crea nuevo usuario",
            description = "Crea un nuevo usuario y lo almacena en base de datos. Utiliza un token JWT válido para realizar la creación",
            tags = "Users"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorsResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ErrorsResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = { @Content(schema = @Schema()) }) })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<GeneralResponse> postUser(@RequestBody @Valid UserRequest user){
        GeneralResponse response;
        try{
            response = userService.createUser(user);
            return ResponseEntity.ok(response);
        } catch (DuplicatedDataException ex){
            response = new ErrorResponse(ex.getMessage());
           return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
       }
    }

}
