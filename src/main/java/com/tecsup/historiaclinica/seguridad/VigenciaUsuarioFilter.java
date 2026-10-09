package com.tecsup.historiaclinica.seguridad;

import com.tecsup.historiaclinica.usuarios.UsuarioRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.io.IOException;
import java.util.List;

public class VigenciaUsuarioFilter extends OncePerRequestFilter {
    private final UsuarioRepository repository;
    public VigenciaUsuarioFilter(UsuarioRepository repository){this.repository=repository;}
    @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)
            throws ServletException,IOException {
        var auth=SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
            var usuario=repository.findByUsername(auth.getName());
            if(usuario.isEmpty() || !usuario.get().isActivo() || !usuario.get().getRol().isActivo()){
                SecurityContextHolder.clearContext();
                if(request.getSession(false)!=null)request.getSession(false).invalidate();
                if(request.getRequestURI().startsWith("/api/"))response.sendError(401);
                else response.sendRedirect("/login?error");
                return;
            }
            var authorities=List.of(new SimpleGrantedAuthority("ROLE_"+usuario.get().getRol().getCodigo()));
            var actualizado=UsernamePasswordAuthenticationToken.authenticated(auth.getPrincipal(),auth.getCredentials(),authorities);
            actualizado.setDetails(auth.getDetails());
            SecurityContextHolder.getContext().setAuthentication(actualizado);
        }
        chain.doFilter(request,response);
    }
}
