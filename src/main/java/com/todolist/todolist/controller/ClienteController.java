package com.todolist.todolist.controller;

import com.todolist.todolist.dto.ClienteDTO;
import com.todolist.todolist.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obterCliente(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ClienteDTO> salvar(@RequestBody ClienteDTO clienteDTO){
        ClienteDTO salvo = service.salvar(clienteDTO);
        return  ResponseEntity.ok(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ClienteDTO> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
