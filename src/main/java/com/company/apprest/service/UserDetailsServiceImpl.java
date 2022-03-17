package com.company.apprest.service;

import com.company.apprest.entity.model.Email;
import com.company.apprest.entity.model.Role;
import com.company.apprest.entity.model.Usuario;
import com.company.apprest.exception.UsuarioException;
import com.company.apprest.repository.UsuarioRepository;
import com.company.apprest.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final EmailService emailService;

    private final JwtTokenUtil jwtTokenUtil;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository, EmailService emailService, JwtTokenUtil jwtTokenUtil) {
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null){
            throw new UsernameNotFoundException("Usuario " + username + " não encontrado!!!");
        }

        return new User(usuario.getUsername(), usuario.getPassword(), buildSimpleGrantedAuthorities(usuario.getRoles()));
    }

    protected static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getNome()));
        }
        return authorities;
    }

    public void validateSenha(String senha) throws UsuarioException {

        final int tamanhoMinimoSenha = 8;

        if(senha.length() < tamanhoMinimoSenha) throw new UsuarioException("Senha deve possuir tamanho minimo de 8 caracteres!!!");

        Pattern pattern = Pattern.compile("[^A-Za-z0-9]+");
        Matcher matcher = pattern.matcher(senha);
        boolean contemCaracterEspecial = matcher.find();

        if(!contemCaracterEspecial) throw new UsuarioException("Senha deve possuir caracter especial como: # $ % @ &");

    }

    public void sendConfirmEmail(Usuario usuario){

        UserDetails userDetails = loadUserByUsername(usuario.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        Email email = Email.builder()
                .ownerRef("Cadastro")
                .emailFrom("moisesrok@gmail.com")
                .emailTo(usuario.getUsername())
                .subject("Ativar usuário !!!")
                .text("Atenção acesso o link para ativar seu usuário. http://localhost:8888/ativar/"+token)
                .build();

        emailService.sendEmail(email);
    }

}
