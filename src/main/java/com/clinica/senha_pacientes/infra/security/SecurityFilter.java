package com.clinica.senha_pacientes.infra.security;

import com.clinica.senha_pacientes.repositories.AdminRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);
        if (token != null) {
            var username = tokenService.getSubjectToken(token);
            var admin = adminRepository.findByUsername(username);

            var autentication = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autentication);
        }

        filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token != null) {
            return token.replace("Bearer ", "").trim();
        }
        return null;


    }
}
