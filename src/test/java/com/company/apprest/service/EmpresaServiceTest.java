package com.company.apprest.service;


import com.company.apprest.entity.model.Acao;
import com.company.apprest.entity.model.Empresa;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
public class EmpresaServiceTest {

    @Autowired
    EmpresaService empresaService;

    final Long idEmpresa = 1L;

    @Before
    public void setup(){

        Acao acao = Acao.builder()
                         .id(1L)
                         .codigoAcao("ABC3")
                         .build();

        Set<Acao> acoes = new HashSet<>();
        acoes.add(acao);

        Empresa empresa = Empresa.builder()
                                 .id(idEmpresa)
                                 .nome("Empresa Test")
                                 .cnpj("18.523.395/0001-72")
                                 .segmento("Vendas")
                                 .acoes(acoes)
                                 .build();

        empresaService.save(empresa);
    }

    @Test
    public void getListTest(){

        Pageable pageable = PageRequest.of(0,10);

        List<Empresa> empresas = empresaService.list(pageable);

        Assert.assertFalse(empresas.isEmpty());
    }

    @Test
    public void getByIdTest(){

        Empresa empresa = empresaService.get(this.idEmpresa);

        Assert.assertNotNull(empresa);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdInexistenteTest(){
        empresaService.get(5454548545L);
    }

    @Test
    public void getByIdListTest(){

        List<Long> ids = Arrays.asList(1L, 2L, 3L, 6L);

        List<Empresa> empresas = empresaService.get(ids);

        Assert.assertFalse(empresas.isEmpty());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteTest(){
        empresaService.delete(this.idEmpresa);

        Empresa empresa = empresaService.get(this.idEmpresa);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteIdInexistenteTest(){

        empresaService.delete(46988560498L);

    }

    @Test
    public void updateTest(){

        Empresa empresa = empresaService.get(this.idEmpresa);

        empresa.setSegmento("Veículos");

        empresa = empresaService.update(empresa);

        Assert.assertEquals("Veículos", empresa.getSegmento());
    }

}
