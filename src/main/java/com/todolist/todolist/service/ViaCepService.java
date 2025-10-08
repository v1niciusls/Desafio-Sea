package com.todolist.todolist.service;

import com.todolist.todolist.dto.EnderecoDTO;
import com.todolist.todolist.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    public EnderecoDTO buscarEnderecoPorCep(String cep){
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();

        try {
            EnderecoDTO endereco = restTemplate.getForObject(url, EnderecoDTO.class);

            // se o CEP não existir, ViaCEP retorna JSON com campo "erro": true
            if (endereco == null || endereco.cep() == null) {
                throw new RuntimeException("CEP não encontrado!");
            }

            return endereco;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar ViaCEP: " + e.getMessage(), e);
        }
    }
}
