package com.company.AppRest.thread;

import com.company.AppRest.entity.api.EmpresaApi;
import com.company.AppRest.entity.model.Acao;
import com.company.AppRest.entity.model.Empresa;
import com.company.AppRest.repository.EmpresaRepository;
import com.company.AppRest.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class EmpresaApiThread extends Thread{

    @Autowired
    private EmpresaService empresaService;

    @Override
    public void run(){

        WebClient.Builder webClientBuilder = WebClient.builder();

        Mono<List<EmpresaApi>> monoEmpresaApiList = webClientBuilder.build()
                .get()
                .uri("https://api-cotacao-b3.labdo.it/api/empresa")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<EmpresaApi>>() {});

        List<EmpresaApi> empresaApiList = monoEmpresaApiList.block();

        if(empresaApiList != null) {

            List<Empresa> empresas = empresaApiList.parallelStream()
                    .map(this::mapeiaEmpresaApiParaEmpresa)
                    .collect(Collectors.toList());

            empresas.parallelStream()
                    .forEach(empresa -> {
                        empresaService.save(empresa);
                    });

        } else {

            System.err.println("Atenção !!!!! \n Não foi possível obter dados da API ");

        }

    }


    private Empresa mapeiaEmpresaApiParaEmpresa(EmpresaApi empresaApi){

        Empresa empresa = new Empresa();

        empresa.setNome(empresaApi.getNm_empresa());
        empresa.setCnpj(empresaApi.getTx_cnpj());
        empresa.setSegmento(empresaApi.getSegmento());

        List<String> codigoAcoes = Arrays.asList(empresaApi.getCd_acao().split(","));

        Set<Acao> acoes = codigoAcoes.parallelStream()
                                        .map(codigoAcao -> {
                                            return Acao.builder()
                                                    .codigoAcao(codigoAcao)
                                                    .build();
                                        }).collect(Collectors.toSet());

        empresa.setAcoes(acoes);

        return empresa;
    }

}
