package com.clinica.senha_pacientes.webSocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("painel")
public class PainelController {

    @GetMapping("/espera")
    public String painelEspera() {
        return "chamar_espera";
    }
}
