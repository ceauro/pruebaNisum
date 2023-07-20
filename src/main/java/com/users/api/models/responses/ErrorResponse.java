package com.users.api.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements GeneralResponse {
    @Schema(example = "mensaje de error", description = "describe el error ocurrido")
    private String message;
}
