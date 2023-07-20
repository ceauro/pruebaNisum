package com.users.api.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorsResponse implements GeneralResponse {
    @Schema(description = "Lista de errores ocurridos")
    private List<ErrorResponse> errors;
}
