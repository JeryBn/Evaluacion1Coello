package com.tecsup.historiaclinica.usuarios;

import com.tecsup.historiaclinica.auditoria.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Set;

@Service @Transactional
public class GestionUsuariosService {
    private final UsuarioRepository usuarios;
    private final RolRepository roles;
    private final PasswordEncoder encoder;
    private static final Set<String> SISTEMA=Set.of("ADMINISTRADOR","MEDICO","RECEPCIONISTA");
    public GestionUsuariosService(UsuarioRepository u,RolRepository r,PasswordEncoder p){usuarios=u;roles=r;encoder=p;}
    public List<Usuario> usuarios(){return usuarios.findAll();}
    public List<Rol> roles(){return roles.findAll();}
    public Usuario usuario(Long id){return usuarios.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado"));}
    public Rol rol(Long id){return roles.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Rol no encontrado"));}
    private void validar(boolean condicion,String mensaje){if(!condicion)throw new IllegalArgumentException(mensaje);}
    @Auditar(entidad=Usuario.class, operacion=Operacion.REGISTRO)
    public Usuario crearUsuario(String username,String password,Long rolId){return guardar(new Usuario(),username,password,rolId);}
    @Auditar(entidad=Usuario.class, operacion=Operacion.MODIFICACION)
    public Usuario editarUsuario(Long id,String username,String password,Long rolId){
        roles.bloquearAdministrador();
        var u=usuario(id);
        protegerAdministrador(u,rolId,u.isActivo());
        return guardar(u,username,password,rolId);
    }
    private Usuario guardar(Usuario u,String username,String password,Long rolId){
        validar(username!=null && username.matches("[a-zA-Z0-9._-]{3,50}"),"Usuario: 3 a 50 letras, numeros, punto, guion o guion bajo");
        username=username.toLowerCase(java.util.Locale.ROOT);
        var existente=usuarios.findByUsername(username);
        validar(existente.isEmpty() || existente.get().getId().equals(u.getId()),"El usuario ya existe");
        var r=rol(rolId); validar(r.isActivo(),"El rol esta inactivo");
        if(u.getId()==null || (password!=null && !password.isBlank())){
            validar(password!=null && password.length()>=10 && password.getBytes(java.nio.charset.StandardCharsets.UTF_8).length<=72,"La clave debe tener al menos 10 caracteres y hasta 72 bytes");
            u.setPasswordHash(encoder.encode(password));
        }
        u.setUsername(username);u.setRol(r);return usuarios.save(u);
    }
    private void protegerAdministrador(Usuario u,Long nuevoRol,boolean activo){
        if(u.isActivo() && u.getRol().getCodigo().equals("ADMINISTRADOR") &&
                (!activo || !u.getRol().getId().equals(nuevoRol)))
            validar(usuarios.countByActivoTrueAndRolCodigo("ADMINISTRADOR")>1,"No se puede desactivar ni cambiar el rol del ultimo administrador");
    }
    @Auditar(entidad=Usuario.class, operacion=Operacion.MODIFICACION)
    public Usuario estadoUsuario(Long id,boolean activo){
        roles.bloquearAdministrador();var u=usuario(id);protegerAdministrador(u,u.getRol().getId(),activo);
        validar(!activo || u.getRol().isActivo(),"El rol esta inactivo");u.setActivo(activo);return usuarios.save(u);
    }
    @Auditar(entidad=Rol.class, operacion=Operacion.REGISTRO)
    public Rol crearRol(String codigo,String nombre){
        validar(codigo!=null && codigo.matches("[A-Z][A-Z0-9_]{2,39}"),"Codigo de rol: 3 a 40 caracteres en mayusculas");
        validar(roles.findByCodigo(codigo).isEmpty(),"El rol ya existe");
        Rol r=new Rol();r.setCodigo(codigo);return guardarRol(r,nombre);
    }
    @Auditar(entidad=Rol.class, operacion=Operacion.MODIFICACION)
    public Rol editarRol(Long id,String nombre){return guardarRol(rol(id),nombre);}
    private Rol guardarRol(Rol r,String nombre){validar(nombre!=null && !nombre.isBlank() && nombre.length()<=80,"Nombre de rol requerido, maximo 80 caracteres");r.setNombre(nombre.trim());return roles.save(r);}
    @Auditar(entidad=Rol.class, operacion=Operacion.MODIFICACION)
    public Rol estadoRol(Long id,boolean activo){
        roles.bloquearAdministrador();Rol r=rol(id);
        validar(activo || !r.getCodigo().equals("ADMINISTRADOR"),"El rol ADMINISTRADOR debe permanecer activo");
        r.setActivo(activo);return roles.save(r);
    }
}
