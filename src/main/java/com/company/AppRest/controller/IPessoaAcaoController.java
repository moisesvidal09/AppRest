package com.company.AppRest.controller;

import com.company.AppRest.entity.request.PessoaAcaoRequestDto;
import com.company.AppRest.entity.response.PessoaAcaoResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPessoaAcaoController extends ICrudController<PessoaAcaoRequestDto>{

    ResponseEntity<List<PessoaAcaoResponseDto>> getAcoesByToken(String token);

}
