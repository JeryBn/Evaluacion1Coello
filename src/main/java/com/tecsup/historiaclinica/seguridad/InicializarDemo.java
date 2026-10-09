package com.tecsup.historiaclinica.seguridad;

import com.tecsup.historiaclinica.usuarios.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component @Profile("demo")
public class InicializarDemo implements CommandLineRunner {
    private final UsuarioRepository usuarios; private final RolRepository roles;
    private final PasswordEncoder encoder; private final Environment env;
    public InicializarDemo(UsuarioRepository u,RolRepository r,PasswordEncoder p,Environment e){usuarios=u;roles=r;encoder=p;env=e;}
    @Override @Transactional public void run(String... args){
        String[] codigos={"ADMINISTRADOR","MEDICO","RECEPCIONISTA"};
        String[] nombres={"admin","medico","recepcion"};
        for(int i=0;i<codigos.length;i++){
            String codigo=codigos[i];
            Rol rol=roles.findByCodigo(codigo).orElseGet(()->{Rol r=new Rol();r.setCodigo(codigo);r.setNombre(codigo);return roles.save(r);});
            if(usuarios.findByUsername(nombres[i]).isPresent())continue;
            String password=env.getProperty("DEMO_"+codigo+"_PASSWORD");
            if(password==null || password.length()<10 || password.getBytes(java.nio.charset.StandardCharsets.UTF_8).length>72)
                throw new IllegalStateException("Definir DEMO_"+codigo+"_PASSWORD (10 caracteres minimo, 72 bytes maximo)");
            Usuario u=new Usuario();u.setUsername(nombres[i]);u.setPasswordHash(encoder.encode(password));u.setRol(rol);usuarios.save(u);
        }
    }
}
