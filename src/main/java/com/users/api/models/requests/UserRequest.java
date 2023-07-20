package com.users.api.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.users.api.annotations.DynamicPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @Schema(example = "Juan Perez", description = "Nombre del usuario")
    @NotBlank(message = "name no puede estar vacío")
    private String name;

    @Schema(example = "correo@gmail.com", description = "correo eléctrónico del usuario", pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @NotBlank(message = "email no puede estar vacío")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email no es válido"
    )
    private String email;

    @Schema(example = "Abcs12345", description = "Constraseña del usuario, se guarda encriptada en la BD y es validada por una expresión regular que se encuentra almacenada en la base de datos")
    @NotBlank(message = "password no puede estar vacío")
    @DynamicPattern(
            property = "regexp.password",
            message = "Password no válido"
    )
    private String password;

    @Schema(description = "Lista de teléfonos que pueda tener el usuario")
    private List<PhoneRequest> phones;
}
