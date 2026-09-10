package com.coello.historiaclinica.atencionmedica.dto;

import jakarta.validation.constraints.NotBlank;

public record EvolucionMedicaRequest(
        @NotBlank String descripcion,
        String plan,
        @NotBlank String medicoResponsable
) {
}
