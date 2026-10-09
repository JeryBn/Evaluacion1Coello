package com.tecsup.historiaclinica;

import com.coello.historiaclinica.atencionmedica.dto.*;
import com.coello.historiaclinica.atencionmedica.entity.TipoDiagnostico;
import com.coello.historiaclinica.atencionmedica.repository.ConsultaMedicaRepository;
import com.coello.historiaclinica.atencionmedica.service.AtencionMedicaService;
import com.tecsup.historiaclinica.auditoria.*;
import com.tecsup.historiaclinica.model.Paciente;
import com.tecsup.historiaclinica.repository.PacienteRepository;
import com.tecsup.historiaclinica.service.HistoriaClinicaService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.*;
import java.math.BigDecimal;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuditoriaIntegrationTest {
    @Autowired AtencionMedicaService medica;
    @Autowired HistoriaClinicaService historias;
    @Autowired PacienteRepository pacientes;
    @Autowired ConsultaMedicaRepository consultas;
    @Autowired AuditoriaRepository auditoria;
    @Autowired PlatformTransactionManager manager;
    @Autowired MockMvc mvc;
    Long historiaId;

    @BeforeEach void preparar() {
        auditoria.deleteAll();
        var paciente = pacientes.save(new Paciente(UUID.randomUUID().toString(), "Prueba", "Auditoria", "F", "000"));
        historiaId = historias.crearHistoriaClinica(paciente.getId()).getId();
        auditoria.deleteAll();
        var request = new MockHttpServletRequest();
        request.setUserPrincipal(() -> "medico.prueba");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach void limpiarContexto() { RequestContextHolder.resetRequestAttributes(); }

    ConsultaMedicaRequest consulta(Long historia) {
        return new ConsultaMedicaRequest(historia, null, 1L, "Medico prueba", "General", null,
                "Control", "Anamnesis prueba", "Examen prueba", null, null);
    }

    @Test void registraCrudConUsuarioFechaEIdentificador() {
        var registro = medica.registrarConsulta(consulta(historiaId));
        medica.actualizarConsulta(registro.getId(), consulta(historiaId));
        medica.eliminarConsulta(registro.getId());
        var eventos = auditoria.findAll();
        assertThat(eventos).extracting(AuditoriaRegistro::getOperacion)
                .containsExactly(Operacion.REGISTRO, Operacion.MODIFICACION, Operacion.ELIMINACION);
        assertThat(eventos).allSatisfy(e -> {
            assertThat(e.getUsuario()).isEqualTo("medico.prueba");
            assertThat(e.getFechaHora()).isNotNull();
            assertThat(e.getEntidad()).isEqualTo("ConsultaMedica");
            assertThat(e.getRegistroId()).isEqualTo(registro.getId());
        });
        assertThat(consultas.existsById(registro.getId())).isFalse();
    }

    @Test void falloDeRelacionNoGeneraAuditoria() {
        assertThatThrownBy(() -> medica.registrarConsulta(consulta(Long.MAX_VALUE))).isInstanceOf(RuntimeException.class);
        assertThat(auditoria.count()).isZero();
    }

    @Test void rollbackRevierteOperacionYBitacora() {
        long cantidad = consultas.count();
        new TransactionTemplate(manager).executeWithoutResult(status -> {
            medica.registrarConsulta(consulta(historiaId));
            status.setRollbackOnly();
        });
        assertThat(consultas.count()).isEqualTo(cantidad);
        assertThat(auditoria.count()).isZero();
    }

    @Test void signosMantienenRelacionUnicaYDetectanActualizacion() {
        var consulta = medica.registrarConsulta(consulta(historiaId));
        var valores = new SignosVitalesRequest(new BigDecimal("60"), new BigDecimal("1.60"),
                "120/80", 70, 18, new BigDecimal("36.5"), 98);
        var primero = medica.registrarSignosVitales(consulta.getId(), valores);
        var segundo = medica.registrarSignosVitales(consulta.getId(), valores);
        assertThat(segundo.getId()).isEqualTo(primero.getId());
        assertThat(medica.consultarSignosVitales(consulta.getId()).getId()).isEqualTo(primero.getId());
        assertThat(auditoria.findAll()).extracting(AuditoriaRegistro::getOperacion)
                .containsExactly(Operacion.REGISTRO, Operacion.REGISTRO, Operacion.MODIFICACION);
        medica.eliminarConsulta(consulta.getId());
        assertThatThrownBy(() -> medica.consultarSignosVitales(consulta.getId())).isInstanceOf(RuntimeException.class);
    }

    @Test void diagnosticoCrudRelacionadoYLecturaSinAuditar() {
        var consulta = medica.registrarConsulta(consulta(historiaId));
        var dto = new DiagnosticoRequest("Z00", "Prueba", TipoDiagnostico.PRINCIPAL, null);
        var diagnostico = medica.registrarDiagnostico(consulta.getId(), dto);
        medica.actualizarDiagnostico(diagnostico.getId(), dto);
        assertThat(medica.listarDiagnosticos(consulta.getId())).hasSize(1);
        assertThat(auditoria.count()).isEqualTo(3);
        medica.eliminarDiagnostico(diagnostico.getId());
        assertThat(medica.listarDiagnosticos(consulta.getId())).isEmpty();
        assertThat(auditoria.count()).isEqualTo(4);
    }

    @Test void noInventaUsuarioYRenderizaPantallaYApi() throws Exception {
        RequestContextHolder.resetRequestAttributes();
        medica.registrarConsulta(consulta(historiaId));
        assertThat(auditoria.findAll().getFirst().getUsuario()).isEqualTo("NO_AUTENTICADO");
        mvc.perform(get("/auditoria")).andExpect(status().isOk()).andExpect(view().name("auditoria"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("ConsultaMedica")));
        mvc.perform(get("/api/auditoria").param("pagina", "-1"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.pagina").value(0))
                .andExpect(jsonPath("$.registros[0].entidad").value("ConsultaMedica"));
    }
}
