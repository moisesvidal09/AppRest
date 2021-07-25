package com.company.AppRest.entity.response;

import com.company.AppRest.entity.model.Acao;
import com.company.AppRest.entity.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.collection.internal.PersistentSortedMap;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PessoaAcaoResponseDto {

    private Long id;

    private AcaoResponseDto acao;

    private Integer quatidade;

    private BigDecimal valorCompra;

    private BigDecimal valorVenda;

    private Date dataCompra;

    private Date dataVenda;
}
