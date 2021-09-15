package com.company.apprest.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaAcaoPrecoMedioResponseDto {

    private AcaoResponseDto acao;

    private String precoMedio;

}
