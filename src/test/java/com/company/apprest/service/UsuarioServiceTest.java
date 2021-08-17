package com.company.apprest.service;


import com.company.apprest.exception.UsuarioException;
import com.company.apprest.service.UserDetailsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Test
    public void validateSenha() throws UsuarioException {

        String senha = "JOAOteste#";

        final int tamanhoMinimoSenha = 8;

        if(senha.length() < tamanhoMinimoSenha) throw new UsuarioException("Senha deve possuir tamanho minimo de 8 caracteres!!!");

        Pattern pattern = Pattern.compile("[^A-Za-z0-9]+");
        Matcher matcher = pattern.matcher(senha);
        boolean contemCaracterEspecial = matcher.find();

        //se não tiver caracteres especiais
        if(!contemCaracterEspecial) throw new UsuarioException("Senha deve possuir caracter especial como: # $ % @ &");

    }

    @Test
    public void tedfasfdj(){

        List<String> myList = new ArrayList();
        Optional<String> optList = myList.stream().findAny();
        System.out.println(optList);
        try {

        }catch (Throwable e){

        }
    }



}
