package com.clinica.senha_pacientes.repositories;

import com.clinica.senha_pacientes.enitites.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);

    boolean existsByUsername(String username);
}
