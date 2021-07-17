package com.company.AppRest.controller;

import com.company.AppRest.entity.model.Pessoa;
import com.company.AppRest.entity.request.PessoaRequestDto;
import com.company.AppRest.entity.response.PessoaResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPessoaController extends ICrudController<PessoaRequestDto> {

    ResponseEntity<List<PessoaResponseDto>> updateList(List<PessoaRequestDto> pessoas);

}
