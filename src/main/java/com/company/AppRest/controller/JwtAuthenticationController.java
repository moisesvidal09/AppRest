package com.company.AppRest.controller;


import com.company.AppRest.entity.model.Usuario;
import com.company.AppRest.entity.request.UserRequestDto;
import com.company.AppRest.entity.response.JwtResponse;
import com.company.AppRest.enums.EstadoUsuario;
import com.company.AppRest.exception.UsuarioException;
import com.company.AppRest.repository.UsuarioRepository;
import com.company.AppRest.service.PessoaAcaoService;
import com.company.AppRest.service.PessoaService;
import com.company.AppRest.service.UserDetailsServiceImpl;
import com.company.AppRest.util.JwtTokenUtil;
import org.apache.coyote.Response;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid UserRequestDto authenticationRequest) throws Exception {

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final EstadoUsuario estadoUsuario = usuarioRepository.findByUsername(userDetails.getUsername()).getEstadoUsuario();

        if(EstadoUsuario.DESATIVADO.equals(estadoUsuario))
            throw new UsuarioException("Usuário inativo ou E-mail não confirmado");

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/ativar/{token}")
    public ResponseEntity<String> ativarUsuario(@PathVariable String token){

        Usuario usuario = usuarioRepository.findByUsername(jwtTokenUtil.getUsernameFromToken(token));

        usuario.setEstadoUsuario(EstadoUsuario.ATIVDADO);

        usuarioRepository.save(usuario);

        return new ResponseEntity<>("Usuário Ativado com sucesso !!!", HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}