package com.clinica.senha_pacientes.services;

import com.clinica.senha_pacientes.DTOs.AutenticacaoDTO;
import com.clinica.senha_pacientes.enitites.Admin;
import com.clinica.senha_pacientes.infra.exceptions.ValidacaoException;
import com.clinica.senha_pacientes.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username);
    }

    public void registrarAdmin(AutenticacaoDTO dto) {
        var encodedPassword = passwordEncoder.encode(dto.senha());

        if (adminRepository.existsByUsername(dto.username())) {
            throw new ValidacaoException("Username já cadastrado no sistema!");
        }

        var admin = new Admin(dto.username(), encodedPassword);
        adminRepository.save(admin);
    }
}
