package com.company.apprest.service;

import com.company.apprest.entity.model.Pessoa;
import com.company.apprest.entity.model.Usuario;
import com.company.apprest.enums.EstadoUsuario;
import com.company.apprest.enums.Sexo;
import com.company.apprest.exception.UsuarioException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
public class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    private Long idPessoa;

    @Before
    public void setup() throws UsuarioException {
        Usuario usuario = Usuario.builder()
                .username("teste@email.com")
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

        idPessoa = pessoa.getId();
    }

    @Test(expected = UsuarioException.class)
    public void testCadastrarUsuarioEmailExistente() throws UsuarioException {

        Usuario usuario = Usuario.builder()
                .username("teste@email.com")
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

        pessoaService.save(pessoa);
    }

    @Test
    public void testNullList(){

        Pageable pageable = PageRequest.of(0, 10);

        List<Pessoa> pessoas = pessoaService.list(pageable);

        Assert.assertNotNull(pessoas);
    }

    @Test
    public void testGetPessoa(){

        Pessoa pessoa = pessoaService.get(this.idPessoa);

        Assert.assertNotNull(pessoa);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetPessoaIdInexistente(){

        Pageable pageable = PageRequest.of(0, 10000000);

        List<Pessoa> pessoas = pessoaService.list(pageable);

        //Pegando ultimo id da lista retornada e adicinando 500 para chegar em um id inexistente
        Pessoa pessoa = pessoaService.get(pessoas.get(pessoas.size()-1).getId() + 500);

        Assert.assertNotNull(pessoa);
    }

    @Test
    public void testDeletar(){
        pessoaService.delete(this.idPessoa);
    }

    @Test
    public void testUpdate(){

        Pessoa pessoa = pessoaService.get(this.idPessoa);

        pessoa.setSexo(Sexo.F);

        pessoa = pessoaService.update(pessoa);

        Assert.assertEquals(Sexo.F, pessoa.getSexo());
    }

    @Test
    public void testUpdateList(){

        Pageable pageable = PageRequest.of(0, 10000000);

        List<Pessoa> pessoas = pessoaService.list(pageable);

        pessoaService.updateList(pessoas);
    }

    @Test
    public void getListByIds(){

        List<Long> ids = Arrays.asList(10L,20L,30L,50L);

        pessoaService.get(ids);
    }

}
