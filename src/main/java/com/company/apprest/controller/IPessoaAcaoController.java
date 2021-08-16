package com.company.apprest.controller;

import com.company.apprest.entity.request.PessoaAcaoRequestDto;
import com.company.apprest.entity.response.PessoaAcaoResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPessoaAcaoController extends ICrudController<PessoaAcaoRequestDto>{

    ResponseEntity<List<PessoaAcaoResponseDto>> getAcoesByToken(String token);

}
