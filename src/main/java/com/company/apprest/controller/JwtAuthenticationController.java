package com.company.apprest.controller;


import com.company.apprest.entity.model.Usuario;
import com.company.apprest.entity.request.UserRequestDto;
import com.company.apprest.entity.response.JwtResponse;
import com.company.apprest.enums.EstadoUsuario;
import com.company.apprest.exception.UsuarioException;
import com.company.apprest.repository.UsuarioRepository;
import com.company.apprest.service.UserDetailsServiceImpl;
import com.company.apprest.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsServiceImpl userDetailsService;

    private final UsuarioRepository usuarioRepository;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserDetailsServiceImpl userDetailsService, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody @Valid UserRequestDto authenticationRequest) throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final EstadoUsuario estadoUsuario = usuarioRepository.findByUsername(userDetails.getUsername()).getEstadoUsuario();

        if(EstadoUsuario.DESATIVADO.equals(estadoUsuario))
            throw new UsuarioException("Usuário inativo ou E-mail não confirmado");

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    @PostMapping("/ativar/{token}")
    public ResponseEntity<String> ativarUsuario(@PathVariable String token){

        Usuario usuario = usuarioRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token));

        usuario.setEstadoUsuario(EstadoUsuario.ATIVDADO);

        usuarioRepository.save(usuario);

        return new ResponseEntity<>("Usuário Ativado com sucesso !!!", HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws UsuarioException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UsuarioException("Usuário Desativado");
        } catch (BadCredentialsException e) {
            throw new UsuarioException("Usuário ou senha errado");
        }
    }
}