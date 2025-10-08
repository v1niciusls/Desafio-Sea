package com.todolist.todolist.service;

import com.todolist.todolist.dto.ClienteDTO;
import com.todolist.todolist.mappers.ClienteMapper;
import com.todolist.todolist.model.Cliente;
import com.todolist.todolist.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;
    @Autowired
    private  ClienteMapper clienteMapper;

    public List<ClienteDTO> listarTodos(){
        return repository.findAll().stream().map(clienteMapper::toClienteDto)
                .toList();
    }

    public Optional<ClienteDTO> buscarPorId(Long id){
         return repository.findById(id)
                 .map(clienteMapper::toClienteDto);
    }

    public ClienteDTO salvar(ClienteDTO clienteDTO){
        Cliente cliente = clienteMapper.toCliente(clienteDTO);
        Cliente salvo = repository.save(cliente);
        return clienteMapper.toClienteDto(salvo);
    }

    public ClienteDTO deletar(Long id){
         Cliente cliente = repository.findById(id)
                 .orElseThrow(() -> new EntityNotFoundException(id + "Nao encontrado"));

         repository.delete(cliente);
         return clienteMapper.toClienteDto(cliente);
    }


}
