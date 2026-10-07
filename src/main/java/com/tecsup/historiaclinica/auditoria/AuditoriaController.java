package com.tecsup.historiaclinica.auditoria;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class AuditoriaController {
    private final AuditoriaService service;
    public AuditoriaController(AuditoriaService service) { this.service = service; }

    @GetMapping("/auditoria")
    public String pantalla(@RequestParam(defaultValue = "0") int pagina, Model model) {
        model.addAttribute("registros", service.listar(pagina));
        return "auditoria";
    }

    @GetMapping("/api/auditoria")
    @ResponseBody
    public Resultado listar(@RequestParam(defaultValue = "0") int pagina) {
        var resultado = service.listar(pagina);
        return new Resultado(resultado.getContent(), resultado.getNumber(), resultado.getTotalPages(),
                resultado.getTotalElements());
    }

    public record Resultado(List<AuditoriaRegistro> registros, int pagina, int totalPaginas, long total) {}
}
