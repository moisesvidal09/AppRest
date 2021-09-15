package com.company.apprest.service;

import com.company.apprest.entity.model.PessoaAcao;
import com.company.apprest.repository.PessoaAcaoRepository;
import com.company.apprest.repository.PessoaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PessoaAcaoServiceTest {

    @Autowired
    PessoaAcaoRepository pessoaAcaoRepository;

    @Autowired
    PessoaRepository pessoaRepository;


    @Test
    public void calculaPrecoMedioAcoes(){

        PessoaAcao acao = PessoaAcao.builder()
                                    .valorCompra(new BigDecimal("16.85"))
                                    .quantidade(50)
                                    .build();

        PessoaAcao acao2 = PessoaAcao.builder()
                .valorCompra(new BigDecimal("17.44"))
                .quantidade(50)
                .build();

        PessoaAcao acao3 = PessoaAcao.builder()
                .valorCompra(new BigDecimal("17.44"))
                .quantidade(50)
                .valorVenda(new BigDecimal("19.56"))
                .build();

        List<PessoaAcao> acoes = Arrays.asList(acao, acao2, acao3);

        System.out.println(acoes);

        acoes = acoes.parallelStream()
                .filter(a -> a.getValorVenda() == null)
                .collect(Collectors.toList());

        List<BigDecimal> totalPagoPorAcao = acoes.stream()
                  .map(a ->
                      new BigDecimal(a.getValorCompra().toString()).multiply(new BigDecimal(a.getQuantidade().toString()),new MathContext(4,RoundingMode.HALF_UP))
                  )
                  .collect(Collectors.toList());

        Double totalPago = totalPagoPorAcao.stream()
                                            .mapToDouble(BigDecimal::doubleValue).sum();

        Integer totalQuantidade = acoes.stream()
                                        .map(PessoaAcao::getQuantidade)
                                        .mapToInt(Integer::intValue).sum();


        Assert.assertEquals(1714.5, totalPago, 0.0);
        Assert.assertEquals(100, totalQuantidade.longValue());
        Assert.assertEquals(17.145, (totalPago/totalQuantidade), 0.0);

        DecimalFormat df = new DecimalFormat("R$ #,##0.00");
        System.out.println("Total pago por acao: " + totalPagoPorAcao);
        System.out.println("Quantidade: " + totalQuantidade);
        System.out.println("Total Pago: " + totalPago);
        System.out.println("Preço medio: " + totalPago/totalQuantidade);
    }




}
