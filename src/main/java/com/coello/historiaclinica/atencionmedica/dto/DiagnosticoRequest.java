package com.coello.historiaclinica.atencionmedica.dto;

import com.coello.historiaclinica.atencionmedica.entity.TipoDiagnostico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DiagnosticoRequest(
        @NotBlank String codigoCie10,
        @NotBlank String descripcion,
        @NotNull TipoDiagnostico tipoDiagnostico,
        String observaciones
) {
}
