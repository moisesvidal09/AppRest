package com.company.AppRest.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpresaResponseDto {

    private Long id;

    private String nome;

    private String segmento;

    private String cnpj;

    private Set<AcaoResponseDto> acoes;

}
