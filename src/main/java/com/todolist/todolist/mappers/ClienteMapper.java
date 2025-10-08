package com.todolist.todolist.mappers;

import com.todolist.todolist.dto.ClienteDTO;
import com.todolist.todolist.dto.TelefoneDTO;
import com.todolist.todolist.model.Cliente;
import com.todolist.todolist.model.Telefone;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteMapper {

    // DTO -> Entidade
    public Cliente toCliente(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());

        if (dto.cpf() == null || dto.cpf().isBlank()) {
            throw new IllegalArgumentException("O cliente deve ter CPF preenchido");
        }
        cliente.setCpf(removerMascara(dto.cpf()));

        cliente.setEndereco(dto.endereco());

        // Telefones
        List<Telefone> telefones = Optional.ofNullable(dto.telefones())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("O cliente deve ter ao menos um telefone"))
                .stream()
                .map(tdto -> {
                    Telefone t = new Telefone();
                    t.setNumero(removerMascara(tdto.numero()));
                    t.setTipo(tdto.tipo());
                    return t;
                })
                .toList();
        cliente.setTelefone(telefones);

        // Emails
        cliente.setEmails(Optional.ofNullable(dto.emails()).orElse(new ArrayList<>()));

        return cliente;
    }

    // Entidade -> DTO
    public ClienteDTO toClienteDto(Cliente cliente) {
        List<TelefoneDTO> telefones = Optional.ofNullable(cliente.getTelefone())
                .orElse(new ArrayList<>())
                .stream()
                .map(t -> new TelefoneDTO(formatarTelefone(t.getNumero()), t.getTipo()))
                .toList();

        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                formatarCpf(cliente.getCpf()),
                telefones,
                cliente.getEndereco(),
                Optional.ofNullable(cliente.getEmails()).orElse(new ArrayList<>())
        );
    }

    // Funções auxiliares
    private static String removerMascara(String valor) {
        return valor == null ? null : valor.replaceAll("\\D", "");
    }

    private static String formatarTelefone(String numero) {
        if (numero == null || numero.length() != 11) return numero;
        return "(" + numero.substring(0, 2) + ") " +
                numero.substring(2, 7) + "-" +
                numero.substring(7);
    }

    private static String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) return cpf;
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9);
    }
}
