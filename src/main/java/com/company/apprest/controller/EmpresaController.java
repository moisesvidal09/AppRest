package com.company.apprest.controller;

import com.company.apprest.entity.model.Empresa;
import com.company.apprest.entity.request.EmpresaRequestDto;
import com.company.apprest.entity.response.EmpresaResponseDto;
import com.company.apprest.service.EmpresaService;
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
@RequestMapping("api/v1/empresas/")
public class EmpresaController implements IEmpresaController{

    private final EmpresaService empresaService;

    private final ModelMapper mapper;

    public EmpresaController(EmpresaService empresaService, ModelMapper mapper) {
        this.empresaService = empresaService;
        this.mapper = mapper;
    }

    @PostMapping
    @Override
    public ResponseEntity<EmpresaResponseDto> create(@RequestBody @Valid EmpresaRequestDto empresaRequestDto) {

        Empresa empresa = empresaService.save(mapper.map(empresaRequestDto, Empresa.class));

        EmpresaResponseDto empresaResponseDto = mapper.map(empresa, EmpresaResponseDto.class);

        return new ResponseEntity<>(empresaResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Override
    public ResponseEntity<EmpresaResponseDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(mapper.map(empresaService.get(id), EmpresaResponseDto.class), HttpStatus.OK);
    }

    @PatchMapping
    @Override
    public ResponseEntity<EmpresaResponseDto> update(@RequestBody @Valid EmpresaRequestDto empresaRequestDto) {

        Empresa empresa = empresaService.update(mapper.map(empresaRequestDto, Empresa.class));

        EmpresaResponseDto empresaResponseDto = mapper.map(empresa, EmpresaResponseDto.class);

        return new ResponseEntity<>(empresaResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Override
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        empresaService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("list")
    @Override
    public ResponseEntity<List<EmpresaResponseDto>> getList(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {

        List<Empresa> empresas = empresaService.list(pageable);

        List<EmpresaResponseDto> empresaResponseDtoList = empresas.parallelStream()
                                                                  .map(e -> mapper.map(e, EmpresaResponseDto.class))
                                                                  .collect(Collectors.toList());

        return new ResponseEntity<>(empresaResponseDtoList, HttpStatus.OK);
    }

}
