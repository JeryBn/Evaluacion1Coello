package com.tecsup.historiaclinica.seguridad;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.csrf.CsrfToken;
@Controller
public class AccesoController {
    @GetMapping("/login") public String login(){return "login";}
    @GetMapping("/api/csrf") @ResponseBody public CsrfToken csrf(CsrfToken token){return token;}
}
