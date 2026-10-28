package com.tecsup.historiaclinica;

import com.coello.historiaclinica.atencionmedica.entity.SignosVitales;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SignosVitalesJsonTest {
    @Test void respuestaIncluyeImcEInterpretacion() throws Exception {
        SignosVitales signos = new SignosVitales();
        signos.setPesoKg(new BigDecimal("68.5"));
        signos.setTallaMetros(new BigDecimal("1.70"));
        var json = new ObjectMapper().valueToTree(signos);
        assertEquals(0, new BigDecimal("23.70").compareTo(json.get("imc").decimalValue()));
        assertEquals("Peso normal", json.get("interpretacionImc").asText());
    }
}
