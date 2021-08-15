package com.company.AppRest;

import com.company.AppRest.entity.model.Pessoa;
import com.company.AppRest.entity.model.Role;
import com.company.AppRest.entity.model.Usuario;
import com.company.AppRest.enums.EstadoUsuario;
import com.company.AppRest.enums.Sexo;
import com.company.AppRest.exception.UsuarioException;
import com.company.AppRest.repository.RoleRepository;
import com.company.AppRest.service.PessoaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void save() throws UsuarioException {

        Set<Role> roles = new HashSet<>();

        roles.add(roleRepository.findByNome("USER"));

        Usuario usuario = Usuario.builder()
                                 .username("teste")
                                  .password("1q23")
                                .estadoUsuario(EstadoUsuario.ATIVDADO)
                                .roles(roles)
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
