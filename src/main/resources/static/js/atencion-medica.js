const baseUrl = "/api/atencion-medica";
let historiaId = null;
let consultaId = null;

const value = (id) => document.getElementById(id).value;
const number = (id) => Number(value(id));
const notice = document.getElementById("notice");

function message(text, error = false) {
    notice.textContent = text;
    notice.style.background = error ? "#fae8e8" : "#e7f4f2";
    notice.style.borderColor = error ? "#bf4b4b" : "#2a9d8f";
    notice.style.color = error ? "#8b2525" : "#245a58";
}

async function request(path, method, body) {
    const response = await fetch(`${baseUrl}${path}`, {
        method,
        headers: { "Content-Type": "application/json" },
        body: body ? JSON.stringify(body) : undefined
    });
    if (!response.ok) {
        const error = await response.json().catch(() => ({}));
        throw new Error(error.mensaje || "No fue posible guardar la informacion.");
    }
    return response.status === 204 ? null : response.json();
}

function requireConsulta() {
    if (!consultaId) {
        message("Primero registra una consulta medica.", true);
        return false;
    }
    return true;
}

document.getElementById("consultForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    try {
        const consulta = await request("/consultas", "POST", {
            historiaClinicaId: number("consultaHistoriaId"), codigoCita: value("codigoCita"),
            medicoId: number("medicoId"), nombreMedico: value("nombreMedico"),
            especialidad: value("especialidad"), motivoConsulta: value("motivoConsulta"),
            anamnesis: value("anamnesis"), examenFisico: value("examenFisico"),
            evaluacionClinica: value("evaluacionClinica")
        });
        consultaId = consulta.id;
        document.getElementById("consultaId").textContent = consultaId;
        document.getElementById("clinicalArea").setAttribute("aria-disabled", "false");
        message("Consulta registrada. Ya puedes completar la atencion medica.");
    } catch (error) { message(error.message, true); }
});

document.getElementById("vitalsForm").addEventListener("submit", async (event) => {
    event.preventDefault(); if (!requireConsulta()) return;
    try { const result = await request(`/consultas/${consultaId}/signos-vitales`, "POST", { pesoKg: number("pesoKg"), tallaMetros: number("tallaMetros"), presionArterial: value("presionArterial"), frecuenciaCardiaca: number("frecuenciaCardiaca"), frecuenciaRespiratoria: number("frecuenciaRespiratoria"), temperatura: number("temperatura"), saturacionOxigeno: number("saturacionOxigeno") }); message(`Signos vitales guardados. IMC: ${result.imc} (${result.interpretacionImc}).`); } catch (error) { message(error.message, true); }
});

document.getElementById("diagnosisForm").addEventListener("submit", async (event) => {
    event.preventDefault(); if (!requireConsulta()) return;
    try { await request(`/consultas/${consultaId}/diagnosticos`, "POST", { codigoCie10: value("codigoCie10"), descripcion: value("descripcionDiagnostico"), tipoDiagnostico: value("tipoDiagnostico") }); message("Diagnostico guardado."); } catch (error) { message(error.message, true); }
});

document.getElementById("treatmentForm").addEventListener("submit", async (event) => {
    event.preventDefault(); if (!requireConsulta()) return;
    try { await request(`/consultas/${consultaId}/tratamientos`, "POST", { tratamientoIndicado: value("tratamientoIndicado"), indicaciones: value("indicaciones"), duracion: value("duracion") }); message("Tratamiento guardado."); } catch (error) { message(error.message, true); }
});

document.getElementById("evolutionForm").addEventListener("submit", async (event) => {
    event.preventDefault(); if (!requireConsulta()) return;
    try { await request(`/consultas/${consultaId}/evoluciones`, "POST", { descripcion: value("descripcionEvolucion"), plan: value("plan"), medicoResponsable: value("medicoResponsable") }); message("Evolucion medica guardada."); } catch (error) { message(error.message, true); }
});
