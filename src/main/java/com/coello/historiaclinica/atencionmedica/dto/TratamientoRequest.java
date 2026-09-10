package com.coello.historiaclinica.atencionmedica.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record TratamientoRequest(
        @NotBlank String tratamientoIndicado,
        String indicaciones,
        String duracion,
        String recomendaciones,
        LocalDate fechaInicio,
        LocalDate fechaFinalizacion,
        String observaciones
) {
}
