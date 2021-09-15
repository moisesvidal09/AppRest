package com.company.apprest.controller;

import com.company.apprest.entity.model.PessoaAcao;
import com.company.apprest.entity.request.PessoaAcaoRequestDto;
import com.company.apprest.entity.response.PessoaAcaoPrecoMedioResponseDto;
import com.company.apprest.entity.response.PessoaAcaoResponseDto;
import com.company.apprest.service.PessoaAcaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoa/acao")
public class PessoaAcaoController implements IPessoaAcaoController{

    @Autowired
    private PessoaAcaoService pessoaAcaoService;

    @Autowired
    private ModelMapper mapper;


    @PostMapping
    @Override
    public ResponseEntity<PessoaAcaoResponseDto> create(@RequestBody @Valid PessoaAcaoRequestDto pessoaAcaoRequest) {

        PessoaAcao pessoaAcao = pessoaAcaoService.save(mapper.map(pessoaAcaoRequest, PessoaAcao.class));

        PessoaAcaoResponseDto pessoaAcaoResponseDto = mapper.map(pessoaAcao, PessoaAcaoResponseDto.class);

        return new ResponseEntity<>(pessoaAcaoResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Override
    public ResponseEntity<PessoaAcaoResponseDto> get(@PathVariable("id") Long id) {

        PessoaAcao pessoaAcao = pessoaAcaoService.get(id);

        PessoaAcaoResponseDto pessoaAcaoResponseDto = mapper.map(pessoaAcao, PessoaAcaoResponseDto.class);

        return new ResponseEntity<>(pessoaAcaoResponseDto, HttpStatus.OK);
    }

    @PatchMapping
    @Override
    public ResponseEntity<PessoaAcaoResponseDto> update(@RequestBody @Valid PessoaAcaoRequestDto pessoaAcaoRequest) {

        PessoaAcao pessoaAcao = pessoaAcaoService.update(mapper.map(pessoaAcaoRequest, PessoaAcao.class));

        PessoaAcaoResponseDto pessoaAcaoResponseDto = mapper.map(pessoaAcao, PessoaAcaoResponseDto.class);

        return new ResponseEntity<>(pessoaAcaoResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Override
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {

        pessoaAcaoService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("list")
    @Override
    public ResponseEntity<List<PessoaAcaoResponseDto>> getList(@PageableDefault(page = 0,size = 10, sort = "dataCompra", direction = Sort.Direction.ASC) Pageable pageable) {

        List<PessoaAcao> pessoaAcaoList = pessoaAcaoService.list(pageable);

        List<PessoaAcaoResponseDto> pessoaAcaoResponseDtoList = pessoaAcaoList.parallelStream()
                                                                              .map(p -> mapper.map(p, PessoaAcaoResponseDto.class))
                                                                              .collect(Collectors.toList());

        return new ResponseEntity<>(pessoaAcaoResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("byToken")
    @Override
    public ResponseEntity<List<PessoaAcaoResponseDto>> getAcoesByToken(@RequestHeader (name="Authorization") String token){

        List<PessoaAcao> acoes = pessoaAcaoService.getAcoesByToken(token);

        List<PessoaAcaoResponseDto> pessoaAcaoResponseDtoList = acoes.parallelStream()
                                                                     .map(p -> mapper.map(p, PessoaAcaoResponseDto.class))
                                                                     .collect(Collectors.toList());

        return new ResponseEntity<>(pessoaAcaoResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("preco-medio/{id}")
    public ResponseEntity<PessoaAcaoPrecoMedioResponseDto> getPrecoMedioAcao(@RequestHeader (name = "Authorization") String token, @PathVariable("id") Long acao_id) throws Exception {
        return new ResponseEntity<>(pessoaAcaoService.getPrecoMedioAcao(token, acao_id), HttpStatus.OK);
    }
}
