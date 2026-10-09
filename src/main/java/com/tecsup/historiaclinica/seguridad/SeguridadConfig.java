package com.tecsup.historiaclinica.seguridad;

import com.tecsup.historiaclinica.usuarios.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.Customizer;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SeguridadConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean UserDetailsService userDetails(UsuarioRepository repository){
        return name -> {
            Usuario u=repository.findByUsername(name.toLowerCase(java.util.Locale.ROOT))
                    .orElseThrow(()->new UsernameNotFoundException("Credenciales incorrectas"));
            return User.withUsername(u.getUsername()).password(u.getPasswordHash())
                    .roles(u.getRol().getCodigo()).disabled(!u.isActivo() || !u.getRol().isActivo()).build();
        };
    }
    @Bean SecurityFilterChain security(HttpSecurity http,UsuarioRepository usuarios) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/login","/css/**","/js/**","/error").permitAll()
            .requestMatchers("/admin/**","/api/admin/**","/auditoria","/api/auditoria").hasRole("ADMINISTRADOR")
            .requestMatchers("/pacientes","/pacientes/**","/api/pacientes","/api/pacientes/**",
                    "/historias-clinicas/pacientes/nuevo").hasAnyRole("ADMINISTRADOR","MEDICO","RECEPCIONISTA")
            .requestMatchers("/historias-clinicas/**","/api/historias-clinicas/**","/atencion-medica/**","/api/atencion-medica/**")
                    .hasAnyRole("ADMINISTRADOR","MEDICO")
            .requestMatchers("/","/api/csrf","/logout").authenticated()
            .anyRequest().denyAll())
            .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/",true).permitAll())
            .httpBasic(Customizer.withDefaults())
            .exceptionHandling(e -> e.defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED), new AntPathRequestMatcher("/api/**")))
            .logout(logout -> logout.logoutSuccessUrl("/login?logout"))
            .addFilterAfter(new VigenciaUsuarioFilter(usuarios), AnonymousAuthenticationFilter.class);
        return http.build();
    }
}
