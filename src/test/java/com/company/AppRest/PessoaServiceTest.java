package com.company.AppRest;

import com.company.AppRest.entity.model.Pessoa;
import com.company.AppRest.enums.Sexo;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Test
    public void save(){

        Pessoa pessoa = Pessoa.builder()
                              .nome("Moises")
                              .cpf("705.123.789-85")
                              .dataNascimento(Date.from(LocalDate.parse("1999-04-08").atStartOfDay(ZoneId.systemDefault()).toInstant()))
                              .sexo(Sexo.M)
                              .build();

        pessoa = pessoaService.save(pessoa);

        Assert.assertTrue(pessoa.getId() != null && pessoa.getId() > 0);

    }

}
