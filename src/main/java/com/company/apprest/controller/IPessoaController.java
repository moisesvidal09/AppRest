package com.company.apprest.controller;

import com.company.apprest.entity.request.PessoaRequestDto;
import com.company.apprest.entity.response.PessoaResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPessoaController extends ICrudController<PessoaRequestDto> {

    ResponseEntity<List<PessoaResponseDto>> updateList(List<PessoaRequestDto> pessoas);

}
