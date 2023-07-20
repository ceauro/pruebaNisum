package com.users.api.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse implements GeneralResponse {
    @Schema(example = "de890b7e-262a-11ee-be56-0242ac120002", description = "Id del usuario creado en la base de datos")
    private String id;

    @Schema(example = "2023-07-19T11:04:54.975+00:00", description = "Fecha en la que se crea el usuario en la base de datos")
    private Date created;

    @Schema(example = "2023-07-19T11:04:54.975+00:00", description = "Fecha en la que se modifica el usuario en la base de datos")
    private Date modified;

    @Schema(example = "2023-07-19T11:04:54.975+00:00", description = "Fecha en la que el usuario hizo el último login")
    @JsonProperty(value = "last_login")
    private Date lastLogin;

    @Schema(example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1.YLH8wIsOAfdsWk", description = "Token JWT del usuario creado el cuál puede ser usado para utilizar la api")
    private String token;

    @Schema(example = "true", description = "Indica si el usuario está activo o inactivo en la base de datos")
    @JsonProperty(value = "isactive")
    private boolean isActive;
}
