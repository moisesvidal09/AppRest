package com.company.AppRest.entity.response;

import com.company.AppRest.entity.model.Acao;
import com.company.AppRest.entity.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;
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


    private AcaoResponseDto acao;

    private Integer quantidade;

    private BigDecimal valorCompra;

    private BigDecimal valorVenda;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataCompra;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataVenda;
}
