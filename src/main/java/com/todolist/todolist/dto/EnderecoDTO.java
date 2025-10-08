package com.todolist.todolist.dto;

public record EnderecoDTO(
         String cep,
         String logradouro,
         String bairro,
         String localidade,
         String uf
) {
}
