package com.todolist.todolist.dto;

import com.todolist.todolist.model.TipoTelefone;

public record TelefoneDTO (
        String numero,
        TipoTelefone tipo
){
}
