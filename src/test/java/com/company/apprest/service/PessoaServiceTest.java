package com.company.apprest.service;

import com.company.apprest.entity.model.Pessoa;
import com.company.apprest.entity.model.Usuario;
import com.company.apprest.enums.EstadoUsuario;
import com.company.apprest.enums.Sexo;
import com.company.apprest.exception.UsuarioException;
import com.company.apprest.repository.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void save() throws UsuarioException {

        Usuario usuario = Usuario.builder()
                                 .username("teste")
                                  .password("sdkiflbasdlfkasd#")
                                .estadoUsuario(EstadoUsuario.ATIVDADO)
                                .build();

        Pessoa pessoa = Pessoa.builder()
                              .nome("Moises")
                              .cpf("705.123.789-85")
                              .dataNascimento(Date.from(LocalDate.parse("1999-04-08").atStartOfDay(ZoneId.systemDefault()).toInstant()))
                              .sexo(Sexo.M)
                              .usuario(usuario)
                              .build();

        pessoa = pessoaService.save(pessoa);

        Assert.assertTrue(pessoa.getId() != null && pessoa.getId() > 0);

    }

}
