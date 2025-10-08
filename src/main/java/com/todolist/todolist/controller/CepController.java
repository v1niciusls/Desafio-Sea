package com.todolist.todolist.controller;

import com.todolist.todolist.dto.EnderecoDTO;
import com.todolist.todolist.model.Endereco;
import com.todolist.todolist.service.ViaCepService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CepController {

    private final ViaCepService viaCepService;

    public CepController(ViaCepService viaCepService){
        this.viaCepService = viaCepService;
    }

    @GetMapping("/cep/{cep}")
    public EnderecoDTO buscarCep(@PathVariable String cep){
        return viaCepService.buscarEnderecoPorCep(cep);
    }


}
