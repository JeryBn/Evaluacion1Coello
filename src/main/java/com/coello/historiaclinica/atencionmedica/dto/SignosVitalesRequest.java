package com.coello.historiaclinica.atencionmedica.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SignosVitalesRequest(
        @DecimalMin("0.1") BigDecimal pesoKg,
        @DecimalMin("0.1") BigDecimal tallaMetros,
        @NotBlank String presionArterial,
        @NotNull Integer frecuenciaCardiaca,
        @NotNull Integer frecuenciaRespiratoria,
        @NotNull BigDecimal temperatura,
        @NotNull Integer saturacionOxigeno
) {
}
