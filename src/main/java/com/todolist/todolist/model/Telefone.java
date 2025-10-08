package com.todolist.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class Telefone {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private TipoTelefone tipo;

    @NotBlank(message = "Não pode ser nulo")
    @Pattern(regexp = "\\d{11}")
    private String numero;

}
