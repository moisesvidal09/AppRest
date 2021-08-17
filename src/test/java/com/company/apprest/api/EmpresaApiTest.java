package com.company.apprest.api;

import com.company.apprest.entity.api.EmpresaApi;
import com.company.apprest.entity.model.Acao;
import com.company.apprest.entity.model.Empresa;
import com.company.apprest.service.EmpresaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class EmpresaApiTest {

    @Autowired
    private EmpresaService empresaService;

    @Test
    public void getDataApiTest(){

        WebClient.Builder webClientBuilder = WebClient.builder();

        Mono<List<EmpresaApi>> monoEmpresaApiList = webClientBuilder.build()
                .get()
                .uri("https://api-cotacao-b3.labdo.it/api/empresa")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<EmpresaApi>>() {});

        List<EmpresaApi> empresaApiList = monoEmpresaApiList.block()
                                                            .parallelStream()
                                                            .filter(empresaApi -> !"".equals(empresaApi.getTx_cnpj()) && !"".equals(empresaApi.getNm_empresa())
                                                                                                && !"".equals(empresaApi.getCd_acao())
                                                                                                && !"".equals(empresaApi.getSegmento()))
                                                            .collect(Collectors.toList());

            List<Empresa> empresas = empresaApiList.parallelStream()
                    .map(this::mapeiaEmpresaApiParaEmpresa)
                    .collect(Collectors.toList());

            empresas.parallelStream()
                    .forEach(empresa -> empresaService.save(empresa));

        Assert.assertFalse(empresas.isEmpty());
    }

    private Empresa mapeiaEmpresaApiParaEmpresa(EmpresaApi empresaApi){

        Empresa empresa = new Empresa();

        empresa.setNome(empresaApi.getNm_empresa().trim());
        empresa.setCnpj(empresaApi.getTx_cnpj().trim());
        empresa.setSegmento(empresaApi.getSegmento().trim());

        List<String> codigoAcoes = Arrays.asList(empresaApi.getCd_acao().split(","));

        Set<Acao> acoes = codigoAcoes.parallelStream()
                                     .map(codigoAcao -> Acao.builder()
                                                         .codigoAcao(codigoAcao.trim())
                                                         .build())
                                     .collect(Collectors.toSet());

        empresa.setAcoes(acoes);

        return empresa;
    }

}
