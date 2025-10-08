package com.todolist.todolist.infra.security;

import com.todolist.todolist.repository.UsuarioRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//  o envio de um token é realizado em um cabeçalho do protocolo http, e tem um cabecalho especifico para o token que é o Authorization

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;


    // chegou uma requisicao da api, o spring vai chamar o filter com o metodo doFilterInternal(), será executada uma unica vez para cada requisicao
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // agora a logica é recuperar o token e checar se ele é valido
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null){
            var subject = tokenService.getSubject(tokenJWT); // verirficando se o token é valido, e no subject esta guardado o login da pessoa
            System.out.println(subject);
            var role = tokenService.getRole(tokenJWT);
            // agora o spring nao guarda sessao, logo ele acha que a pessoa nao esta autenticada entao vamos fazer uma autenticacao forcada

            var usuario = repository.findByLogin(subject); // recuperando o objeto usuario,

            var authorities = List.of(new SimpleGrantedAuthority(role));
            // agora vamos passar pro spring: spring considere que este usuario esta autenticado

            var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());// essa classe do spring é como se fosse um dto que representa o usuario logado
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        System.out.println(tokenJWT);

        filterChain.doFilter(request,response); // continua o fluxo encaminhando para o proximo filtro o request e o response

    }

    public String recuperarToken(HttpServletRequest request)
    {
        //pegando cabeçalho
        var authorizationHeader = request.getHeader("Authorization"); // dentro do parametro coloca o header que voce que rpegar
        if(authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", "");
        }
        return  null;
    }



    // FILTERCHAIN = CADEIA DE FILTROS QUE EXISTE NA APLICACAO


}
