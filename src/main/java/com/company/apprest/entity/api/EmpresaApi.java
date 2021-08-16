package com.company.apprest.entity.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaApi {

    private String nm_empresa;

    private String segmento;

    private String tx_cnpj;

    private String cd_acao;

}
