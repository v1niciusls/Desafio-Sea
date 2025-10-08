package com.todolist.todolist.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.todolist.todolist.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try{
            // Algorithm.HMAC256() recebe como parametro uma chave secreta para fazer a geração, senha para fazer a assinatura do token
            var algoritmo = Algorithm.HMAC256(secret);
            return  JWT.create()
                    .withIssuer("API") // ferramenta responsavel pela geracao do token
                    .withClaim("id",usuario.getId()) // passar dados do usuario
                    .withClaim("role", usuario.getRole())
                    .withSubject(usuario.getLogin()) // quem é a pessoa relacionada com esse token
                    .withExpiresAt(dataExpiracao()) // tempo para o token expirar
                    .sign(algoritmo); // metodo para fazer assinatura
        }catch(JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token jwt",exception);
        }
    }


    public String getSubject(String tokenJWT){
        // logica desse metodo, receber o token verificar se esta valido e retornar o usuario que contem este token

        try{
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

    }catch (JWTVerificationException exception){
        throw new RuntimeException("Token JWT invalido ou expirado");
        }
    }
    public String getRole(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API")
                    .build()
                    .verify(tokenJWT)
                    .getClaim("role") // pega a claim role
                    .asString();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


}
