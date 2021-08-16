package com.company.apprest.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaRequestDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String segmento;

    @NotBlank
    private String cnpj;

    @NotNull
    private Set<AcaoRequestDto> acoes;

}
