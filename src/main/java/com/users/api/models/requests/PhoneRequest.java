package com.users.api.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneRequest {
    @Schema(example = "4567834", description = "Número del teléfono del usuario")
    private String number;

    @Schema(example = "57", description = "Indicativo de la ciudad")
    @JsonProperty("citycode")
    private String cityCode;

    @Schema(example = "1", description = "Indicativo del país")
    @JsonProperty("contrycode")
    private String countryCode;
}
