package com.tecsup.historiaclinica.usuarios;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class UsuariosController {
    private final GestionUsuariosService service;
    public UsuariosController(GestionUsuariosService service){this.service=service;}
    @GetMapping("/admin/usuarios")
    public String usuarios(Model m,@RequestParam(required=false) Long editar){
        m.addAttribute("usuarios",service.usuarios());m.addAttribute("roles",service.roles());
        m.addAttribute("edicion",editar==null?null:service.usuario(editar));return "usuarios";
    }
    @GetMapping("/admin/roles")
    public String roles(Model m,@RequestParam(required=false) Long editar){
        m.addAttribute("roles",service.roles());m.addAttribute("edicion",editar==null?null:service.rol(editar));return "roles";
    }
    @PostMapping("/admin/usuarios")
    public String guardarUsuario(@RequestParam(required=false) Long id,@RequestParam String username,
            @RequestParam(defaultValue="") String password,@RequestParam Long rolId,RedirectAttributes flash){
        try {if(id==null)service.crearUsuario(username,password,rolId);else service.editarUsuario(id,username,password,rolId);
            flash.addFlashAttribute("mensaje","Usuario guardado");}
        catch(IllegalArgumentException e){flash.addFlashAttribute("error",e.getMessage());}
        return "redirect:/admin/usuarios";
    }
    @PostMapping("/admin/usuarios/{id}/estado")
    public String estadoUsuario(@PathVariable Long id,@RequestParam boolean activo,RedirectAttributes flash){
        try{service.estadoUsuario(id,activo);}catch(IllegalArgumentException e){flash.addFlashAttribute("error",e.getMessage());}
        return "redirect:/admin/usuarios";
    }
    @PostMapping("/admin/roles")
    public String guardarRol(@RequestParam(required=false) Long id,@RequestParam(defaultValue="") String codigo,
            @RequestParam String nombre,RedirectAttributes flash){
        try{if(id==null)service.crearRol(codigo,nombre);else service.editarRol(id,nombre);flash.addFlashAttribute("mensaje","Rol guardado");}
        catch(IllegalArgumentException e){flash.addFlashAttribute("error",e.getMessage());}
        return "redirect:/admin/roles";
    }
    @PostMapping("/admin/roles/{id}/estado")
    public String estadoRol(@PathVariable Long id,@RequestParam boolean activo,RedirectAttributes flash){
        try{service.estadoRol(id,activo);}catch(IllegalArgumentException e){flash.addFlashAttribute("error",e.getMessage());}
        return "redirect:/admin/roles";
    }
    public record UsuarioDatos(String username,String password,Long rolId){}
    public record RolDatos(String codigo,String nombre){}
    public record Estado(boolean activo){}
    @GetMapping("/api/admin/usuarios") @ResponseBody public List<Usuario> lista(){return service.usuarios();}
    @PostMapping("/api/admin/usuarios") @ResponseBody
    public Usuario crear(@RequestBody UsuarioDatos d){return service.crearUsuario(d.username(),d.password(),d.rolId());}
    @PutMapping("/api/admin/usuarios/{id}") @ResponseBody
    public Usuario editar(@PathVariable Long id,@RequestBody UsuarioDatos d){return service.editarUsuario(id,d.username(),d.password(),d.rolId());}
    @PatchMapping("/api/admin/usuarios/{id}/estado") @ResponseBody
    public Usuario estado(@PathVariable Long id,@RequestBody Estado d){return service.estadoUsuario(id,d.activo());}
    @GetMapping("/api/admin/roles") @ResponseBody public List<Rol> listaRoles(){return service.roles();}
    @PostMapping("/api/admin/roles") @ResponseBody
    public Rol crearRol(@RequestBody RolDatos d){return service.crearRol(d.codigo(),d.nombre());}
    @PutMapping("/api/admin/roles/{id}") @ResponseBody
    public Rol editarRol(@PathVariable Long id,@RequestBody RolDatos d){return service.editarRol(id,d.nombre());}
    @PatchMapping("/api/admin/roles/{id}/estado") @ResponseBody
    public Rol estadoRol(@PathVariable Long id,@RequestBody Estado d){return service.estadoRol(id,d.activo());}
}
