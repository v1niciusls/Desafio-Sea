package com.todolist.todolist.dto;

import com.todolist.todolist.model.Endereco;
import com.todolist.todolist.model.Telefone;

import java.util.List;

public record ClienteDTO(
        Long id,
        String nome,
        String cpf,
        List<TelefoneDTO> telefones,
        Endereco endereco,
        List<String> emails

) { }