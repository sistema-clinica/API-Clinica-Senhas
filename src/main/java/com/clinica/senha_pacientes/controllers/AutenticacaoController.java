package com.clinica.senha_pacientes.controllers;

import com.clinica.senha_pacientes.DTOs.AutenticacaoDTO;
import com.clinica.senha_pacientes.DTOs.TokenDTO;
import com.clinica.senha_pacientes.enitites.Admin;
import com.clinica.senha_pacientes.infra.security.TokenService;
import com.clinica.senha_pacientes.services.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(summary = "Fazer login de admin")
    public ResponseEntity<TokenDTO> fazerLogin(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO){
        var authenticationToken = new UsernamePasswordAuthenticationToken(autenticacaoDTO.username(), autenticacaoDTO.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);

        var token = tokenService.gerarToken((Admin) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/cadastro")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Fazer cadastro de um admin",
            description = "Faz o cadastro de um novo admin, é necessário que o username seja único e apenas um admin logado pode cadastrar outro.")
    public ResponseEntity<TokenDTO> fazerCadastro(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO) {
        autenticacaoService.registrarAdmin(autenticacaoDTO);

        return fazerLogin(autenticacaoDTO);
    }

}
