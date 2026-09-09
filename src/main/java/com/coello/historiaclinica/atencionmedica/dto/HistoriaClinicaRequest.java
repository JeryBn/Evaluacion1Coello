package com.coello.historiaclinica.atencionmedica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HistoriaClinicaRequest(
        @NotBlank String numeroHistoria,
        @NotNull Long pacienteId
) {
}
