package com.company.apprest.controller;

import com.company.apprest.entity.model.Role;
import com.company.apprest.entity.model.Usuario;
import com.company.apprest.enums.EstadoUsuario;
import com.company.apprest.repository.RoleRepository;
import com.company.apprest.repository.UsuarioRepository;
import com.company.apprest.util.JwtTokenUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
public class AuthenticationTest {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserDetailsService userDetailsService;

    @Before
    public void setup(){

        Set<Role> roles = new HashSet<>();

        Role role = Role.builder()
                .nome("USER")
                .build();

        role = roleRepository.save(role);

        roles.add(role);

        Usuario usuario = Usuario.builder()
                                 .id(1L)
                                 .username("moises@gmail.com")
                                 .password(passwordEncoder.encode("asodufhaosdfu!@#!"))
                                 .estadoUsuario(EstadoUsuario.ATIVDADO)
                                 .roles(roles)
                                 .build();

        usuarioRepository.save(usuario);
    }

    @Test
    public void acessarUrlSemAutenticacaoTest() throws Exception {
        mockMvc.perform(delete("/empresas/1")).andExpect(status().isUnauthorized());
    }

    @Test
    public void validarTokenTest(){

        UserDetails userDetails = userDetailsService.loadUserByUsername("moises@gmail.com");

        final String token = jwtTokenUtil.generateToken(userDetails);

        Assert.assertTrue(jwtTokenUtil.validateToken(token, userDetails));
    }



}
