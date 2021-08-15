package com.company.AppRest.controller;

import com.company.AppRest.entity.model.Pessoa;
import com.company.AppRest.entity.request.PessoaRequestDto;
import com.company.AppRest.entity.response.PessoaResponseDto;
import com.company.AppRest.exception.UsuarioException;
import com.company.AppRest.service.PessoaService;
import com.company.AppRest.service.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/pessoas/")
public class PessoaController implements IPessoaController{

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    @PostMapping
    public ResponseEntity<PessoaResponseDto> create(@RequestBody @Valid PessoaRequestDto pessoaRequestDto) throws UsuarioException {

        Pessoa pessoa = pessoaService.save(mapper.map(pessoaRequestDto, Pessoa.class));

        userDetailsService.sendConfirmEmail(pessoa.getUsuario());

        PessoaResponseDto pessoaResponseDto = mapper.map(pessoa, PessoaResponseDto.class);

        return new ResponseEntity<>(pessoaResponseDto, HttpStatus.CREATED);
    }


    @Override
    @GetMapping("{id}")
    public ResponseEntity<PessoaResponseDto> get(@PathVariable("id") Long id) {

        return new ResponseEntity<>(addListLink(mapper.map(pessoaService.get(id), PessoaResponseDto.class)), HttpStatus.OK);

    }


    @Override
    @PatchMapping
    public ResponseEntity<PessoaResponseDto> update(@RequestBody @Valid PessoaRequestDto pessoaRequestDto) {

        Pessoa pessoa = pessoaService.update(mapper.map(pessoaRequestDto, Pessoa.class));

        PessoaResponseDto pessoaResponseDto = mapper.map(pessoa, PessoaResponseDto.class);

        return new ResponseEntity<>(pessoaResponseDto, HttpStatus.OK);

    }


    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {

        pessoaService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @Override
    @GetMapping("list")
    public ResponseEntity<List<PessoaResponseDto>> getList(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC)Pageable pageable) {

        List<Pessoa> pessoas = pessoaService.list(pageable);

        List<PessoaResponseDto> pessoaResponseDtoList = pessoas.parallelStream()
                                                          .map(p -> mapper.map(p, PessoaResponseDto.class))
                                                          .collect(Collectors.toList());
        pessoaResponseDtoList.forEach(this::addGetLink);

        return new ResponseEntity<>(pessoaResponseDtoList, HttpStatus.OK);
    }


    @Override
    @PatchMapping("list")
    public ResponseEntity<List<PessoaResponseDto>> updateList(@RequestBody List<PessoaRequestDto> pessoaRequestDtoList) {

        List<Pessoa> pessoas = pessoaService.updateList(pessoaRequestDtoList.parallelStream()
                                                                            .map(p -> mapper.map(p, Pessoa.class))
                                                                            .collect(Collectors.toList()));

        List<PessoaResponseDto> pessoaResponseDtoList = pessoas.parallelStream()
                                                               .map(p -> mapper.map(p, PessoaResponseDto.class))
                                                               .collect(Collectors.toList());

        pessoaResponseDtoList.forEach(this::addGetLink);

        return new ResponseEntity<>(pessoaResponseDtoList, HttpStatus.OK);
    }


    private PessoaResponseDto addGetLink(PessoaResponseDto pessoaResponseDto){
        return pessoaResponseDto.add(linkTo(methodOn(PessoaController.class).get(pessoaResponseDto.getId())).withSelfRel());
    }

    private PessoaResponseDto addListLink(PessoaResponseDto pessoaResponseDto){

        Pageable pageable = PageRequest.of(0,10, Sort.by("nome"));

        return pessoaResponseDto.add(linkTo(methodOn(PessoaController.class).getList(pageable)).withSelfRel());
    }

}
