package com.todolist.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Setter
@Getter
@Entity
@Table
public class Cliente {

    public Cliente(String nome, List<Telefone> telefone, Endereco endereco, List<String> emails){
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.emails = emails;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 100)
    @NotBlank
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\d{11}")
    private String cpf;


    @Embedded
    private Endereco endereco;

    @NotEmpty(message = "O cliente deve ter ao menos um telefone")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefone;


    @ElementCollection
    @CollectionTable(name = "emails", joinColumns = @JoinColumn(name = "cliente_id"))
    @Column(name = "email")
    private List<@Email(message = "Email invalido") String> emails;


    public Cliente() {

    }
}
