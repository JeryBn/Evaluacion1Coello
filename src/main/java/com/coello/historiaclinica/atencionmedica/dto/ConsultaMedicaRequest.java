package com.coello.historiaclinica.atencionmedica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaMedicaRequest(
        @NotNull Long historiaClinicaId,
        String codigoCita,
        @NotNull Long medicoId,
        @NotBlank String nombreMedico,
        @NotBlank String especialidad,
        LocalDateTime fechaAtencion,
        @NotBlank String motivoConsulta,
        @NotBlank String anamnesis,
        @NotBlank String examenFisico,
        String evaluacionClinica,
        String observaciones
) {
}
