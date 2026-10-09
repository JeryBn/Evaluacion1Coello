package com.tecsup.historiaclinica;

import com.tecsup.historiaclinica.usuarios.*;
import com.tecsup.historiaclinica.auditoria.AuditoriaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest @AutoConfigureMockMvc
class SeguridadIntegrationTest {
    @Autowired MockMvc mvc;
    @Autowired UsuarioRepository usuarios;
    @Autowired RolRepository roles;
    @Autowired PasswordEncoder encoder;
    @Autowired GestionUsuariosService gestion;
    @Autowired AuditoriaRepository auditoria;
    final String clave="PruebaLocal2026!";
    @BeforeEach void preparar(){
        usuarios.deleteAll();roles.deleteAll();auditoria.deleteAll();
        String[] codigos={"ADMINISTRADOR","MEDICO","RECEPCIONISTA"};
        String[] nombres={"admin","medico","recepcion"};
        for(int i=0;i<codigos.length;i++){
            Rol rol=new Rol();rol.setCodigo(codigos[i]);rol.setNombre(codigos[i]);roles.save(rol);
            Usuario u=new Usuario();u.setUsername(nombres[i]);u.setPasswordHash(encoder.encode(clave));u.setRol(rol);usuarios.save(u);
        }
    }
    @Test void permisosRealesYMenu() throws Exception {
        mvc.perform(get("/api/admin/usuarios")).andExpect(status().isUnauthorized());
        mvc.perform(get("/admin/usuarios").with(httpBasic("medico",clave))).andExpect(status().isForbidden());
        mvc.perform(get("/api/historias-clinicas").with(httpBasic("recepcion",clave))).andExpect(status().isForbidden());
        mvc.perform(get("/api/pacientes").with(httpBasic("recepcion",clave))).andExpect(status().isOk());
        mvc.perform(get("/").with(httpBasic("recepcion",clave))).andExpect(status().isOk())
            .andExpect(content().string(not(containsString("href=\"/admin/usuarios\""))))
            .andExpect(content().string(not(containsString("href=\"/historias-clinicas\""))));
        mvc.perform(get("/admin/usuarios").with(httpBasic("admin",clave))).andExpect(status().isOk());
        mvc.perform(get("/admin/roles").with(httpBasic("admin",clave))).andExpect(status().isOk());
        mvc.perform(get("/api/admin/usuarios").with(httpBasic("admin",clave))).andExpect(status().isOk())
            .andExpect(content().string(not(containsString("passwordHash"))));
    }
    @Test void csrfYAuditoriaDeUsuarioReal() throws Exception {
        String json="{\"codigo\":\"CONSULTOR\",\"nombre\":\"Consultor\"}";
        mvc.perform(post("/api/admin/roles").with(httpBasic("admin",clave)).contentType("application/json").content(json))
            .andExpect(status().isForbidden());
        mvc.perform(post("/api/admin/roles").with(httpBasic("admin",clave)).with(csrf()).contentType("application/json").content(json))
            .andExpect(status().isOk()).andExpect(jsonPath("$.codigo").value("CONSULTOR"));
        assertThat(auditoria.findAll()).anySatisfy(a->assertThat(a.getUsuario()).isEqualTo("admin"));
    }
    @Test void gestionCompletaYUltimoAdministrador(){
        var rol=gestion.crearRol("AUXILIAR","Auxiliar");
        var u=gestion.crearUsuario("auxiliar",clave,rol.getId());
        assertThat(encoder.matches(clave,u.getPasswordHash())).isTrue();
        gestion.editarUsuario(u.getId(),"auxiliar.editado","",rol.getId());
        gestion.estadoUsuario(u.getId(),false);
        assertThat(usuarios.findById(u.getId()).orElseThrow().isActivo()).isFalse();
        gestion.estadoUsuario(u.getId(),true);
        gestion.editarRol(rol.getId(),"Auxiliar actualizado");
        gestion.estadoRol(rol.getId(),false);
        assertThatThrownBy(()->gestion.crearUsuario("invalido",clave,rol.getId())).isInstanceOf(IllegalArgumentException.class);
        var admin=usuarios.findByUsername("admin").orElseThrow();
        assertThatThrownBy(()->gestion.estadoUsuario(admin.getId(),false)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(()->gestion.estadoRol(admin.getRol().getId(),false)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test void loginRedireccionYSesionRevocada() throws Exception {
        var login=mvc.perform(post("/login").with(csrf()).param("username","medico").param("password",clave))
            .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/")).andReturn();
        var session=(org.springframework.mock.web.MockHttpSession)login.getRequest().getSession(false);
        mvc.perform(get("/historias-clinicas").session(session)).andExpect(status().isOk());
        gestion.estadoUsuario(usuarios.findByUsername("medico").orElseThrow().getId(),false);
        mvc.perform(get("/historias-clinicas").session(session)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login?error"));
        mvc.perform(get("/api/pacientes").with(httpBasic("medico",clave))).andExpect(status().isUnauthorized());
    }
    @Test void rolInactivoDeniegaLogin() throws Exception {
        gestion.estadoRol(roles.findByCodigo("MEDICO").orElseThrow().getId(),false);
        mvc.perform(get("/api/pacientes").with(httpBasic("medico",clave))).andExpect(status().isUnauthorized());
    }
    @Test void relacionMagalyCrudSinRecursion() throws Exception {
        String paciente=mvc.perform(post("/api/pacientes").with(httpBasic("medico",clave)).with(csrf())
            .contentType("application/json").content("{\"dni\":\"DEMO-TEST\",\"nombres\":\"Prueba\",\"apellidos\":\"Relacion\"}"))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var mapper=new com.fasterxml.jackson.databind.ObjectMapper();
        long pacienteId=mapper.readTree(paciente).get("id").asLong();
        String historia=mvc.perform(post("/api/historias-clinicas/paciente/"+pacienteId).with(httpBasic("medico",clave)).with(csrf()))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        long h=mapper.readTree(historia).get("id").asLong();
        String atencion=mvc.perform(post("/api/historias-clinicas/"+h+"/atenciones").with(httpBasic("medico",clave)).with(csrf()).param("motivo","Prueba"))
            .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        long a=mapper.readTree(atencion).get("id").asLong();
        mvc.perform(get("/api/historias-clinicas/"+h).with(httpBasic("medico",clave)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.atenciones[0].id").value(a))
            .andExpect(jsonPath("$.atenciones[0].historiaClinica").doesNotExist());
        mvc.perform(put("/api/historias-clinicas/"+h+"/atenciones/"+a).with(httpBasic("medico",clave)).with(csrf()).param("motivo","Actualizado"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.motivo").value("Actualizado"));
        mvc.perform(delete("/api/historias-clinicas/"+(h+999)+"/atenciones/"+a).with(httpBasic("medico",clave)).with(csrf()))
            .andExpect(status().isNotFound());
        mvc.perform(delete("/api/historias-clinicas/"+h+"/atenciones/"+a).with(httpBasic("medico",clave)).with(csrf()))
            .andExpect(status().isNoContent());
    }
}
